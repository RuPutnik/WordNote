package ru.putnik.wordnote.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import ru.putnik.wordnote.pojo.Word;

import java.io.*;
import java.util.GregorianCalendar;

import static ru.putnik.wordnote.AlertCall.*;
/**
 * Создано 02.08.2019 в 11:02
 */
public class MainModel {
    public static String PATH_PROGRAM_FOLDER="C:\\WordNote";
    private static ObservableList<Word> wordList=FXCollections.observableArrayList();
    private static ObservableList<String> groupList=FXCollections.observableArrayList();
    private String path;

    public ObservableList<Word> getWordList() {
        return wordList;
    }

    public void addWord(Word word){
        if(path!=null){
            if(!wordList.contains(word)){
                wordList.add(word);
                rewriteFile();
            }else{
                callAlert(Alert.AlertType.WARNING,"Невозможно добавить слово",null,"Введенное слово слово с аналогичным переводом уже существует");
            }
        }else{
            callAlert(Alert.AlertType.WARNING,"Невозможно добавить слово",null,"Словарь не выбран");
        }
    }
    private void rewriteFile(){
        rewriteFile(path);
    }
    public boolean rewriteFile(String path){
        try (FileWriter writer=new FileWriter(path)){
            writer.write("");
            writer.append("-----Хранилище слов [WordNote] "+new GregorianCalendar().getTime()+"-----"+"\n");
            for(Word word1:wordList){
                writer.append(word1.getNumber()+":"+word1.getWord()+":"+word1.getTranslate()+":"+word1.getGroup()+":"+word1.getComment()+"\n");
            }
            writer.flush();
            this.path=path;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Word getWord(int index){
        return wordList.get(index);
    }
    public boolean openWordBook(String pathFile){
        try(BufferedReader reader=new BufferedReader(new FileReader(new File(pathFile)))){

            String line;
            line=reader.readLine();
            if(!line.contains("[WordNote]")){
                callAlert(Alert.AlertType.ERROR,"Ошибка загрузки",null,"Файл со словарем поврежден и не может быть использован");
                return false;
            }else {
                wordList.clear();
                while ((line = reader.readLine()) != null) {
                    Word word=new Word();

                    String partsLine[]=line.split(":");

                    if(partsLine.length<5){
                        callAlert(Alert.AlertType.ERROR,"Ошибка загрузки",null,"Файл со словарем по адресу "+pathFile+" поврежден и не может быть использован");
                        return false;
                    }
                    word.setNumber(Integer.parseInt(partsLine[0]));
                    word.setWord(partsLine[1]);
                    word.setTranslate(partsLine[2]);
                    word.setGroup(partsLine[3]);
                    word.setComment(partsLine[4]);

                    wordList.add(word);
                }
                path=pathFile;
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            callAlert(Alert.AlertType.WARNING,"Ошибка загрузки",null,"Файл со словарем по адресу "+pathFile+" отсутствует или поврежден");
            return false;
        }
    }
    public String createWordBook(String nameFile){
        File folderProgram=new File(PATH_PROGRAM_FOLDER);
        if(nameFile!=null) {
            File bookFile = new File(nameFile);
            if (!folderProgram.exists()) {
                //noinspection ResultOfMethodCallIgnored
                folderProgram.mkdir();
            }

            if (!bookFile.exists()) {
                try (FileWriter writer = new FileWriter(bookFile)) {
                    writer.append("-----Хранилище слов [WordNote] " + new GregorianCalendar().getTime() + "-----");
                    writer.flush();

                    path = bookFile.getPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                callAlert(Alert.AlertType.WARNING,"Невозможно создать словарь",null,"Файл по данному адресу уже сущесвует");
                return null;
            }
            return bookFile.getPath();
        }else {
            return null;
        }
    }

    public void deleteWord(int index){
        wordList.remove(index);
        for (Word w:wordList){
            if(w.getNumber()>index+1){
                w.setNumber(w.getNumber()-1);
            }
        }
        rewriteFile();
    }
    public void editWord(int index,Word newWord){
        wordList.set(index,newWord);
        rewriteFile();
    }

    public void setWordList(ObservableList<Word> list) {
        wordList = list;
    }

    public ObservableList<String> getGroupList() {
        return groupList;
    }
}
