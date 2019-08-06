package ru.putnik.wordnote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 06.08.2019 в 18:25
 */
public class TrainingController implements Initializable {
    private static Stage stage;
    private static MainController mainController;
    private ToggleGroup typeGroup=new ToggleGroup();
    private ToggleGroup queueGroup=new ToggleGroup();

    @FXML
    private RadioButton standardTypeRadioButton;
    @FXML
    private RadioButton timeTypeRadioButton;
    @FXML
    private TextField countTimeTextField;
    @FXML
    private TextField fromTextField;
    @FXML
    private TextField untilTextField;
    @FXML
    private TextField ignoreTextField;
    @FXML
    private RadioButton topBottomRadioButton;
    @FXML
    private RadioButton bottomTopRadioButton;
    @FXML
    private RadioButton randomRadioButton;
    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;

    public TrainingController(MainController controller){
        mainController=controller;
    }
    public TrainingController(){}

    public void createWindow(){
        stage=new Stage();
        Parent parent;
        try {
            parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/trainingView.fxml")));
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
        stage.setResizable(false);
        stage.setTitle("Word Note");
        stage.setWidth(510);
        stage.setHeight(180);
        stage.initOwner(mainController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeGroup.getToggles().addAll(standardTypeRadioButton,timeTypeRadioButton);
        queueGroup.getToggles().addAll(topBottomRadioButton,bottomTopRadioButton,randomRadioButton);
        standardTypeRadioButton.setSelected(true);
        topBottomRadioButton.setSelected(true);
        fromTextField.setText("1");
        untilTextField.setText(String.valueOf(mainController.getMainModel().getWordList().size()));

        startButton.setOnAction(event -> {

        });
        exitButton.setOnAction(event -> {
            stage.close();
        });
    }
}
