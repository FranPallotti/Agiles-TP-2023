package isi.agiles.ui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Action;

import isi.agiles.App;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.Titular;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorLicencia;
import isi.agiles.logica.GestorTitular;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.control.Label;

public class EmitirLicenciaController implements Initializable{
    private TitularDTO titular;

    @FXML
    private TableColumn<TitularDTO,String> apellidosColumn;

    @FXML
    private Button botonBuscar;
    @FXML
    private Button botonVolverAtras;

    @FXML
    private Button botonEmitir;

    @FXML
    private TextField nroDoc;
    @FXML
    private ComboBox<TipoDoc> tipoDoc;

 
    @FXML
    private TextField campoApellido;

    @FXML
    private TextField campoFecha;

    @FXML
    private TextField campoNombre;

    @FXML
    private ComboBox<ClaseLicenciaDTO> campoClaseLicencia;

     @FXML
    private TextArea campoObservaciones;


    @FXML
    private Label errorFormatoObservaciones;

    @FXML
    private Label errorFaltaClaseLicencia;

    

   

   

    


    @FXML
    private void handleExit(ActionEvent event) throws IOException{
        try{
            Stage currentStage = (Stage) botonVolverAtras.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void inicializarTabla(){

        campoApellido.setText("");
        campoFecha.setText("");
        campoNombre.setText("");
        //campoTipoLicencia.setValue(null);

        
    }

    public void actualizarTabla(){
        if(titular==null){
            
            campoApellido.setVisible(false);
            campoFecha.setVisible(false);
            campoNombre.setVisible(false);
            campoClaseLicencia.setVisible(false);
            //no encontrados msg true
            return;
        }

        
        campoApellido.setText(titular.getApellido());
        campoFecha.setText(titular.getFechaNacimiento().toString());
        campoNombre.setText(titular.getNombre());

        campoApellido.setVisible(true);
        campoFecha.setVisible(true);
        campoNombre.setVisible(true);
        campoClaseLicencia.setVisible(true);
        
        
        // no encontrados msg false
        
        return;
    }
    public TitularDTO getTitularDTO(){
        TitularDTO dto = new TitularDTO();
        dto.setNroDoc(this.nroDoc.getText());
        dto.setTipoDoc(this.tipoDoc.getValue());
        return dto;

    }

    public void buscarCliqueado(){
    try{
        titular=GestorTitular.getTitularDTOByDocumento(this.getTitularDTO().getNroDoc(), this.getTitularDTO().getTipoDoc());

        this.actualizarTabla();
    }
    catch(ObjetoNoEncontradoException e){
        e.printStackTrace();
    }
       
    }

    public void poblarTipoDoc() {
        tipoDoc.setItems(FXCollections.observableArrayList(TipoDoc.values()));
    }
   
    @FXML
    private void emitirCliqueado(ActionEvent event) throws IOException{

        //validar los datos
        

        LicenciaDTO l = getLicenciaDTO(titular, campoObservaciones.getText(), campoClaseLicencia.getValue());
        

        try{
            GestorLicencia.crearLicencia(l);
        }
        catch(ObjetoNoEncontradoException e){
            e.printStackTrace();
        }
        


        
        
        try{
            Stage currentStage = (Stage) botonEmitir.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            inicializarTabla();
			
			poblarTipoDoc();
			
            campoApellido.setVisible(true);
            campoFecha.setVisible(true);
            campoNombre.setVisible(true);
            //campoClaseLicencia.setVisible(true);

			//tablaTitulares.setVisible(false);
			//noEncontradosMsg.setVisible(false);
			//botonConfirmar.disableProperty().bind(tablaTitulares.getSelectionModel().selectedItemProperty().isNull());
			//confirmarMsg.visibleProperty().bind(clientesTable.visibleProperty());
        }


        public LicenciaDTO getLicenciaDTO(TitularDTO t, String obs, ClaseLicenciaDTO clase){
            LicenciaDTO l = new LicenciaDTO();
            l.setTitular(t);
            l.setObservaciones(obs);
            l.setClase(clase);
            return l;
    
        }


        private void setErroresFalse() {
            errorFormatoObservaciones.setVisible(false);
            errorFaltaClaseLicencia.setVisible(false);
            
        }





}
