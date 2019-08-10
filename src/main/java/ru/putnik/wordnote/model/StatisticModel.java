package ru.putnik.wordnote.model;

import javafx.scene.control.Alert;
import ru.putnik.wordnote.pojo.StatisticPacketData;

import java.io.*;
import java.util.GregorianCalendar;

import static ru.putnik.wordnote.AlertCall.callAlert;

/**
 * Создано 10.08.2019 в 18:58
 */
public class StatisticModel {
    private static String PATH_STATISTIC_FILE="C:\\WordNote\\statistic.log";
    private File statisticFile=new File(PATH_STATISTIC_FILE);
    public void clearStatistic(){
        if(statisticFile.exists()){
            //noinspection ResultOfMethodCallIgnored
            statisticFile.delete();
        }
    }
    public void saveStatistic(StatisticPacketData updatePacketData){
        StatisticPacketData oldStatistic=loadStatistic();

        StatisticPacketData newStatistic=new StatisticPacketData();
        newStatistic.setCountTraining(oldStatistic.getCountTraining()+1);
        newStatistic.setCountAnswers(oldStatistic.getCountAnswers()+updatePacketData.getCountAnswers());
        newStatistic.setCountRightAnswers(oldStatistic.getCountRightAnswers()+updatePacketData.getCountRightAnswers());
        newStatistic.setCountWrongAnswers(oldStatistic.getCountWrongAnswers()+updatePacketData.getCountWrongAnswers());
        newStatistic.setCountIgnoreAnswers(oldStatistic.getCountIgnoreAnswers()+updatePacketData.getCountIgnoreAnswers());

        try (FileWriter writer=new FileWriter(PATH_STATISTIC_FILE)){
            writer.write("");
            writer.append("-----Статистика [WordNote] ").append(String.valueOf(new GregorianCalendar().getTime())).append("-----").append("\n");
            writer.append("countTraining" + ":").append(String.valueOf(newStatistic.getCountTraining())).append("\n");
            writer.append("countAnswers" + ":").append(String.valueOf(newStatistic.getCountAnswers())).append("\n");
            writer.append("countRightAnswers" + ":").append(String.valueOf(newStatistic.getCountRightAnswers())).append("\n");
            writer.append("countWrongAnswers" + ":").append(String.valueOf(newStatistic.getCountWrongAnswers())).append("\n");
            writer.append("countIgnoreAnswers" + ":").append(String.valueOf(newStatistic.getCountIgnoreAnswers())).append("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            callAlert(Alert.AlertType.WARNING,"Ошибка сохранения",null,"При сохранении статистики по адресу "+PATH_STATISTIC_FILE+" произошла ошибка");
        }
    }
    public StatisticPacketData loadStatistic(){
        StatisticPacketData statisticPacketData=new StatisticPacketData();

        if(statisticFile.exists()){
            try(BufferedReader reader=new BufferedReader(new FileReader(statisticFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    switch (line.split(":")[0]){
                        case "countTraining":{
                            statisticPacketData.setCountTraining(Integer.parseInt(line.split(":")[1]));
                            break;
                        }
                        case "countAnswers":{
                            statisticPacketData.setCountAnswers(Integer.parseInt(line.split(":")[1]));
                            break;
                        }
                        case "countRightAnswers":{
                            statisticPacketData.setCountRightAnswers(Integer.parseInt(line.split(":")[1]));
                            break;
                        }
                        case "countWrongAnswers":{
                            statisticPacketData.setCountWrongAnswers(Integer.parseInt(line.split(":")[1]));
                            break;
                        }
                        case "countIgnoreAnswers":{
                            statisticPacketData.setCountIgnoreAnswers(Integer.parseInt(line.split(":")[1]));
                            break;
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                callAlert(Alert.AlertType.WARNING,"Ошибка загрузки",null,"Файл со статистикой по адресу "+PATH_STATISTIC_FILE+" отсутствует или поврежден");
                statisticPacketData.setCountTraining(0);
                statisticPacketData.setCountAnswers(0);
                statisticPacketData.setCountIgnoreAnswers(0);
                statisticPacketData.setCountRightAnswers(0);
                statisticPacketData.setCountWrongAnswers(0);
            }
        }else{
            statisticPacketData.setCountTraining(0);
            statisticPacketData.setCountAnswers(0);
            statisticPacketData.setCountIgnoreAnswers(0);
            statisticPacketData.setCountRightAnswers(0);
            statisticPacketData.setCountWrongAnswers(0);
        }

        return statisticPacketData;
    }
}
