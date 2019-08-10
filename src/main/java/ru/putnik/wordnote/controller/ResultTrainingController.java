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
import ru.putnik.wordnote.pojo.ResultTrainingData;
import ru.putnik.wordnote.pojo.StatisticPacketData;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 09.08.2019 в 17:40
 */
public class ResultTrainingController implements Initializable {
    private StatisticModel statisticModel=new StatisticModel();
    private static Stage stage;
    private static ResultTrainingData trainingData;

    @FXML
    private ListView<String> listResults;
    @FXML
    private Button exitButton;

    public void createWindow(ResultTrainingData data){
        stage=new Stage();
        trainingData=data;
        Parent parent;
        try {
            parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/resultTrainingView.fxml")));
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
        stage.setWidth(250);
        stage.setHeight(300);
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitButton.setOnAction(event->{stage.close();});

        fillList(trainingData);
    }
    private void fillList(ResultTrainingData trainingData){
        int countAll=trainingData.getResultsAnswers().length;
        int countTrue=0;
        int countWrong=0;
        int countIgnore=0;
        double percentTrue;
        for(int a=0;a<trainingData.getQuestionWords().length;a++){
            if(trainingData.getResultsAnswers()[a]==0){
                countIgnore++;
            }else if(trainingData.getResultsAnswers()[a]==-1){
                countWrong++;
            }else if(trainingData.getResultsAnswers()[a]==1){
                countTrue++;
            }
        }
        percentTrue=((countTrue*1.0)/countAll)*100;
        listResults.getItems().add("Всего вопросов: "+countAll);
        listResults.getItems().add("Всего правильных ответов: "+countTrue);
        listResults.getItems().add("Всего неправильных ответов: "+countWrong);
        listResults.getItems().add("Всего пропущено: "+countIgnore);
        listResults.getItems().add("Процент правильных ответов: "+percentTrue+"%");
        listResults.getItems().add("");
        listResults.getItems().add("Список вопросов");
        statisticModel.saveStatistic(new StatisticPacketData(countAll,countTrue,countWrong,countIgnore));
        for(int a=0;a<trainingData.getQuestionWords().length;a++){
            String result="";
            if(trainingData.getResultsAnswers()[a]==0){
                result="Игнорирован";
            }else if(trainingData.getResultsAnswers()[a]==-1){
                result="Неправильно";
            }else if(trainingData.getResultsAnswers()[a]==1){
                result="Правильно";
            }
            listResults.getItems().add(trainingData.getQuestionWords()[a]+" : "+result);
        }

    }
}
