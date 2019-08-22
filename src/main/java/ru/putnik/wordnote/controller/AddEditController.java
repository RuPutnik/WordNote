package ru.putnik.wordnote.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.putnik.wordnote.pojo.Word;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static ru.putnik.wordnote.AlertCall.*;
/**
 * Создано 01.08.2019 в 18:01
 */
public class AddEditController implements Initializable {
    private static Stage stage;
    private static String typeOperation;
    private static int indexWord=-1;
    private static MainController mainController;


    @FXML
    private Label typeOperationLabel;
    @FXML
    private Button addEditWordButton;
    @FXML
    private Button exitButton;
    @FXML
    private TextField wordTextField;
    @FXML
    private TextField translateTextField;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private ComboBox<String> groupComboBox;

    public AddEditController(MainController controller){
        mainController=controller;
    }
    public AddEditController(){}

    private void createWindow(){
        stage=new Stage();
        Parent parent;
        try {
            parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/addEditWordView.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            stage.getIcons().add(new Image("icon/mainIcon.png"));
        }catch (Exception ex){
            System.out.println("Нет иконки окна");
        }
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.setTitle("Word Note");
        stage.setWidth(330);
        stage.setHeight(270);
        stage.initOwner(mainController.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }
    public void addWord(){
        typeOperation="Добавить новое слово";
        createWindow();
    }
    public void editWord(int index){
        if(index!=-1) {
            typeOperation = "Редактировать слово";
            indexWord = index;
            createWindow();
        }else{
            callAlert(Alert.AlertType.WARNING,"Невозможно отредактировать слово",null,"Редактируемое слово не выбрано");
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOperationLabel.setText(typeOperation);
        ObservableList<String> groupList=mainController.getGroupManagerController().getManagerModel().openGroupFile(mainController.getPathToGroupFile());
        if(groupList!=null){
        groupComboBox.setItems(groupList);
        }

        if(typeOperation.equals("Редактировать слово")){
            addEditWordButton.setText("Сохранить");
            Word word=mainController.getMainModel().getWordList().get(indexWord);
            if(!word.getWord().equals("-")) {
                wordTextField.setText(word.getWord());
            }
            if(!word.getTranslate().equals("-")) {
                translateTextField.setText(word.getTranslate());
            }
            if(groupComboBox.getValue()==null||!(word.getGroup().equals("-")||groupComboBox.getValue().equals("Не выбрано"))) {
                groupComboBox.setPromptText(word.getGroup());
            }
        }
        if(typeOperation.equals("Добавить новое слово")){
            addEditWordButton.setText("Добавить");
        }

        addEditWordButton.setOnAction(event -> {
            String word="-";
            String translate="-";
            String nameGroup="-";
            String comment="-";
            if(wordTextField.getText()!=null&&!wordTextField.getText().equals("")) word=wordTextField.getText().trim();
            if(translateTextField.getText()!=null&&!translateTextField.getText().equals("")) translate=translateTextField.getText().trim();
            if(groupComboBox.getValue()!=null&&!groupComboBox.getValue().equals("")&&!groupComboBox.getValue().equals("Не выбрано")) nameGroup=groupComboBox.getValue().trim();
            if(commentTextArea.getText()!=null&&!commentTextArea.getText().equals("")) comment=commentTextArea.getText();

            if(typeOperation.equals("Добавить новое слово")){
                if(!(word.equals("-")&&translate.equals("-"))){
                    mainController.getMainModel().addWord(new Word(word,translate,nameGroup,mainController.getMainModel().getWordList().size()+1,comment));
                }else{
                    callAlert(Alert.AlertType.WARNING,"Невозможно добавить слово",null,"Слово и его перевод не определены");
                }
            }else if(typeOperation.equals("Редактировать слово")){
                if(!(word.equals("-")&&translate.equals("-"))) {
                    mainController.getMainModel().editWord(indexWord, new Word(word, translate, nameGroup,indexWord+1, comment));
                }else {
                    callAlert(Alert.AlertType.WARNING,"Невозможно отредактировать слово",null,"Слово и его перевод не могут быть неопределены одновременно");
                }
            }
        });
        exitButton.setOnAction(event -> {
            stage.close();
        });
    }
}
