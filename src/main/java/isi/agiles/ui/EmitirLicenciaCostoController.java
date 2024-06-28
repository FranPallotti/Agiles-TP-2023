package isi.agiles.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import isi.agiles.App;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.excepcion.NoCumpleCondicionesLicenciaException;
import isi.agiles.excepcion.NoPuedeEmitirExisteLicenciaException;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorImpresionFactura;
import isi.agiles.logica.GestorImpresionLicencia;
import isi.agiles.logica.GestorLicencia;
import isi.agiles.ui.elementos.ErrorLicenciaAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EmitirLicenciaCostoController implements Initializable{

    @FXML
    private Button botonEmitir;
    @FXML
    private Button botonVolverAtras;

    @FXML
    private TextField campoApellido;

    @FXML
    private TextField campoClaseLicencia;

    @FXML
    private TextField campoCostoVigencia;

    @FXML
    private DatePicker campoFechaNacimiento;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoNumeroDoc;

    @FXML
    private TextField campoTipoDocumento;

     @FXML
    private DatePicker campoVigenteDesde;

    @FXML
    private DatePicker campoVigenteHasta;

    private LicenciaDTO licencia;

    private GestorLicencia gestorLicencia = new GestorLicencia();

    private PDFDisplayerController pdfDisplayerController = new PDFDisplayerController();
    
    GestorImpresionLicencia gestorImpresionLicencia = new GestorImpresionLicencia();

    GestorImpresionFactura gestorImpresionFactura = new GestorImpresionFactura();

    public LicenciaDTO getLicencia() {
        return licencia;
    }

    public void setLicencia(LicenciaDTO l) {
        this.licencia=l;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //setearDatos();

        campoClaseLicencia.setVisible(true);
        campoApellido.setVisible(true);
        campoFechaNacimiento.setVisible(true);
        campoNombre.setVisible(true);
    }
    
    public void setearDatos(){
        campoApellido.setText(licencia.getTitular().getApellido());
        campoNombre.setText(licencia.getTitular().getNombre());
        campoClaseLicencia.setText(licencia.getClaseLic().toString());
        campoFechaNacimiento.setValue(licencia.getTitular().getFechaNacimiento());
        campoVigenteDesde.setValue(licencia.getInicioVigencia());
        campoVigenteHasta.setValue(licencia.getFinVigencia());
        campoTipoDocumento.setText(licencia.getTitular().getTipoDoc().toString());
        campoNumeroDoc.setText(licencia.getTitular().getNroDoc());
        campoCostoVigencia.setText(licencia.getCosto().toString());
    }

    @FXML
    private void handleExit(ActionEvent event) throws IOException{
        try{
            Stage currentStage = (Stage) botonVolverAtras.getScene().getWindow();
            App.cambiarVentana("EmitirLicencia.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    private void emitirCliqueado(ActionEvent e){
        try{
            gestorLicencia.altaLicencia(licencia);
            licenciaDadaDeAlta();
            File licenciaPdf = gestorImpresionLicencia.imprimirLicencia(licencia);
            pdfDisplayerController.mostrarPdf(licenciaPdf);
            File facturaPdf = gestorImpresionFactura.imprimirFactura(licencia);
            pdfDisplayerController.mostrarPdf(facturaPdf);
            Stage currentStage = (Stage) botonVolverAtras.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);

        }catch(NoCumpleCondicionesLicenciaException ex){
            String msg = "El titular no cumple con las condiciones para obtener esta clase de licencia";
            ErrorLicenciaAlert alert = new ErrorLicenciaAlert(msg);
            alert.showAndWait();
        }catch(ObjetoNoEncontradoException ex){
            String msg = "El titular buscado no fue encontrado.";
            ErrorLicenciaAlert alert = new ErrorLicenciaAlert(msg);
            alert.showAndWait();
        }catch(NoPuedeEmitirExisteLicenciaException ex){
            String msg = "Si el titular tiene una licencia vigente, no pueden emitirse otras con la misma clase.";
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

    private void licenciaDadaDeAlta(){
            Alert alert = new Alert(AlertType.INFORMATION, "¡Licencia dada de alta con Éxito! ", ButtonType.OK);
            alert.setTitle("Exito al emitir licencia");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
        }
}
