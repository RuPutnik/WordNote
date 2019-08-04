package ru.putnik.wordnote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.putnik.wordnote.model.SettingModel;
import ru.putnik.wordnote.pojo.SettingData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 01.08.2019 в 22:04
 */
public class SettingController implements Initializable {
    private SettingModel settingModel=new SettingModel();
    private static Stage stage;
    private static MainController mainController;

    private String pathToWordBook;
    private String pathToGroupFile="C:\\WordNote\\groupList.txt";

    @FXML
    private TextField pathToWordbookTextField;
    @FXML
    private Button pickWordFileButton;
    @FXML
    private TextField pathToGroupFileTextField;
    @FXML
    private Button pickGroupFileButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    public SettingController(MainController controller){
        mainController=controller;
        SettingData data=settingModel.loadSettings();
        if(data!=null) {
            pathToWordBook = data.getPathToWordbook();
            pathToGroupFile = data.getPathToGroupFile();
        }
    }
    public SettingController(){}


    public void createWindow(){
        stage=new Stage();
        Parent parent;
        try {
            parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/settingView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            stage.getIcons().add(new Image("icon/mainIcon.png"));
        }catch (Exception ex){
            System.out.println("Нет иконки окна");
        }
        stage.setScene(new Scene(parent));
        stage.setResizable(true);
        stage.setTitle("Word Note");
        stage.setWidth(430);
        stage.setHeight(180);
        stage.initOwner(mainController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SettingData settingData=settingModel.loadSettings();
        if(settingData!=null) {
            pathToWordBook = settingData.getPathToWordbook();
            pathToGroupFile = settingData.getPathToGroupFile();
        }
        pathToWordbookTextField.setText(pathToWordBook);
        pathToGroupFileTextField.setText(pathToGroupFile);
        pathToWordbookTextField.setFocusTraversable(false);
        pathToGroupFileTextField.setFocusTraversable(false);
        pickWordFileButton.setFocusTraversable(false);
        pickGroupFileButton.setFocusTraversable(false);
        pickWordFileButton.setOnAction(event -> {
            FileChooser chooser=new FileChooser();

            chooser.setTitle("Выберите файл со словарем");
            chooser.setInitialDirectory(new File((System.getenv("USERPROFILE") + "\\Desktop\\")));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt","*.txt"));
            File pickedFile=chooser.showOpenDialog(new Stage());
            if(pickedFile!=null){
            pathToWordbookTextField.setText(pickedFile.getPath());
            }
        });
        cancelButton.setOnAction(event -> {
            stage.close();
        });
        saveButton.setOnAction(event -> {
            SettingData data=new SettingData();
            pathToWordBook=pathToWordbookTextField.getText();
            pathToGroupFile=pathToGroupFileTextField.getText();
            data.setPathToWordbook(pathToWordBook);
            data.setPathToGroupFile(pathToGroupFile);
            settingModel.saveSettings(data);
            stage.close();
        });
    }

    public String getPathToWordBook() {
        return pathToWordBook;
    }

    public void setPathToWordBook(String pathToWordBook) {
        this.pathToWordBook = pathToWordBook;
    }

    public String getPathToGroupFile() {
        return pathToGroupFile;
    }

    public void setPathToGroupFile(String pathToGroupFile) {
        this.pathToGroupFile = pathToGroupFile;
    }
}
