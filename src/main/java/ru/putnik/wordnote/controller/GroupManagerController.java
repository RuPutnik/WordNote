package ru.putnik.wordnote.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.putnik.wordnote.model.GroupManagerModel;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 01.08.2019 в 22:03
 */
public class GroupManagerController implements Initializable {
    private GroupManagerModel managerModel=new GroupManagerModel();
    private static Stage stage;
    private static MainController mainController;

    @FXML
    public ListView<String> listGroups;
    @FXML
    public Button createGroupButton;
    @FXML
    public Button editGroupButton;
    @FXML
    public Button removeGroupButton;
    @FXML
    public Button saveChangesButton;
    @FXML
    public Button exitButton;

    public GroupManagerController(MainController controller){
        mainController=controller;
    }
    public GroupManagerController(){}
    public void createWindow(){
        stage=new Stage();
        Parent parent;
        try {
            parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/groupManagerView.fxml")));
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
        stage.setWidth(430);
        stage.setHeight(330);
        stage.initOwner(mainController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(mainController.getPathToGroupFile()==null){
            managerModel.createGroupFile();
            stage.setTitle("Word Note"+" ["+"C:\\WordNote\\groupList.txt"+"]");
            mainController.setPathToGroupFile("C:\\WordNote\\groupList.txt");
        }else{
            ObservableList<String> list=managerModel.openGroupFile(mainController.getPathToGroupFile());
            if(list==null){
                managerModel.createGroupFile();
                stage.setTitle("Word Note"+" ["+"C:\\WordNote\\groupList.txt"+"]");
                mainController.setPathToGroupFile("C:\\WordNote\\groupList.txt");
            }else {
                listGroups.setItems(list);
                stage.setTitle("Word Note"+" ["+mainController.getPathToGroupFile()+"]");
            }
        }

        createGroupButton.setOnAction(event -> {
            Alert newGroup=new Alert(Alert.AlertType.CONFIRMATION);
            ((Stage)newGroup.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon/mainIcon.png"));
            newGroup.setTitle("Создание новое группы");
            newGroup.setHeaderText(null);
            VBox box=new VBox();
            Label infoLabel=new Label("Введите название группы");
            TextField nameField=new TextField();

            box.getChildren().add(infoLabel);
            box.getChildren().add(nameField);

            newGroup.getDialogPane().setContent(box);
            if(newGroup.showAndWait().get()==ButtonType.OK) {
                //if() {//TODO проверка что такая группа уже есть и что название не пустое
                    listGroups.getItems().add(nameField.getText());
                //}
            }
        });
        editGroupButton.setOnAction(event -> {
            Alert newGroup=new Alert(Alert.AlertType.CONFIRMATION);
            ((Stage)newGroup.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon/mainIcon.png"));
            newGroup.setTitle("Редактирование группы");
            newGroup.setHeaderText(null);
            VBox box=new VBox();
            Label infoLabel=new Label("Введите новое название группы");
            TextField nameField=new TextField(listGroups.getSelectionModel().getSelectedItem());

            box.getChildren().add(infoLabel);
            box.getChildren().add(nameField);

            newGroup.getDialogPane().setContent(box);
            if(newGroup.showAndWait().get()==ButtonType.OK) {
                //if() {//TODO проверка что такая группа уже есть и что название не пустое
                listGroups.getItems().set(listGroups.getSelectionModel().getSelectedIndex(),nameField.getText());
                //}
            }
        });
        removeGroupButton.setOnAction(event -> {
            listGroups.getItems().remove(listGroups.getSelectionModel().getSelectedIndex());
        });
        saveChangesButton.setOnAction(event -> {
            managerModel.saveGroupFile(mainController.getPathToGroupFile(),listGroups.getItems());
        });
        exitButton.setOnAction(event -> {
            stage.close();
        });
    }

    public GroupManagerModel getManagerModel() {
        return managerModel;
    }
}
