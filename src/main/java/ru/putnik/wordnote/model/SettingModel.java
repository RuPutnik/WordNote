package ru.putnik.wordnote.model;

import javafx.scene.control.Alert;
import ru.putnik.wordnote.pojo.SettingData;

import java.io.*;

import static ru.putnik.wordnote.AlertCall.*;

/**
 * Создано 03.08.2019 в 19:56
 */
public class SettingModel {
    public static String PATH_SETTINGS="C:\\WordNote\\settings.dat";
    public void saveSettings(SettingData data){
        try (FileWriter writer=new FileWriter(PATH_SETTINGS)){
            writer.write("");
            writer.append("-----Настройки [WordNote]-----"+"\n");
            writer.append("pathToWordFile"+"="+data.getPathToWordbook()+"\n");
            writer.append("pathToGroupFile"+"="+data.getPathToGroupFile()+"\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public SettingData loadSettings(){
        SettingData data=new SettingData();

        if(new File(PATH_SETTINGS).exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(PATH_SETTINGS)))) {
                String line;
                line = reader.readLine();
                if (!line.contains("[WordNote]")) {
                    callAlert(Alert.AlertType.ERROR,"Ошибка загрузки",null,"Файл с настройками поврежден и не был использован");
                    return null;
                } else {
                    while ((line=reader.readLine())!=null) {
                        if (line.split("=").length>1&&line.split("=")[0].equals("pathToWordFile")) {
                            data.setPathToWordbook(line.split("=")[1]);
                        }
                        if (line.split("=").length>1&&line.split("=")[0].equals("pathToGroupFile")) {
                            data.setPathToGroupFile(line.split("=")[1]);
                        }
                    }
                    return data;
                }
            } catch (IOException e) {
                e.printStackTrace();
                callAlert(Alert.AlertType.ERROR,"Ошибка загрузки",null,"Файл с настройками поврежден и не был использован");
                return null;
            }
        }else {
            callAlert(Alert.AlertType.WARNING,"Ошибка загрузки",null,"Файл с настройками отсутствует");
            return null;
        }
    }

}
