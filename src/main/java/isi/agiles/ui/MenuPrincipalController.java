package isi.agiles.ui;

import isi.agiles.App;
import isi.agiles.entidad.TipoRol;
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
    private AnchorPane frameMenuPrincipal;

    @FXML
    private Label labelMenuPrincipal;

    @FXML
    private ImageView logoStaFeDer;

    @FXML
    private ImageView logoStaFeIzq;


    public void informacionFuncionalidadNoDesarrollada(){
        Alert alert = new Alert(AlertType.INFORMATION, "Importante: La implementación de esta funcionalidad todavía no está desarrollada.", ButtonType.OK);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Times New Roman", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.showAndWait();
    }

    private void errorCredencialesIncorrectas() {
        Alert alert = new Alert(AlertType.ERROR, "Advertencia: No posee las credenciales necesarias para acceder a esta opción", ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Times New Roman", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.showAndWait();
    }

    @FXML
    void accionDarAltaTitular(ActionEvent event) {
        
    }

    @FXML
    void accionDarAltaUsuario(ActionEvent event) {
        if(App.getUsuarioLogueado().getRol().equals(TipoRol.ADMINISTRADOR)){
            
        }else{
            errorCredencialesIncorrectas();
        }
    }

    @FXML
    void accionEmitirLicencia(ActionEvent event) {

    }

    @FXML
    void accionImprimirLicencia(ActionEvent event) {
        informacionFuncionalidadNoDesarrollada();
    }

    @FXML
    void accionListarLicenciasExpiradas(ActionEvent event) {
        //Codigo para ir a esa pantalla
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
        informacionFuncionalidadNoDesarrollada();
    }

    @FXML
    void accionModificarDatosUsuario(ActionEvent event) {
        if(App.getUsuarioLogueado().getRol().equals(TipoRol.ADMINISTRADOR)){
            informacionFuncionalidadNoDesarrollada();
        }else{
            errorCredencialesIncorrectas();
        }
    }

    @FXML
    void accionRenovarLicencia(ActionEvent event) {
        informacionFuncionalidadNoDesarrollada();
    }

}

/*  PARA PASAR ENTRE PANTALLAS  
		AltaPolizaInicioController controller = new AltaPolizaInicioController();
		
		FXMLLoader loader = new FXMLLoader();
		
		loader.setController(controller);
		
		loader.setLocation(getClass().getResource("../altapoliza/AltaPolizaInicio.fxml"));
		
		AnchorPane altaPoliza = loader.load();
		
		controller.setActual(altaPoliza);
		
		App.switchScreenTo(altaPoliza); */