package ru.putnik.wordnote;

import javafx.application.Platform;
import ru.putnik.wordnote.model.TrainingModel;

import java.util.GregorianCalendar;

/**
 * Создано 11.08.2019 в 18:24
 */
public class Timer implements Runnable {
    private TrainingModel model;
    private int timeSecond;
    public Timer(TrainingModel trainingModel, int second){
        model=trainingModel;
        timeSecond=second;
    }
    @Override
    public void run() {
        long startTime=new GregorianCalendar().getTimeInMillis();
        for (;;){
            if(model.isTimeTraining()){
                if(new GregorianCalendar().getTimeInMillis()-startTime>timeSecond*1000){
                    Platform.runLater(()->{
                        model.getQuestionAlert().hide();
                        model.timeStopTraining();
                        model.setTimeTraining(false);

                    });
                    break;
                }else{
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                break;
            }
        }
    }
}
