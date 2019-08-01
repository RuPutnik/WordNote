package ru.putnik.wordnote.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 01.08.2019 в 16:43
 */
public class MainController extends Application implements Initializable {
    private AddEditController addEditController=new AddEditController();
    private GroupManagerController groupManagerController=new GroupManagerController();
    private SettingController settingController=new SettingController();
    private static Stage stage;

    @FXML
    private MenuItem addWord;
    @FXML
    private MenuItem editWord;
    @FXML
    private MenuItem addWordMenuItem;
    @FXML
    private MenuItem editWordMenuItem;
    @FXML
    private MenuItem deleteWordMenuItem;
    @FXML
    private MenuItem manageGroupsMenuItem;
    @FXML
    private MenuItem settingsMenuItem;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        Parent parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/mainView.fxml")));

        primaryStage.setScene(new Scene(parent));
        try {
            primaryStage.getIcons().add(new Image("mainIcon.png"));
        }catch (Exception ex){
            System.out.println("Нет иконки главного окна");
        }
        primaryStage.setResizable(true);
        primaryStage.setTitle("Word Note");
        primaryStage.setWidth(830);
        primaryStage.setHeight(630);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addWordMenuItem.setOnAction(event -> {addEditController.addWord();});
        editWordMenuItem.setOnAction(event -> {addEditController.editWord();});
        manageGroupsMenuItem.setOnAction(event -> {groupManagerController.createWindow();});
        settingsMenuItem.setOnAction(event -> {settingController.createWindow();});
        addWord.setOnAction(event -> {addEditController.addWord();});
        editWord.setOnAction(event -> {addEditController.editWord();});
    }

    public static void play(){
        launch();
    }
}
