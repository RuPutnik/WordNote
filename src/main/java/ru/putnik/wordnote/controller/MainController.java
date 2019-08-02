package ru.putnik.wordnote.controller;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import ru.putnik.wordnote.model.MainModel;
import ru.putnik.wordnote.pojo.Word;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 01.08.2019 в 16:43
 */
public class MainController extends Application implements Initializable {

    private MainModel mainModel=new MainModel();

    private AddEditController addEditController=new AddEditController(this);
    private GroupManagerController groupManagerController=new GroupManagerController(this);
    private SettingController settingController=new SettingController(this);

    private static Stage stage;

    @FXML
    private MenuItem addWord;
    @FXML
    private MenuItem editWord;
    @FXML
    private MenuItem addWordMenuItem;
    @FXML
    private MenuItem editWordMenuItem;
    @FXML
    private MenuItem deleteWordMenuItem;
    @FXML
    private MenuItem manageGroupsMenuItem;
    @FXML
    private MenuItem settingsMenuItem;
    @FXML
    private TableView<Word> wordTable;
    @FXML
    public TableColumn<Word,Integer> indexColumn;
    @FXML
    public TableColumn<Word,String> wordColumn;
    @FXML
    public TableColumn<Word,String>  translateColumn;
    @FXML
    public TableColumn<Word,String>  groupColumn;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        Parent parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/mainView.fxml")));

        primaryStage.setScene(new Scene(parent));
        try {
            primaryStage.getIcons().add(new Image("mainIcon.png"));
        }catch (Exception ex){
            System.out.println("Нет иконки главного окна");
        }
        primaryStage.setResizable(true);
        primaryStage.setTitle("Word Note");
        primaryStage.setWidth(830);
        primaryStage.setHeight(630);
        primaryStage.show();
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addWordMenuItem.setOnAction(event -> {addEditController.addWord();});
        editWordMenuItem.setOnAction(event -> {addEditController.editWord(wordTable.getSelectionModel().getSelectedIndex());});
        deleteWordMenuItem.setOnAction(event -> {
            int index=wordTable.getSelectionModel().getSelectedIndex();
            if(index!=-1) {
                mainModel.getWordList().remove(wordTable.getSelectionModel().getSelectedIndex());
            }else{
                //TODO
                //Сообщить что слово не выбрано
            }
        });
        manageGroupsMenuItem.setOnAction(event -> {groupManagerController.createWindow();});
        settingsMenuItem.setOnAction(event -> {settingController.createWindow();});
        addWord.setOnAction(event -> {addEditController.addWord();});
        editWord.setOnAction(event -> {addEditController.editWord(wordTable.getSelectionModel().getSelectedIndex());});
        deleteWordMenuItem.setOnAction(event -> {
            int index=wordTable.getSelectionModel().getSelectedIndex();
            if(index!=-1) {
                mainModel.getWordList().remove(wordTable.getSelectionModel().getSelectedIndex());
            }else{
                //TODO
                //Сообщить что слово не выбрано
            }
        });

        indexColumn.setCellValueFactory(value-> new SimpleObjectProperty<>(mainModel.getWordList().toArray().length));
        wordColumn.setCellValueFactory(value-> new SimpleObjectProperty<>(value.getValue().getWord()));
        translateColumn.setCellValueFactory(value-> new SimpleObjectProperty<>(value.getValue().getTranslate()));
        groupColumn.setCellValueFactory(value-> new SimpleObjectProperty<>(value.getValue().getGroup()));
        mainModel.openWordBook("");//TODO
        wordTable.setItems(mainModel.getWordList());
    }

    public static void play(){
        launch();
    }

    public MainModel getMainModel() {
        return mainModel;
    }

    public Stage getStage() {
        return stage;
    }
}
