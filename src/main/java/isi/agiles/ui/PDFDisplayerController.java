package isi.agiles.ui;

import java.io.File;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import com.dansoftware.pdfdisplayer.PdfJSVersion;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PDFDisplayerController {

    private final String errMessage = "Hubo un problema al imprimir el archivo .pdf.";
    
    public void mostrarPdf(File pdf){
        Stage pdfDisplayStage = new Stage();
        Button recargarButton = new Button("Recargar .pdf");       
        pdfDisplayStage.setTitle("Visualizando " + pdf.getName());
        /*Para crear el nodo JavaFX (mas especificamente un objeto Parent) que contiene
         * el displayer se usa la función toNode()
         */
        VBox displayerConBotonBox = new VBox(generarDisplayer(pdf),recargarButton);
        displayerConBotonBox.setStyle("-fx-background-color: #424242");
        displayerConBotonBox.setAlignment(Pos.CENTER);
        pdfDisplayStage.setScene(new Scene(displayerConBotonBox));
        /*Cuando apretas el boton "Recargar" recarga todo el visor. Sirve cuando el visor falla (cosa que
         * a veces pasa y no se porque)
         */
        recargarButton.setOnAction(ev -> displayerConBotonBox.getChildren().set(0,generarDisplayer(pdf)));
        pdfDisplayStage.showAndWait();
    }

    private Parent generarDisplayer(File pdf){
        try { 
            /*Se usa un displayer con una version anterior de pdfJS porque la ultima version trae
             * problemas para visualizar los botones de la toolbar
             */
            PDFDisplayer displayer = new PDFDisplayer(PdfJSVersion._2_2_228);
            /*Para crear el nodo JavaFX (mas especificamente un objeto Parent) que contiene
             *el displayer se usa la función toNode()
             */
            displayer.loadPDF(pdf);
            Parent node = displayer.toNode();
            VBox.setVgrow(node,Priority.ALWAYS);
            return node;
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, errMessage, ButtonType.CLOSE);
            alert.showAndWait();
        }
        return null;
    }
}
