package ru.putnik.wordnote.pojo;

import java.io.Serializable;

/**
 * Создано 02.08.2019 в 18:52
 */
public class SettingData implements Serializable {
    private String pathToWordbook;
    private String pathToGroupFile;

    public String getPathToWordbook() {
        return pathToWordbook;
    }

    public void setPathToWordbook(String pathToWordbook) {
        this.pathToWordbook = pathToWordbook;
    }

    public String getPathToGroupFile() {
        return pathToGroupFile;
    }

    public void setPathToGroupFile(String pathToGroupFile) {
        this.pathToGroupFile = pathToGroupFile;
    }
}
