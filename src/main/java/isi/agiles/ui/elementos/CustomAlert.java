package isi.agiles.ui.elementos;

import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class CustomAlert extends Alert {
    public CustomAlert(String msg){
        super(AlertType.ERROR, msg, ButtonType.CLOSE);
        this.setTitle("Error al emitir licencia");
        this.setHeaderText(null);
        this.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        this.getDialogPane().lookupButton(ButtonType.CLOSE).setCursor(Cursor.HAND);
        this.setResizable(false);
    }
}
