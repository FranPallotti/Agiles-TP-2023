package isi.agiles.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import isi.agiles.App;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.dto.TitularDTO;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.entidad.ClaseLicencia;
import isi.agiles.entidad.EstadoLicencia;
import isi.agiles.entidad.TipoClasesLicencia;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorClaseLicencia;
import isi.agiles.logica.GestorLicencia;
import isi.agiles.util.DatosInvalidosException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
    private ComboBox<ClaseLicenciaDTO> campoClaseLicencia;

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
    
    
    private GestorClaseLicencia gestorClase= new GestorClaseLicencia();
    private GestorLicencia gestorLicencia = new GestorLicencia();


    @FXML
    void renovarCliqueado(ActionEvent event) {
        //Calculamos vigencia y costo
        try{
            if(checkboxRenovarVigencia.isSelected()){
                licencia.setClaseLic(campoClaseLicencia.getSelectionModel().getSelectedItem());
                gestorLicencia.calcularVigenciaLicencia(licencia);
                licencia.setCosto(gestorLicencia.getCostoLicencia(licencia));
                campoCostoVigencia.setText(String.valueOf(licencia.getCosto()));
                campoVIgenteHasta.setValue(licencia.getFinVigencia());
                campoVigenteDesde.setValue(licencia.getInicioVigencia());
                
                
    
    
                campoVIgenteHasta.setVisible(true);
                campoVigenteDesde.setVisible(true);
    
            }
            else{
                campoVIgenteHasta.setVisible(false);
                campoVigenteDesde.setVisible(false);
            }
                
        }
        catch(ObjetoNoEncontradoException e){
            errorRenovarLicencia();
        }
        
        
        
    }

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
        campoApellido.setText(licencia.getTitular().getApellido());
        campoNombre.setText(licencia.getTitular().getNombre());
        
        campoClaseLicencia.setValue(licencia.getClaseLic());
        
        //campoVigenteDesde.setValue(licencia.getInicioVigencia());
        //campoVIgenteHasta.setValue(licencia.getFinVigencia());
        
        campoNroDoc.setText(licencia.getTitular().getNroDoc());
        campoCostoVigencia.setText(licencia.getCosto().toString());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        campoVIgenteHasta.setVisible(false);
        campoVigenteDesde.setVisible(false);
        try{
            campoClaseLicencia.setItems(FXCollections.observableArrayList(gestorClase.getAllDTOs()));
        }
        catch(ObjetoNoEncontradoException e){
            faltanDatosLicencias();
        }
        
        
        
        
    }

    private void faltanDatosLicencias(){
            Alert alert = new Alert(AlertType.WARNING, "Advertencia: No existen clases de licencia que se puedan emitir ", ButtonType.OK);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
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
        void claseLicenciaSeleccionada(ActionEvent event) {
            try{
                if(checkboxRenovarVigencia.isSelected()){
                    //TODO validar campos de texto
                    campoVIgenteHasta.setVisible(false);
                    campoVIgenteHasta.setVisible(false);
                    campoCostoVigencia.setVisible(false);

                    licencia.setClaseLic(campoClaseLicencia.getSelectionModel().getSelectedItem());
                    
                    gestorLicencia.calcularVigenciaLicencia(licencia);
                    licencia.setCosto(gestorLicencia.getCostoLicencia(licencia));
                    campoCostoVigencia.setText(String.valueOf(licencia.getCosto()));
                    campoVIgenteHasta.setValue(licencia.getFinVigencia());
                    campoVigenteDesde.setValue(licencia.getInicioVigencia());
                    campoVIgenteHasta.setVisible(true);
                    campoVIgenteHasta.setVisible(true);
                    campoCostoVigencia.setVisible(true);
                    
        
                }
                else{
                    
                    campoCostoVigencia.setVisible(false);

                    licencia.setClaseLic(campoClaseLicencia.getSelectionModel().getSelectedItem());
                    
                    gestorLicencia.calcularVigenciaLicencia(licencia);
                    licencia.setCosto(gestorLicencia.getCostoLicencia(licencia));
                    campoCostoVigencia.setText(String.valueOf(licencia.getCosto()));
                    
                    
                    campoCostoVigencia.setVisible(true);
                }
                
                    
            }
            catch(ObjetoNoEncontradoException e){
                errorRenovarLicencia();
            }
        }
        @FXML
    void confirmarCliqueado(ActionEvent event) {
        if(!datosInvalidos(this.licencia)){
            licencia.getTitular().setNombre(campoNombre.getText());
            licencia.getTitular().setApellido(campoApellido.getText());
            licencia.getTitular().setNroDoc(campoNroDoc.getText());
            licencia.setClaseLic(campoClaseLicencia.getSelectionModel().getSelectedItem());
            
            //TODO backend de renovar licencia
            //TODO que tire una excepcion si no califica para el tipo de licencia
            System.out.println("HOLA");
        }
        else{
            datosInvalidosWarning();
        }
    }
    public Boolean datosInvalidos(LicenciaDTO l){
        Boolean invalidos = false;
        invalidos |=this.nombreInvalido(campoNombre.getText());
        invalidos |=this.apellidoInvalido(campoApellido.getText());
        invalidos |=this.dniInvalido(campoNroDoc.getText(),l.getTitular().getTipoDoc());
        invalidos |= this.claseNoSeleccionada(campoClaseLicencia.getSelectionModel().getSelectedItem());
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
    public Boolean claseNoSeleccionada(ClaseLicenciaDTO c){
        Boolean invalido =  false;
        if(c == null){
            invalido = true;
            errorClaseLicencia.setVisible(true);
        }
        else{
            errorClaseLicencia.setVisible(false);
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
            Alert alert = new Alert(AlertType.WARNING, "Advertencia: Revise el formato de los datos ingresados ", ButtonType.OK);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
        }

}
