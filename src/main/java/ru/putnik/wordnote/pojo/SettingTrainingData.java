package ru.putnik.wordnote.pojo;

/**
 * Создано 06.08.2019 в 19:58
 */
public class SettingTrainingData {
    private String typeTraining;
    private int countTimeTraining;
    private int[] fromUntilIgnore;
    private String typeQueue;

    public SettingTrainingData(String typeTraining, int countTimeTraining, int[] fromUntilIgnore, String typeQueue) {
        this.typeTraining = typeTraining;
        this.countTimeTraining = countTimeTraining;
        this.fromUntilIgnore = fromUntilIgnore;
        this.typeQueue = typeQueue;
    }

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

    public int[] getFromUntilIgnore() {
        return fromUntilIgnore;
    }

    public void setFromUntilIgnore(int[] fromUntilIgnore) {
        this.fromUntilIgnore = fromUntilIgnore;
    }

    public String getTypeQueue() {
        return typeQueue;
    }

    public void setTypeQueue(String typeQueue) {
        this.typeQueue = typeQueue;
    }
}
