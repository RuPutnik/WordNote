package ru.putnik.wordnote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 01.08.2019 в 18:01
 */
public class AddEditController implements Initializable {
    private static Stage stage;
    private static String typeOperation;

    @FXML
    private Label typeOperationLabel;
    private void createWindow(){
        stage=new Stage();
        Parent parent;
        try {
            parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/addEditWordView.fxml")));
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
        stage.setWidth(330);
        stage.setHeight(230);
        stage.show();
    }
    public void addWord(){
        typeOperation="Добавить новое слово";
        createWindow();
    }
    public void editWord(){
        typeOperation="Редактировать слово";
        createWindow();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOperationLabel.setText(typeOperation);
    }
}
