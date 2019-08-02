package ru.putnik.wordnote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 01.08.2019 в 22:04
 */
public class SettingController implements Initializable {
    private static Stage stage;
    private static MainController mainController;

    public SettingController(MainController controller){
        mainController=controller;
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
            stage.getIcons().add(new Image("mainIcon.png"));
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

    }
}
