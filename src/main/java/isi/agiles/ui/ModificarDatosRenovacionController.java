package isi.agiles.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import isi.agiles.App;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorImpresionFactura;
import isi.agiles.logica.GestorImpresionLicencia;
import isi.agiles.logica.GestorLicencia;
import isi.agiles.logica.GestorTitular;
import isi.agiles.ui.elementos.ErrorLicenciaAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ModificarDatosRenovacionController implements Initializable {

    @FXML
    private Button botonContinuar;

    @FXML
    private Button botonVolverAtras;

    @FXML
    private TextField campoApellido;

    @FXML
    private TextField campoClaseLicencia;

    @FXML
    private TextField campoCostoVigencia;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoNroDoc;

    @FXML
    private DatePicker campoVIgenteHasta;

    @FXML
    private DatePicker campoVigenteDesde;

    @FXML
    private CheckBox checkboxRenovarVigencia;

    @FXML
    private Label labelCostoLicencia;
    // errores
    @FXML
    private Label errorFormatoApellido;

    @FXML
    private Label errorFormatoDni;

    @FXML
    private Label errorFormatoNombre;

    @FXML
    private Label errorClaseLicencia;
    
    // atributos internos

    private LicenciaDTO licencia;

    private GestorLicencia gestorLicencia = new GestorLicencia();

    private GestorTitular gestorTitular = new GestorTitular();

    private GestorImpresionLicencia gestorImpresionLicencia = new GestorImpresionLicencia();

    private GestorImpresionFactura gestorImpresionFactura = new GestorImpresionFactura();
    
    private PDFDisplayerController pdfDisplayerController = new PDFDisplayerController();
    @FXML
    void volverAtrasCliqueado(ActionEvent event) {
         try{
            Stage currentStage = (Stage) botonVolverAtras.getScene().getWindow();
            App.cambiarVentana("RenovarLicencia.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setLicencia(LicenciaDTO l){
        licencia=l;
    }
    public LicenciaDTO getLicenciaDTO(){
        return licencia;
    }

    public void setearDatos(){
        /*
        campoApellido.setText(licencia.getTitular().getApellido());
        campoNombre.setText(licencia.getTitular().getNombre());
        
        campoClaseLicencia.setText(licencia.getClaseLic().toString());
        
        //campoVigenteDesde.setValue(licencia.getInicioVigencia());
        //campoVIgenteHasta.setValue(licencia.getFinVigencia());
        
        campoNroDoc.setText(licencia.getTitular().getNroDoc());
        campoCostoVigencia.setText(licencia.getCosto().toString());
        */
        try{
            campoVIgenteHasta.setVisible(false);
            campoVIgenteHasta.setVisible(false);
            campoCostoVigencia.setVisible(false);
            campoClaseLicencia.setVisible(false);
            campoApellido.setVisible(false);
            campoNombre.setVisible(false);
            campoNroDoc.setVisible(false);
            
            gestorLicencia.calcularVigenciaLicencia(licencia);
            licencia.setCosto(gestorLicencia.getCostoLicencia(licencia));
            campoCostoVigencia.setText(String.valueOf(licencia.getCosto()));
            campoVIgenteHasta.setValue(licencia.getFinVigencia());
            campoVigenteDesde.setValue(licencia.getInicioVigencia());
            campoClaseLicencia.setText(licencia.getClaseLic().toString());
            campoApellido.setText(licencia.getTitular().getApellido());
            campoNombre.setText(licencia.getTitular().getNombre());
            campoNroDoc.setText(licencia.getTitular().getNroDoc());

            campoApellido.setVisible(true);
            campoNombre.setVisible(true);
            campoNroDoc.setVisible(true);
            campoVIgenteHasta.setVisible(true);
            campoVIgenteHasta.setVisible(true);
            campoCostoVigencia.setVisible(true);
            campoClaseLicencia.setVisible(true);
        }
        catch(ObjetoNoEncontradoException e){
            errorRenovarLicencia();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { 
        
    }

        
    private void errorRenovarLicencia(){
        Alert alert = new Alert(AlertType.ERROR, "Error: Ocurrio un inconveniente al renovar su licencia", ButtonType.OK);
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
    void confirmarCliqueado(ActionEvent event) {
        try{
        if(!datosInvalidos(this.licencia)){
            
            licencia.getTitular().setNombre(campoNombre.getText());
            licencia.getTitular().setApellido(campoApellido.getText());
            licencia.getTitular().setNroDoc(campoNroDoc.getText());
            //En el caso de que sea una renovación temprana se marcará como Expirada la licencia vieja.
            gestorTitular.actualizarTitular(licencia.getTitular());
            gestorLicencia.renuevaLicencia(licencia);
            renovacionExitosa();
            File licenciaPdf = gestorImpresionLicencia.imprimirLicencia(licencia);
            pdfDisplayerController.mostrarPdf(licenciaPdf);
            File facturaPdf = gestorImpresionFactura.imprimirFactura(licencia);
            pdfDisplayerController.mostrarPdf(facturaPdf);
            volverMenuPrincipal();
        }
        else{
            datosInvalidosWarning();
        }
        }catch(ObjetoNoEncontradoException ex){
            String msg = "El titular buscado no fue encontrado.";
            ErrorLicenciaAlert alert = new ErrorLicenciaAlert(msg);
            alert.showAndWait();
        }catch(FileNotFoundException | URISyntaxException ex){
            ex.printStackTrace();
            String msg = "Hubo un error imprimiendo su .pdf. Intentelo nuevamente mas tarde.";
            ErrorLicenciaAlert alert = new ErrorLicenciaAlert(msg);
            alert.showAndWait();
        }catch(Exception ex){
            ex.printStackTrace();
            String msg = "Hubo un problema. Intentelo más tarde";
            ErrorLicenciaAlert alert = new ErrorLicenciaAlert(msg);
            alert.showAndWait();
        }
    }

    public Boolean datosInvalidos(LicenciaDTO l){
        Boolean invalidos = false;
        invalidos |=this.nombreInvalido(campoNombre.getText());
        invalidos |=this.apellidoInvalido(campoApellido.getText());
        invalidos |=this.dniInvalido(campoNroDoc.getText(),l.getTitular().getTipoDoc());
        return invalidos;
    }

    public Boolean nombreInvalido(String nombre){
        Boolean invalido = false;
        if(nombre == null || nombre.matches("^\\s+$") || nombre.isBlank() || nombre.isEmpty()|| nombre.length()>32){
            invalido = true;
            errorFormatoNombre.setVisible(true);
        }
        else{
            errorFormatoNombre.setVisible(false);
        }
        return invalido;
    }

    public Boolean apellidoInvalido(String apellido){
        Boolean invalido =  false;
        if(apellido == null|| apellido.matches("^\\s+$") || apellido.isEmpty() || apellido.isBlank() || apellido.length()>32){
            invalido = true;
            errorFormatoApellido.setVisible(true);
        }
        else{
            errorFormatoApellido.setVisible(false);
        }
        return invalido;
    }

    

    public  Boolean dniInvalido(String num, TipoDoc tipo){
        Boolean invalido = false;

        if(tipo != null && num != null){
            switch (tipo) {
                case DNI:
                    if(!num.matches("^\\d{8}$") || num.isBlank() || num.isEmpty()){
                        invalido=true;
                        errorFormatoDni.setVisible(true);
                    }
                    else{
                        errorFormatoDni.setVisible(false);
                    }
                                        
                break;
                case PASAPORTE:
                    if(!num.matches("^[a-zA-Z]{3}\\d{6}$") || num.isBlank() || num.isEmpty() ){
                        invalido=true;
                        errorFormatoDni.setVisible(true);
                    }
                    else{
                        errorFormatoDni.setVisible(false);
                    }
                break;
                default:
                    invalido=true;
            }
        }
        return invalido;
    }

    private void datosInvalidosWarning(){
        Alert alert = new Alert(AlertType.WARNING, "Advertencia: Revise el formato de los datos ingresados.", ButtonType.OK);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }

    private void renovacionExitosa() {
        Alert alert = new Alert(AlertType.INFORMATION, "¡Información: Licencia renovada con éxito.!", ButtonType.OK);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }

    private void volverMenuPrincipal() {
        try{
            Stage currentStage = (Stage) botonContinuar.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
