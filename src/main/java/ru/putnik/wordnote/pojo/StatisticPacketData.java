package ru.putnik.wordnote.pojo;

/**
 * Создано 10.08.2019 в 21:43
 */
public class StatisticPacketData {
    private int countTraining;
    private int countAnswers;
    private int countRightAnswers;
    private int countWrongAnswers;
    private int countIgnoreAnswers;

    public StatisticPacketData(){}

    public StatisticPacketData(int countAnswers, int countRightAnswers, int countWrongAnswers, int countIgnoreAnswers) {
        this.countAnswers = countAnswers;
        this.countRightAnswers = countRightAnswers;
        this.countWrongAnswers = countWrongAnswers;
        this.countIgnoreAnswers = countIgnoreAnswers;
    }

    public int getCountAnswers() {
        return countAnswers;
    }

    public void setCountAnswers(int countAnswers) {
        this.countAnswers = countAnswers;
    }

    public int getCountRightAnswers() {
        return countRightAnswers;
    }

    public void setCountRightAnswers(int countRightAnswers) {
        this.countRightAnswers = countRightAnswers;
    }

    public int getCountWrongAnswers() {
        return countWrongAnswers;
    }

    public void setCountWrongAnswers(int countWrongAnswers) {
        this.countWrongAnswers = countWrongAnswers;
    }

    public int getCountIgnoreAnswers() {
        return countIgnoreAnswers;
    }

    public void setCountIgnoreAnswers(int countIgnoreAnswers) {
        this.countIgnoreAnswers = countIgnoreAnswers;
    }

    public void setCountTraining(int countTraining) {
        this.countTraining = countTraining;
    }

    public int getCountTraining() {
        return countTraining;
    }
}
