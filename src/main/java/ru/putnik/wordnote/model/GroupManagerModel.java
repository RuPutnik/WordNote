package ru.putnik.wordnote.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.GregorianCalendar;

/**
 * Создано 03.08.2019 в 16:59
 */
public class GroupManagerModel {
    public String createGroupFile(){
        File folderProgram=new File("C:\\WordNote");
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
            //TODO сообщить что такой файл уже существует
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
                    //TODO файл не рабочий , сообщить
                } else {
                    while ((line = reader.readLine()) != null) {
                        listGroup.add(line);
                    }
                    return listGroup;
                }

            } catch (IOException e) {
                e.printStackTrace();
                //TODO файл не рабочий , сообщить
            }
        }else {
            //TODO файл отстуствует, сообщить
        }
        return null;
    }
    public void saveGroupFile(String path,ObservableList<String> list){
        try (FileWriter writer=new FileWriter(path)){
            writer.write("");
            writer.append("-----Список групп слов [WordNote] "+new GregorianCalendar().getTime()+"-----"+"\n");
            for(String nameGroup:list){
                writer.append(nameGroup+"\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
