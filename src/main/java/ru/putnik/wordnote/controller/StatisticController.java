package ru.putnik.wordnote.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.putnik.wordnote.model.StatisticModel;
import ru.putnik.wordnote.pojo.StatisticPacketData;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 10.08.2019 в 18:21
 */
public class StatisticController implements Initializable {
    private StatisticModel statisticModel=new StatisticModel();
    private static Stage stage;

    @FXML
    private ListView<String> listStatistics;
    @FXML
    private Button clearButton;
    @FXML
    private Button exitButton;


    public void createWindow(){
        stage=new Stage();
        Parent parent;
        try {
            parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/statisticView.fxml")));
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
        stage.setWidth(330);
        stage.setHeight(330);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StatisticPacketData packetData=statisticModel.loadStatistic();
        listStatistics.getItems().add("Всего тренировок: "+packetData.getCountTraining());
        listStatistics.getItems().add("Всего ответов: "+packetData.getCountAnswers());
        listStatistics.getItems().add("Всего правильных ответов: "+packetData.getCountRightAnswers());
        listStatistics.getItems().add("Всего неправильных ответов: "+packetData.getCountWrongAnswers());
        listStatistics.getItems().add("Всего игнорированных вопросов: "+packetData.getCountIgnoreAnswers());
        double percentTrue;
        if(packetData.getCountAnswers()!=0) {
            percentTrue = ((packetData.getCountRightAnswers() * 1.0) / packetData.getCountAnswers()) * 100;
        }else{
            percentTrue=0;
        }
        listStatistics.getItems().add("Процент правильных ответов: "+percentTrue+"%");
        clearButton.setOnAction(event -> {
            statisticModel.clearStatistic();
        });
        exitButton.setOnAction(event -> {
            stage.close();
        });
    }
}
