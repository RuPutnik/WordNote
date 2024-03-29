package ru.putnik.wordnote.pojo;

/**
 * Создано 01.08.2019 в 17:43
 */
public class Word {
    private int number=0;
    private String word="";
    private String translate="";
    private String group="";
    private String comment="";

    public Word(String word, String translate, String group, int number, String comment) {
        this.word = word;
        this.translate = translate;
        this.group = group;
        this.number=number;
        this.comment=comment;
    }
    public Word(){}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object obj) {
        return this.translate.toLowerCase().equals(((Word)obj).translate.toLowerCase())&&this.word.toLowerCase().equals(((Word)obj).word.toLowerCase());
    }
}
