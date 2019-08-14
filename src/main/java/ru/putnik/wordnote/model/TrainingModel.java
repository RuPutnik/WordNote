package ru.putnik.wordnote.model;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.putnik.wordnote.Timer;
import ru.putnik.wordnote.controller.ResultTrainingController;
import ru.putnik.wordnote.pojo.ResultTrainingData;
import ru.putnik.wordnote.pojo.SettingTrainingData;
import ru.putnik.wordnote.pojo.Word;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static ru.putnik.wordnote.AlertCall.callWaitAlert;

/**
 * Создано 06.08.2019 в 20:46
 */
public class TrainingModel {
    private ResultTrainingController resultTrainingController=new ResultTrainingController();
    private int resultsAnswers[];
    private String questionWords[];
    private double positionAlertX=-1;
    private double positionAlertY=-1;
    private static Alert questionAlert;
    private static boolean timeTraining=false;
    private ArrayList<String> tempMemoryAnswers;
    private ArrayList<String> tempCountDuplicatedAnswers;
    private static ObservableList<Word> list;
    public void runTraining(ObservableList<Word> listWord, SettingTrainingData trainingData){
        tempMemoryAnswers=new ArrayList<>();
        tempCountDuplicatedAnswers=new ArrayList<>();
        int indexQuestion;
        list=listWord;
        ArrayList<Integer> listIndexes = new ArrayList<>();
        if(!trainingData.getFromUntilIgnore()[2].equals("-1")) {
            String[] ignoredQuestions = trainingData.getFromUntilIgnore()[2].split(";");

            for (String ignoredQuestion : ignoredQuestions) {
                if (Integer.parseInt(ignoredQuestion) >= Integer.parseInt(trainingData.getFromUntilIgnore()[0]) &&
                        Integer.parseInt(ignoredQuestion) <= Integer.parseInt(trainingData.getFromUntilIgnore()[1])) {
                    listIndexes.add(Integer.parseInt(ignoredQuestion) - 1);
                }
            }
        }
        if(trainingData.getTypeTraining().contains("На время")){
            timeTraining=true;
            Timer timer=new Timer(this,trainingData.getCountTimeTraining());

            Thread timeThread=new Thread(timer);
            timeThread.start();
        }

        int countAllAnswers = Integer.parseInt(trainingData.getFromUntilIgnore()[1]) - (Integer.parseInt(trainingData.getFromUntilIgnore()[0]) - 1);
        resultsAnswers=new int[countAllAnswers];
        questionWords=new String[countAllAnswers];
        if(trainingData.getTypeQueue().equals("Сверху вниз")) {
            for (indexQuestion = Integer.parseInt(trainingData.getFromUntilIgnore()[0]) - 1; indexQuestion < Integer.parseInt(trainingData.getFromUntilIgnore()[1]); ) {
                if(!listIndexes.contains(indexQuestion)) {
                    ResultAnswer resultAnswer = null;
                    if (trainingData.getDirection().equals("Слово-Перевод")) {
                        resultAnswer = giveQuestion(listWord.get(indexQuestion).getWord(), findAllAnswersForQuestion(listWord,indexQuestion,1));
                        questionWords[indexQuestion] = listWord.get(indexQuestion).getWord();
                    } else if (trainingData.getDirection().equals("Перевод-Слово")) {
                        resultAnswer = giveQuestion(listWord.get(indexQuestion).getTranslate(), findAllAnswersForQuestion(listWord,indexQuestion,-1));
                        questionWords[indexQuestion] = listWord.get(indexQuestion).getTranslate();
                    }else if(trainingData.getDirection().equals("Случайный")){
                        int rndDir=new Random().nextInt(2);
                        if(rndDir==0){
                            resultAnswer = giveQuestion(listWord.get(indexQuestion).getWord(), findAllAnswersForQuestion(listWord,indexQuestion,1));
                            questionWords[indexQuestion] = listWord.get(indexQuestion).getWord();
                        }else {
                            resultAnswer = giveQuestion(listWord.get(indexQuestion).getTranslate(), findAllAnswersForQuestion(listWord,indexQuestion,-1));
                            questionWords[indexQuestion] = listWord.get(indexQuestion).getTranslate();
                        }
                    }

                    switch (Objects.requireNonNull(resultAnswer)) {
                        case TRUE: {
                            callWaitAlert(Alert.AlertType.INFORMATION, "Вопрос", null, "Ответ правильный!");
                            if (resultsAnswers[indexQuestion] != -1) {
                                resultsAnswers[indexQuestion] = 1;
                            }
                            indexQuestion++;
                            break;
                        }
                        case WRONG: {
                            callWaitAlert(Alert.AlertType.WARNING, "Вопрос", null, "Ответ неправильный!");
                            resultsAnswers[indexQuestion] = -1;
                            break;
                        }
                        case IGNORE: {
                            indexQuestion++;
                            break;
                        }
                        case CLOSE: {
                            callWaitAlert(Alert.AlertType.INFORMATION, "Тренировка", null, "Тренировка прекращена");
                            timeTraining=false;
                            return;
                        }
                    }
                }else{
                    resultsAnswers[indexQuestion] = 2;
                    indexQuestion++;
                }
            }
        }else if(trainingData.getTypeQueue().equals("Снизу вверх")){
            int indexQuestion1= Integer.parseInt(trainingData.getFromUntilIgnore()[1])-1;
            for (indexQuestion = Integer.parseInt(trainingData.getFromUntilIgnore()[1])-1; indexQuestion > Integer.parseInt(trainingData.getFromUntilIgnore()[0])-2; ) {
                if(!listIndexes.contains(indexQuestion)) {
                    ResultAnswer resultAnswer = null;

                    if (trainingData.getDirection().equals("Слово-Перевод")) {
                        resultAnswer = giveQuestion(listWord.get(indexQuestion).getWord(), findAllAnswersForQuestion(listWord,indexQuestion,1));
                        questionWords[indexQuestion1-indexQuestion] = listWord.get(indexQuestion).getWord();
                    } else if (trainingData.getDirection().equals("Перевод-Слово")) {
                        resultAnswer = giveQuestion(listWord.get(indexQuestion).getTranslate(), findAllAnswersForQuestion(listWord,indexQuestion,-1));
                        questionWords[indexQuestion1-indexQuestion] = listWord.get(indexQuestion).getTranslate();
                    }else if(trainingData.getDirection().equals("Случайный")){
                        int rndDir=new Random().nextInt(2);
                        if(rndDir==0){
                            resultAnswer = giveQuestion(listWord.get(indexQuestion).getWord(), findAllAnswersForQuestion(listWord,indexQuestion,1));
                            questionWords[indexQuestion1-indexQuestion] = listWord.get(indexQuestion).getWord();
                        }else {
                            resultAnswer = giveQuestion(listWord.get(indexQuestion).getTranslate(), findAllAnswersForQuestion(listWord,indexQuestion,-1));
                            questionWords[indexQuestion1-indexQuestion] = listWord.get(indexQuestion).getTranslate();
                        }
                    }

                    switch (Objects.requireNonNull(resultAnswer)) {
                        case TRUE: {
                            callWaitAlert(Alert.AlertType.INFORMATION, "Вопрос", null, "Ответ правильный!");
                            if (resultsAnswers[indexQuestion1-indexQuestion] != -1) {
                                resultsAnswers[indexQuestion1-indexQuestion] = 1;
                            }
                            indexQuestion--;
                            break;
                        }
                        case WRONG: {
                            callWaitAlert(Alert.AlertType.WARNING, "Вопрос", null, "Ответ неправильный!");
                            resultsAnswers[indexQuestion1-indexQuestion] = -1;
                            break;
                        }
                        case IGNORE: {
                            indexQuestion--;
                            break;
                        }
                        case CLOSE: {
                            callWaitAlert(Alert.AlertType.INFORMATION, "Тренировка", null, "Тренировка прекращена");
                            timeTraining=false;
                            return;
                        }
                    }
                }else{
                    resultsAnswers[indexQuestion1-indexQuestion] = 2;
                    indexQuestion--;
                }
            }
        }else if(trainingData.getTypeQueue().equals("Случайный")){
            boolean nextQuestion=true;
            ArrayList<Integer> memory=new ArrayList<>();
            indexQuestion=0;
            int count=0;
            while (true) {
                if(nextQuestion) {
                    indexQuestion = new Random().nextInt(Integer.parseInt(trainingData.getFromUntilIgnore()[1]));
                }
                if(!memory.contains(indexQuestion)) {
                    if (!listIndexes.contains(indexQuestion)) {
                        ResultAnswer resultAnswer = null;
                        memory.add(indexQuestion);

                        if (trainingData.getDirection().equals("Слово-Перевод")) {
                            resultAnswer = giveQuestion(listWord.get(indexQuestion).getWord(), findAllAnswersForQuestion(listWord,indexQuestion,1));
                            questionWords[indexQuestion] = listWord.get(indexQuestion).getWord();
                        } else if (trainingData.getDirection().equals("Перевод-Слово")) {
                            resultAnswer = giveQuestion(listWord.get(indexQuestion).getTranslate(), findAllAnswersForQuestion(listWord,indexQuestion,-1));
                            questionWords[indexQuestion] = listWord.get(indexQuestion).getTranslate();
                        }else if(trainingData.getDirection().equals("Случайный")){
                            int rndDir=new Random().nextInt(2);
                            if(rndDir==1){
                                resultAnswer = giveQuestion(listWord.get(indexQuestion).getTranslate(), findAllAnswersForQuestion(listWord,indexQuestion,-1));
                                questionWords[indexQuestion] = listWord.get(indexQuestion).getTranslate();
                            }else {
                                resultAnswer = giveQuestion(listWord.get(indexQuestion).getWord(), findAllAnswersForQuestion(listWord,indexQuestion,1));
                                questionWords[indexQuestion] = listWord.get(indexQuestion).getWord();

                            }
                        }

                        switch (Objects.requireNonNull(resultAnswer)) {
                            case TRUE: {
                                callWaitAlert(Alert.AlertType.INFORMATION, "Вопрос", null, "Ответ правильный!");
                                if (resultsAnswers[indexQuestion] != -1) {
                                    resultsAnswers[indexQuestion] = 1;
                                }
                                nextQuestion = true;
                                count++;
                                break;
                            }
                            case WRONG: {
                                callWaitAlert(Alert.AlertType.WARNING, "Вопрос", null, "Ответ неправильный!");
                                resultsAnswers[indexQuestion] = -1;
                                nextQuestion = false;
                                break;
                            }
                            case IGNORE: {
                                nextQuestion = true;
                                count++;
                                break;
                            }
                            case CLOSE: {
                                callWaitAlert(Alert.AlertType.INFORMATION, "Тренировка", null, "Тренировка прекращена");
                                timeTraining=false;
                                return;
                            }
                        }
                    } else {
                        resultsAnswers[indexQuestion] = 2;
                    }
                }
                if(count==(countAllAnswers -listIndexes.size())) break;
            }
        }
        timeTraining=false;
        tempMemoryAnswers.clear();
        tempCountDuplicatedAnswers.clear();
        callWaitAlert(Alert.AlertType.INFORMATION, "Тренировка", null, "Тренировка завершена");
        ResultTrainingData resultTrainingData=new ResultTrainingData(resultsAnswers,questionWords);
        resultTrainingController.createWindow(resultTrainingData);
    }
    private String[] findAllAnswersForQuestion(ObservableList<Word> wordList,int indexQuestion,int direction){
        String[] allAnswers;
        ArrayList<String> allAnswersList=new ArrayList<>();
        String word;
        if(direction==1){
            word=wordList.get(indexQuestion).getWord();
            for(Word w:wordList){
                if(w.getWord().equals(word)){
                    allAnswersList.add(w.getTranslate());
                }
            }

        }else if(direction==-1){
            word=wordList.get(indexQuestion).getTranslate();
            for(Word w:wordList){
                if(w.getTranslate().equals(word)){
                    allAnswersList.add(w.getWord());
                }
            }
        }
        allAnswers=new String[allAnswersList.size()];
        allAnswersList.toArray(allAnswers);
        return allAnswers;
    }
    private ResultAnswer giveQuestion(String word, String[] translates){
        questionAlert=new Alert(Alert.AlertType.CONFIRMATION);

        ((Stage) questionAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon/mainIcon.png"));
        ((Stage) questionAlert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);

        VBox box=new VBox();
        Label wordLabel=new Label(word);
        wordLabel.setFont(new Font(16));
        TextField answerTranslateTextField=new TextField();
        box.getChildren().addAll(wordLabel,answerTranslateTextField);
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        if(positionAlertX!=-1){
            questionAlert.setX(positionAlertX);
        }
        if(positionAlertY!=-1){
            questionAlert.setY(positionAlertY);
        }
        questionAlert.getDialogPane().setContent(box);
        questionAlert.setTitle("Вопрос");
        questionAlert.setHeaderText("Введите перевод слова:");
        questionAlert.setContentText(null);
        questionAlert.getButtonTypes().clear();

        questionAlert.getButtonTypes().addAll(new ButtonType("Проверить"),new ButtonType("Пропустить"), new ButtonType("Выход",ButtonBar.ButtonData.CANCEL_CLOSE));

        Optional<ButtonType> optional=questionAlert.showAndWait();
        positionAlertX=questionAlert.getX();
        positionAlertY=questionAlert.getY();
        if(optional.get().getText().equals("Проверить")){
            for(String translate:translates) {
                String line1=translate.replaceAll("\\s+"," ").toLowerCase().trim();
                String line2=answerTranslateTextField.getText().replaceAll("\\s+"," ").toLowerCase().trim();
                if (line1.equals(line2)&&(!tempMemoryAnswers.contains(line2)||duplicationTranslate(line2))) {
                    tempMemoryAnswers.add(line2);
                    tempCountDuplicatedAnswers.add(line2);
                    return ResultAnswer.TRUE;
                }
            }
            return ResultAnswer.WRONG;
        }else if(optional.get().getText().equals("Пропустить")){
            return ResultAnswer.IGNORE;
        }else if(optional.get().getButtonData()==ButtonBar.ButtonData.CANCEL_CLOSE){
            return ResultAnswer.CLOSE;
        }else{
            return ResultAnswer.NULL;
        }

    }
    private boolean duplicationTranslate(String answer){
        int countAnswersInList=0;
        int countAnswersInMemoryList=0;
        for (Word w:list){
            if(w.getTranslate().replaceAll("\\s+"," ").toLowerCase().equals(answer)||w.getWord().replaceAll("\\s+"," ").toLowerCase().equals(answer)) {
                countAnswersInList++;
            }
        }
        for(String a:tempMemoryAnswers){
            if(a.equals(answer)){
                countAnswersInMemoryList++;
            }
        }
        return countAnswersInList > countAnswersInMemoryList;
    }
    public void timeStopTraining(){
        callWaitAlert(Alert.AlertType.INFORMATION, "Тренировка", null, "Время вышло. Тренировка завершена");
        for(int a=0;a<questionWords.length;a++){
            if(questionWords[a]==null||questionWords[a].equals("null")){
                resultsAnswers[a]=2;
            }
        }
        ResultTrainingData resultTrainingData=new ResultTrainingData(resultsAnswers,questionWords);
        resultTrainingController.createWindow(resultTrainingData);
    }
    private enum ResultAnswer{
        TRUE,IGNORE,WRONG,CLOSE,NULL
    }

    public Alert getQuestionAlert() {
        return questionAlert;
    }

    public boolean isTimeTraining() {
        return timeTraining;
    }

    public void setTimeTraining(boolean timeTraining) {
        TrainingModel.timeTraining = timeTraining;
    }
}
