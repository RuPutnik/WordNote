package ru.putnik.wordnote.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.putnik.wordnote.pojo.Word;

/**
 * Создано 02.08.2019 в 11:02
 */
public class MainModel {
    private static ObservableList<Word> wordList= FXCollections.observableArrayList();

    public ObservableList<Word> getWordList() {
        return wordList;
    }

    public void addWord(Word word){
       // if(){//проверка что такое слово с таким же переводом уже есть
         wordList.add(word);
        //}else{

       // }
    }
    public Word getWord(int index){
        return wordList.get(index);
    }
    public void openWordBook(String pathFile){
        //Чтение из файла
    }
    public void deleteWord(int index){
        wordList.remove(index);
    }
    public void editWord(int index,Word newWord){
        wordList.set(index,newWord);
    }

    public void setWordList(ObservableList<Word> wordList) {
        this.wordList = wordList;
    }
}
