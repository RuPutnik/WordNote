package ru.putnik.wordnote.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.GregorianCalendar;

import static ru.putnik.wordnote.AlertCall.callAlert;
import static ru.putnik.wordnote.Constants.PROGRAM_SETTINGS_PATH;

/**
 * Создано 03.08.2019 в 16:59
 */
public class GroupManagerModel {
    public String createGroupFile(){
        File folderProgram=new File(PROGRAM_SETTINGS_PATH);
        File groupFile=new File(folderProgram.getPath()+"\\groupList.txt");
        if(!folderProgram.exists()){
            folderProgram.mkdir();
        }

        if(!groupFile.exists()){
            try (FileWriter writer=new FileWriter(groupFile)){
                writer.append("-----Список групп слов [WordNote] "+new GregorianCalendar().getTime()+"-----");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            callAlert(Alert.AlertType.WARNING,"Ошибка создания файла",null,"Файл по данному адресу уже сущесвует");
            return null;
        }

        return groupFile.getPath();
    }
    public ObservableList<String> openGroupFile(String path){
        ObservableList<String> listGroup=FXCollections.observableArrayList();
        listGroup.add("Не выбрано");
        if(new File(path).exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {

                String line;
                line = reader.readLine();
                if (!line.contains("[WordNote]")) {
                    callAlert(Alert.AlertType.ERROR,"Ошибка загрузки",null,"Файл со списком групп поврежден и не был использован");
                } else {
                    while ((line = reader.readLine()) != null) {
                        listGroup.add(line);
                    }
                    return listGroup;
                }

            } catch (IOException e) {
                e.printStackTrace();
                callAlert(Alert.AlertType.ERROR,"Ошибка загрузки",null,"Файл со списком групп поврежден и не был использован");
            }
        }else {
            callAlert(Alert.AlertType.WARNING,"Ошибка загрузки",null,"Файл со списком групп отсутствует");
        }
        return null;
    }
    public void saveGroupFile(String path,ObservableList<String> list){
        try (FileWriter writer=new FileWriter(path)){
            writer.write("");
            writer.append("-----Список групп слов [WordNote] "+new GregorianCalendar().getTime()+"-----"+"\n");
            for(String nameGroup:list){
                writer.append(nameGroup.split(":")[0]+"\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
