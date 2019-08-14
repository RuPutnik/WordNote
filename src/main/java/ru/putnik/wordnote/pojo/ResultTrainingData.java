package ru.putnik.wordnote.pojo;

/**
 * Создано 09.08.2019 в 17:44
 */
public class ResultTrainingData {
    private int resultsAnswers[];
    private String questionWords[];

    public ResultTrainingData(int[] resultsAnswers, String[] questionWords) {
        this.resultsAnswers = resultsAnswers;
        this.questionWords = questionWords;
    }

    public int[] getResultsAnswers() {
        return resultsAnswers;
    }

    public String[] getQuestionWords() {
        return questionWords;
    }
}
