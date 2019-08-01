package ru.putnik.wordnote.pojo;

/**
 * Создано 01.08.2019 в 17:43
 */
public class Word {
    private String word;
    private String translate;
    private String group;

    public Word(String word, String translate, String group) {
        this.word = word;
        this.translate = translate;
        this.group = group;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
