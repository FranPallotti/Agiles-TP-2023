package isi.agiles.ui;

import java.io.File;
import java.io.IOException;

import com.dansoftware.pdfdisplayer.PDFDisplayer;
import com.dansoftware.pdfdisplayer.PdfJSVersion;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class PDFDisplayerController {

    private final String errMessage = "Hubo un problema al imprimir el archivo .pdf.";
    
    public void mostrarPdf(File pdf){
        try {  
            Stage pdfDisplayStage = new Stage();
            /*Se usa un displayer con una version anterior de pdfJS porque la ultima version trae
             * problemas para visualizar los botones de la toolbar
             */
            PDFDisplayer displayer = new PDFDisplayer(PdfJSVersion._2_2_228);
            pdfDisplayStage.setTitle("Visualizando " + pdf.getName());
            /*Para crear el nodo JavaFX (mas especificamente un objeto Parent) que contiene
             *el displayer se usa la función toNode()
             */
            pdfDisplayStage.setScene(new Scene(displayer.toNode()));
            displayer.loadPDF(pdf);
            pdfDisplayStage.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, errMessage, ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
}
