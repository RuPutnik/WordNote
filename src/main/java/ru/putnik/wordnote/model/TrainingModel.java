package ru.putnik.wordnote.model;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.putnik.wordnote.controller.ResultTrainingController;
import ru.putnik.wordnote.pojo.ResultTrainingData;
import ru.putnik.wordnote.pojo.SettingTrainingData;
import ru.putnik.wordnote.pojo.Word;

import java.util.Optional;

import static ru.putnik.wordnote.AlertCall.callWaitAlert;

/**
 * Создано 06.08.2019 в 20:46
 */
public class TrainingModel {
    private ResultTrainingController resultTrainingController=new ResultTrainingController();
    private int resultsAnswers[];
    private int countAllAnswers;
    private String questionWords[];
    public void runTraining(ObservableList<Word> listWord, SettingTrainingData trainingData){
        int indexQuestion;

        countAllAnswers=trainingData.getFromUntilIgnore()[1]-(trainingData.getFromUntilIgnore()[0]-1);
        resultsAnswers=new int[countAllAnswers];
        questionWords=new String[countAllAnswers];
        for(indexQuestion=trainingData.getFromUntilIgnore()[0]-1;indexQuestion<trainingData.getFromUntilIgnore()[1];) {
            //добавить проверку игнорируемых вопросов
            ResultAnswer resultAnswer=null;
            if(trainingData.getDirection().equals("Слово-Перевод")) {
                resultAnswer = giveQuestion(listWord.get(indexQuestion).getWord(), listWord.get(indexQuestion).getTranslate());
                questionWords[indexQuestion]=listWord.get(indexQuestion).getWord();
            }else if(trainingData.getDirection().equals("Перевод-Слово")) {
                resultAnswer = giveQuestion(listWord.get(indexQuestion).getTranslate(), listWord.get(indexQuestion).getWord());
                questionWords[indexQuestion]=listWord.get(indexQuestion).getTranslate();
            }
            switch (resultAnswer) {
                case TRUE: {
                    callWaitAlert(Alert.AlertType.INFORMATION, "Вопрос", null, "Ответ правильный!");
                    if(resultsAnswers[indexQuestion]!=-1) {
                        resultsAnswers[indexQuestion] = 1;
                    }
                    indexQuestion++;
                    break;
                }
                case WRONG: {
                    callWaitAlert(Alert.AlertType.WARNING, "Вопрос", null, "Ответ неправильный!");
                    resultsAnswers[indexQuestion]=-1;
                    break;
                }
                case IGNORE: {
                    indexQuestion++;
                    break;
                }
                case CLOSE: {
                    callWaitAlert(Alert.AlertType.INFORMATION, "Тренировка", null, "Тренировка прекращена");
                    ResultTrainingData resultTrainingData=new ResultTrainingData(resultsAnswers,questionWords);
                    resultTrainingController.createWindow(resultTrainingData);
                    return;
                }
                case NULL: {
                    callWaitAlert(Alert.AlertType.INFORMATION, "Вопрос", null, "Неизвестная ошибка");
                    indexQuestion++;
                    break;
                }
            }
        }
        callWaitAlert(Alert.AlertType.INFORMATION, "Тренировка", null, "Тренировка завершена");
        ResultTrainingData resultTrainingData=new ResultTrainingData(resultsAnswers,questionWords);
        resultTrainingController.createWindow(resultTrainingData);
    }
    public ResultAnswer giveQuestion(String word,String translate){
        Alert questionAlert=new Alert(Alert.AlertType.CONFIRMATION);

        ((Stage) questionAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon/mainIcon.png"));
        ((Stage) questionAlert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);

        VBox box=new VBox();
        Label wordLabel=new Label(word);
        wordLabel.setFont(new Font(16));
        TextField answerTranslateTextField=new TextField();
        box.getChildren().addAll(wordLabel,answerTranslateTextField);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        questionAlert.getDialogPane().setContent(box);
        questionAlert.setTitle("Вопрос");
        questionAlert.setHeaderText("Введите перевод слова:");
        questionAlert.setContentText(null);
        questionAlert.getButtonTypes().clear();

        questionAlert.getButtonTypes().addAll(new ButtonType("Проверить"),new ButtonType("Пропустить"), new ButtonType("Выход",ButtonBar.ButtonData.CANCEL_CLOSE));

        Optional<ButtonType> optional=questionAlert.showAndWait();

        if(optional.get().getText().equals("Проверить")){
            if(translate.toLowerCase().equals(answerTranslateTextField.getText().toLowerCase())){
                return ResultAnswer.TRUE;
            }else{
                return ResultAnswer.WRONG;
            }
        }else if(optional.get().getText().equals("Пропустить")){
            return ResultAnswer.IGNORE;
        }else if(optional.get().getButtonData()==ButtonBar.ButtonData.CANCEL_CLOSE){
            return ResultAnswer.CLOSE;
        }else{
            return ResultAnswer.NULL;
        }

    }
    private enum ResultAnswer{
        TRUE,IGNORE,WRONG,CLOSE,NULL;
    }
}
