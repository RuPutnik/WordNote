package ru.putnik.wordnote.controller;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.putnik.wordnote.model.MainModel;
import ru.putnik.wordnote.pojo.Word;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Создано 01.08.2019 в 16:43
 */
public class MainController extends Application implements Initializable {
    private MainModel mainModel=new MainModel();
    private SettingController settingController=new SettingController(this);
    private AddEditController addEditController=new AddEditController(this);
    private GroupManagerController groupManagerController=new GroupManagerController(this);

    private static Stage stage;
    private String pathToWordFile;
    private String pathToGroupFile;

    @FXML
    private MenuItem addWord;
    @FXML
    private MenuItem editWord;
    @FXML
    private MenuItem createWordbook;
    @FXML
    private MenuItem openWordbook;
    @FXML
    private MenuItem deleteWord;
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
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem deleteWordbook;
    @FXML
    private MenuItem deleteAllWordMenuItem;
    @FXML
    private TableView<Word> wordTable;
    @FXML
    private TableColumn<Word,Integer> indexColumn;
    @FXML
    private TableColumn<Word,String> wordColumn;
    @FXML
    private TableColumn<Word,String> translateColumn;
    @FXML
    private TableColumn<Word,String> groupColumn;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        Parent parent=FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/mainView.fxml")));

        primaryStage.setScene(new Scene(parent));
        try {
            primaryStage.getIcons().add(new Image("icon/mainIcon.png"));
        }catch (Exception ex){
            System.out.println("Нет иконки главного окна");
        }
        primaryStage.setResizable(true);
        primaryStage.setWidth(730);
        primaryStage.setHeight(630);
        primaryStage.show();
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pathToWordFile=settingController.getPathToWordBook();
        pathToGroupFile=settingController.getPathToGroupFile();
        stage.setTitle("Word Note");

        addWordMenuItem.setOnAction(event -> {
            addEditController.addWord();
            if(mainModel.getWordList().size()>0){
                deleteWord.setDisable(false);
                editWord.setDisable(false);
            }
        });
        editWordMenuItem.setOnAction(event -> {addEditController.editWord(wordTable.getSelectionModel().getSelectedIndex());});
        deleteWordMenuItem.setOnAction(event -> {
            int index=wordTable.getSelectionModel().getSelectedIndex();
            if(index!=-1) {
                mainModel.deleteWord(index);
            }else{
                //TODO Сообщить что слово не выбрано
            }
            if(mainModel.getWordList().size()==0){
                deleteWord.setDisable(true);
                editWord.setDisable(true);
            }
        });
        manageGroupsMenuItem.setOnAction(event -> {groupManagerController.createWindow();});
        settingsMenuItem.setOnAction(event -> {settingController.createWindow();});
        addWord.setOnAction(event -> {addWordMenuItem.fire();});
        editWord.setOnAction(event -> {editWordMenuItem.fire();});
        deleteWord.setOnAction(event -> {
            deleteWordMenuItem.fire();
        });

        indexColumn.setCellValueFactory(value-> new SimpleObjectProperty<>(wordTable.getItems().indexOf(value.getValue())+1));
        wordColumn.setCellValueFactory(value-> new SimpleObjectProperty<>(value.getValue().getWord()));
        translateColumn.setCellValueFactory(value-> new SimpleObjectProperty<>(value.getValue().getTranslate()));
        groupColumn.setCellValueFactory(value-> new SimpleObjectProperty<>(value.getValue().getGroup()));
        if(pathToWordFile!=null) {
            if(mainModel.openWordBook(pathToWordFile)){
                stage.setTitle(stage.getTitle() + " [" + pathToWordFile + "]");
            }
        }
        wordTable.setItems(mainModel.getWordList());
        if(mainModel.getWordList().size()>0){
            deleteWord.setDisable(false);
            editWord.setDisable(false);
            addWord.setDisable(false);
        }
        createWordbook.setOnAction(event -> {
            String nameFile=createNewWordbook();
            //TODO дописать вызов окна с требованием ввести имя файла

            String path=mainModel.createWordBook(nameFile);
            if(path!=null) {
                settingController.setPathToWordBook(path);
                addWord.setDisable(false);
            }

        });
        openWordbook.setOnAction(event -> {
            FileChooser chooser=new FileChooser();

            chooser.setTitle("Выберите файл со словарем");
            chooser.setInitialDirectory(new File((System.getenv("USERPROFILE") + "\\Desktop\\")));
            //TODO Установить фильтр на txt файлы
            String path;
            File file=chooser.showOpenDialog(new Stage());
            if(file!=null) {
                path = file.getPath();
                    if (mainModel.openWordBook(path)) {
                        stage.setTitle("Word Note" + " [" + path + "]");
                        addWord.setDisable(false);
                        if (mainModel.getWordList().size() > 0) {
                            deleteWord.setDisable(false);
                            editWord.setDisable(false);
                        }else{
                            deleteWord.setDisable(true);
                            editWord.setDisable(true);
                        }
                    }
            }
        });
        deleteWordbook.setOnAction(event -> {

        });
        deleteAllWordMenuItem.setOnAction(event -> {
            if(mainModel.getWordList().size()>0){
                mainModel.getWordList().clear();
                deleteWord.setDisable(true);
                editWord.setDisable(true);
            }
        });
        exitMenuItem.setOnAction(event -> {
            stage.close();
        });
    }
    private String createNewWordbook(){
        Alert newGroup=new Alert(Alert.AlertType.CONFIRMATION);
        ((Stage)newGroup.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon/mainIcon.png"));
        newGroup.setTitle("Создание словаря");
        newGroup.setHeaderText(null);
        VBox box=new VBox();
        HBox hBox=new HBox();
        Label infoLabel=new Label("Укажите путь до словаря");
        TextField nameField=new TextField(System.getenv("USERPROFILE") + "\\Desktop\\"+"wordbook.txt");
        Button pickFile=new Button("Выбрать папку");
        hBox.getChildren().add(nameField);
        hBox.getChildren().add(pickFile);
        hBox.setSpacing(5);

        box.getChildren().add(infoLabel);
        box.getChildren().add(hBox);

        nameField.setPrefWidth(250);

        pickFile.setOnAction(event -> {
            DirectoryChooser chooser=new DirectoryChooser();

            chooser.setTitle("Выберите директорию");
            chooser.setInitialDirectory(new File((System.getenv("USERPROFILE") + "\\Desktop\\")));
            //TODO Установить фильтр на txt файлы
            nameField.setText(chooser.showDialog(new Stage())+"\\wordbook.txt");
        });

        newGroup.getDialogPane().setContent(box);
        if(newGroup.showAndWait().get()==ButtonType.OK) {
            //if() {//TODO проверка что такая группа уже есть и что название не пустое
            return nameField.getText();
            //}
        }else{
            return null;
        }
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

    public String getPathToWordFile() {
        return pathToWordFile;
    }

    public String getPathToGroupFile() {
        return pathToGroupFile;
    }

    public GroupManagerController getGroupManagerController() {
        return groupManagerController;
    }

    public void setPathToGroupFile(String pathToGroupFile) {
        this.pathToGroupFile = pathToGroupFile;
    }
}
