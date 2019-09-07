package ru.putnik.wordnote.pojo;

/**
 * Создано 06.08.2019 в 19:58
 */
public class SettingTrainingData {
    private String typeTraining;
    private int countTimeTraining;
    private String[] fromUntilIgnore;
    private String typeQueue;
    private String direction;
    private boolean showComment;

    public SettingTrainingData(){}

    public String getTypeTraining() {
        return typeTraining;
    }

    public void setTypeTraining(String typeTraining) {
        this.typeTraining = typeTraining;
    }

    public int getCountTimeTraining() {
        return countTimeTraining;
    }

    public void setCountTimeTraining(int countTimeTraining) {
        this.countTimeTraining = countTimeTraining;
    }

    public String[] getFromUntilIgnore() {
        return fromUntilIgnore;
    }

    public void setFromUntilIgnore(String[] fromUntilIgnore) {
        this.fromUntilIgnore = fromUntilIgnore;
    }

    public String getTypeQueue() {
        return typeQueue;
    }

    public void setTypeQueue(String typeQueue) {
        this.typeQueue = typeQueue;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isShowComment() {
        return showComment;
    }

    public void setShowComment(boolean showComment) {
        this.showComment = showComment;
    }
}
