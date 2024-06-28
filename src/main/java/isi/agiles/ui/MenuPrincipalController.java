package isi.agiles.ui;

import java.io.File;
import java.io.IOException;

import isi.agiles.App;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.entidad.TipoRol;
import isi.agiles.logica.GestorImpresionLicencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuPrincipalController {
    @FXML
    private Button botonDarAltaTitular;

    @FXML
    private Button botonDarAltaUsuario;

    @FXML
    private Button botonEmitirLicencia;

    @FXML
    private Button botonImprimirLicencia;

    @FXML
    private Button botonListarLicenciasExpiradas;

    @FXML
    private Button botonListarLicenciasVigentes;

    @FXML
    private Button botonModificarCostorsLicencias;

    @FXML
    private Button botonModificarDatosTitular;

    @FXML
    private Button botonModificarDatosUsuario;

    @FXML
    private Button botonRenovarLicencia;
    @FXML
    private Button botonEmitirCopia;

    @FXML
    private AnchorPane frameMenuPrincipal;

    @FXML
    private Label labelMenuPrincipal;

    @FXML
    private ImageView logoStaFe;

    private PDFDisplayerController pdfDisplayerController = new PDFDisplayerController();
    
    GestorImpresionLicencia gestorImpresionLicencia = new GestorImpresionLicencia();


    public void informacionFuncionalidadNoDesarrollada(){
        Alert alert = new Alert(AlertType.INFORMATION, "Importante: La implementación de esta funcionalidad todavía no está desarrollada.", ButtonType.OK);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }

    private void errorCredencialesIncorrectas() {
        Alert alert = new Alert(AlertType.ERROR, "Advertencia: No posee las credenciales necesarias para acceder a esta opción", ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }

    @FXML
    void accionDarAltaTitular(ActionEvent event) {
        try {
            Stage currentStage = (Stage) botonDarAltaTitular.getScene().getWindow();
            App.cambiarVentana("DarAltaTitular.fxml", currentStage);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void accionDarAltaUsuario(ActionEvent event) {
        if(App.getUsuarioLogueado().getRol().equals(TipoRol.ADMINISTRADOR)){
            try{
                Stage currentStage = (Stage) botonDarAltaUsuario.getScene().getWindow();
                // Cami> cambie botonDarAltaTitular por botonDarAltaUsuario
                App.cambiarVentana("AltaDeUsuario.fxml", currentStage);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            errorCredencialesIncorrectas();
        }
    }

    @FXML
    void accionEmitirLicencia(ActionEvent event) {
        try{
            Stage currentStage = (Stage) botonEmitirLicencia.getScene().getWindow();
            App.cambiarVentana("EmitirLicencia.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    @FXML
    void accionImprimirLicencia(ActionEvent event) {
        informacionFuncionalidadNoDesarrollada();
    }

    @FXML
    void accionListarLicenciasExpiradas(ActionEvent event) {
        try{
            Stage currentStage = (Stage) botonListarLicenciasExpiradas.getScene().getWindow();
            App.cambiarVentana("ListadoExpiradas.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void accionListarLicenciasVigentes(ActionEvent event) {
        informacionFuncionalidadNoDesarrollada();
    }

    @FXML
    void accionModificarCostosLicencias(ActionEvent event) {
        if(App.getUsuarioLogueado().getRol().equals(TipoRol.ADMINISTRADOR)){
            informacionFuncionalidadNoDesarrollada();
        }else{
            errorCredencialesIncorrectas();
        }
    }

    @FXML
    void accionModificarDatosTitular(ActionEvent event) {
        try{
            Stage currentStage = (Stage) botonListarLicenciasExpiradas.getScene().getWindow();
            App.cambiarVentana("ModificarDatosTitular.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void accionModificarDatosUsuario(ActionEvent event) {
        if(App.getUsuarioLogueado().getRol().equals(TipoRol.ADMINISTRADOR)){
            try{
                Stage currentStage = (Stage) botonListarLicenciasExpiradas.getScene().getWindow();
                App.cambiarVentana("ModificarUsuario.fxml", currentStage);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }else{
            errorCredencialesIncorrectas();
        }
    }

    @FXML
    void accionRenovarLicencia(ActionEvent event) {
        try{
            Stage currentStage = (Stage) botonRenovarLicencia.getScene().getWindow();
            App.cambiarVentana("RenovarLicencia.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void accionEmitirCopia(ActionEvent event) {
        try{
            Stage currentStage = (Stage) botonEmitirCopia.getScene().getWindow();
            App.cambiarVentana("EmitirCopia.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}