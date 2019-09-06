package ru.putnik.wordnote;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Создано 04.08.2019 в 20:50
 */
public class AlertCall {
    public static void callAlert(Alert.AlertType type,String title,String header,String content){
        Alert alert=new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        try {
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon/mainIcon.png"));
        }catch (Exception ex){
            System.out.println("Ошибка загрузки иконки");
        }
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);

        alert.show();

    }
    public static Optional<ButtonType> callWaitAlert(Alert.AlertType type,String title,String header,String content){
        Alert alert=new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        try {
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon/mainIcon.png"));
        }catch (Exception ex){
            System.out.println("Ошибка загрузки иконки");
        }
        ((Stage)alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);

        return alert.showAndWait();
    }
    public static Optional<ButtonType> callConfirmationAlert(String title, String header, String content){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        try {
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("icon/mainIcon.png"));
        }catch (Exception ex){
            System.out.println("Ошибка загрузки иконки");
        }
        ((Stage) alert.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);

        return alert.showAndWait();
    }
}
