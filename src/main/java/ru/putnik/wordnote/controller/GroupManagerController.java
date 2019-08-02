package ru.putnik.wordnote.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 01.08.2019 в 22:03
 */
public class GroupManagerController implements Initializable {
    private static Stage stage;
    private static MainController mainController;

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
            stage.getIcons().add(new Image("mainIcon.png"));
        }catch (Exception ex){
            System.out.println("Нет иконки окна");
        }
        stage.setScene(new Scene(parent));
        stage.setResizable(true);
        stage.setTitle("Word Note");
        stage.setWidth(430);
        stage.setHeight(330);
        stage.initOwner(mainController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
