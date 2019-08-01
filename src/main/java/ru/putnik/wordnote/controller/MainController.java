package ru.putnik.wordnote.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Создано 01.08.2019 в 16:43
 */
public class MainController extends Application implements Initializable {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent=FXMLLoader.load(getClass().getResource(""));

        primaryStage.setScene(new Scene(parent));
        primaryStage.getIcons().add(new Image(""));
        primaryStage.setResizable(true);
        primaryStage.setTitle("Word Note");
        primaryStage.setWidth();
        primaryStage.setHeight();
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
