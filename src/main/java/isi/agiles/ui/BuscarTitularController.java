package isi.agiles.ui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Action;

import isi.agiles.App;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.Titular;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorTitular;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BuscarTitularController implements Initializable{
    private List<TitularDTO> titulares = new ArrayList<>();

    @FXML
    private TableColumn<TitularDTO,String> apellidosColumn;

    @FXML
    private Button botonBuscar;
    @FXML
    private Button botonVolverAtras;

    @FXML
    private Button botonConfirmar;

    @FXML
    private TextField nroDoc;
    @FXML
    private ComboBox<TipoDoc> tipoDoc;

    @FXML
    private TableColumn<TitularDTO,String> nombresCollumn;


    @FXML
    private TableColumn<TitularDTO,String> numDocColumn;

    @FXML
    private TableView<TitularDTO> tablaTitulares;

   

    @FXML
    private TableColumn<TitularDTO,TipoDoc> tipoDocColumn;

    


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

        apellidosColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        nombresCollumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoDocColumn.setCellValueFactory(new PropertyValueFactory<>("tipoDoc"));
        numDocColumn.setCellValueFactory(new PropertyValueFactory<>("nroDocumento"));
        
    }

    public void actualizarTabla(){
        if(titulares.isEmpty()){
            tablaTitulares.setVisible(false);
            //no encontrados msg true
            return;
        }
        ObservableList<TitularDTO> datosTabla = FXCollections.observableArrayList(titulares);
        tablaTitulares.setItems(datosTabla);
        // no encontrados msg false
        tablaTitulares.setVisible(true);
        return;
    }
    public TitularDTO getTitularDTO(){
        TitularDTO dto = new TitularDTO();
        dto.setDocumento(this.nroDoc.getText());
        dto.setTipoDoc(this.tipoDoc.getValue());
        return dto;

    }
    public void buscarCliqueado(){
        titulares = GestorTitular.buscarTitularDTO(this.getTitularDTO());
        this.actualizarTabla();
    }

    public void poblarTipoDoc() {
        tipoDoc.setItems(FXCollections.observableArrayList(TipoDoc.values()));
    }

   
    @FXML
    private void confirmarCliqueado(ActionEvent event) throws IOException{
        //Mockup
        //TitularDTO seleccionado = GestorTitular.getTitularDTO(GestorTitular.getByDNI("123456789")) ;
        
        TitularDTO seleccionado = tablaTitulares.getSelectionModel().getSelectedItem();
        
        /* 
        FXMLLoader loader = new FXMLLoader();
        */

        EmitirLicenciaController controllerE = new EmitirLicenciaController();
        controllerE.setTitularDTO(seleccionado);
        /*loader.setController(altaPolizaC);
        
        loader.setLocation(getClass().getResource("../altapoliza/AltaPolizaFormularioPoliza.fxml"));
        AnchorPane form = loader.load();
        
        altaPolizaC.setActual(form);
        altaPolizaC.setAnterior(actual);
        */
        
        try{
            Stage currentStage = (Stage) botonConfirmar.getScene().getWindow();
            App.cambiarVentana("EmitirLicencia.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            inicializarTabla();
			
			poblarTipoDoc();
			
			tablaTitulares.setVisible(false);
			//noEncontradosMsg.setVisible(false);
			//botonConfirmar.disableProperty().bind(tablaTitulares.getSelectionModel().selectedItemProperty().isNull());
			//confirmarMsg.visibleProperty().bind(clientesTable.visibleProperty());
        }





}
