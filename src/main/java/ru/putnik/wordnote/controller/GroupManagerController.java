package ru.putnik.wordnote.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.putnik.wordnote.model.GroupManagerModel;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static ru.putnik.wordnote.AlertCall.callAlert;

/**
 * Создано 01.08.2019 в 22:03
 */
public class GroupManagerController implements Initializable {
    private GroupManagerModel managerModel=new GroupManagerModel();
    private static Stage stage;
    private static MainController mainController;

    @FXML
    private ListView<String> listGroups;
    @FXML
    private Button createGroupButton;
    @FXML
    private Button editGroupButton;
    @FXML
    private Button removeGroupButton;
    @FXML
    private Button saveChangesButton;
    @FXML
    private Button exitButton;

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
                list.remove(0);
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
                if(!nameField.getText().equals("")) {
                    boolean contain=false;
                    for (String group:listGroups.getItems()){
                        if(group.toLowerCase().equals(nameField.getText().toLowerCase())){
                            contain=true;
                            break;
                        }
                    }
                    if(!contain) {
                        listGroups.getItems().add(nameField.getText());
                    }else{
                        callAlert(Alert.AlertType.WARNING,"Ошибка создания группы",null,"Данная группа уже существует");
                    }
                }else{
                    callAlert(Alert.AlertType.WARNING,"Ошибка создания группы",null,"Название группы не определено");
                }
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
                if(!nameField.getText().equals("")) {
                    boolean contain=false;
                    for (String group:listGroups.getItems()){
                        if(group.toLowerCase().equals(nameField.getText().toLowerCase())){
                            contain=true;
                            break;
                        }
                    }
                    if(!contain) {
                        listGroups.getItems().set(listGroups.getSelectionModel().getSelectedIndex(),nameField.getText());
                    }else{
                        callAlert(Alert.AlertType.WARNING,"Ошибка редактирования группы",null,"Данная группа уже существует");
                    }
                }else{
                    callAlert(Alert.AlertType.WARNING,"Ошибка редактирования группы",null,"Название группы не определено");
                }
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
