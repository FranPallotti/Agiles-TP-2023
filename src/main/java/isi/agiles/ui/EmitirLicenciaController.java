package isi.agiles.ui;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import isi.agiles.App;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.excepcion.NoCumpleCondicionesLicenciaException;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorClaseLicencia;
import isi.agiles.logica.GestorLicencia;
import isi.agiles.logica.GestorTitular;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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
    private Label errorFaltaTitular;

    private GestorLicencia gestorLicencia = new GestorLicencia();

    private GestorClaseLicencia gestorClaseLic = new GestorClaseLicencia();

    private GestorTitular gestorTitular = new GestorTitular();

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

    public void inicializarTabla()throws ObjetoNoEncontradoException{
        
        campoApellido.setText("");
        campoFecha.setText("");
        campoNombre.setText("");
        
        campoClaseLicencia.setItems(FXCollections.observableArrayList(gestorClaseLic.getAllDTOs()));
       // campoClaseLicencia.getItems().addAll(opClaseLicencia);
    }

    public void actualizarTabla(){
        if(titular==null){
            
            campoApellido.setVisible(false);
            campoFecha.setVisible(false);
            campoNombre.setVisible(false);
            //campoClaseLicencia.setVisible(false);
            //no encontrados msg true
            return;
        }

        
        campoApellido.setText(titular.getApellido());
        campoFecha.setText(titular.getFechaNacimiento().toString());
        campoNombre.setText(titular.getNombre());

        campoApellido.setVisible(true);
        campoFecha.setVisible(true);
        campoNombre.setVisible(true);
        //campoClaseLicencia.setVisible(true);
        
        
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
        titular = gestorTitular.getTitularDTOByDocumento(this.getTitularDTO().getNroDoc(), this.getTitularDTO().getTipoDoc());

        this.actualizarTabla();
    }
    catch(ObjetoNoEncontradoException e){
        titularNoEncontrado();
        e.printStackTrace();
    }
       
    }

    public void poblarTipoDoc() {
        tipoDoc.setItems(FXCollections.observableArrayList(TipoDoc.values()));
    }
    private void poblarTipoLicencia(){
        //campoClaseLicencia.setItems(FXCollections.observableArrayList(TipoClasesLicencia.values()));
    }
   
    @FXML
    private void emitirCliqueado(ActionEvent event) throws IOException{

              

        if(validarDatos()){
            //campoClaseLicencia.getValue()
            LicenciaDTO l = getLicenciaDTO(titular, campoObservaciones.getText(), campoClaseLicencia.getSelectionModel().getSelectedItem());
        

            try{
                gestorLicencia.altaLicencia(l);
            }
            catch(ObjetoNoEncontradoException e){
                e.printStackTrace();
                faltanDatos();

            }
            catch(NoCumpleCondicionesLicenciaException n){
                n.printStackTrace();
                noCumpleCondicionesLicencia();

            }
        }
        

        /*
        //popup y volver al menu principal hacer
        try{
            Stage currentStage = (Stage) botonEmitir.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        */
    }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            poblarTipoDoc();
            //poblarTipoLicencia();

            try{

                inicializarTabla();
			
			    
            }
            catch(ObjetoNoEncontradoException e){
                this.faltanDatos();
                e.printStackTrace();
            }
            
			
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
            l.setClaseLic(clase);
            return l;
    
        }

        private void setErroresFalse() {
            errorFormatoObservaciones.setVisible(false);
            errorFaltaClaseLicencia.setVisible(false);
            errorFaltaTitular.setVisible(false);
            
        }

        public Boolean validarDatos(){
            boolean datosValidos=false;
            
            if(titular!=null){
                errorFaltaTitular.setVisible(false);
                if(validarObservaciones(campoObservaciones.getText())){
                    if(campoClaseLicencia.getSelectionModel().selectedItemProperty().isNotNull().get()){
                        datosValidos=true;
                    }
                    else{
                        errorFaltaClaseLicencia.setVisible(true);
                    }
                }
                else{
                    errorFormatoObservaciones.setVisible(true);
                }
            }
            else{
                errorFaltaTitular.setVisible(true);
            }

            return datosValidos;
        }


        private void titularNoEncontrado(){

            Alert alert = new Alert(AlertType.ERROR, "Advertencia: Titular no encontrado", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Times New Roman", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
        }

        private void faltanDatos(){
            Alert alert = new Alert(AlertType.ERROR, "Advertencia: Faltan datos ", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Times New Roman", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
        }

        private void noCumpleCondicionesLicencia(){
            Alert alert = new Alert(AlertType.ERROR, "Advertencia: El titular no cumple con las condiciones para la licencia", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Times New Roman", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
        }
        private Boolean validarObservaciones(String o){
            Pattern patronInvalido = Pattern.compile(
            "[\\x00-\\x1F\\x7F]|--|;|'|\"|\\\\|/|\\*|\\b(SELECT|INSERT|UPDATE|DELETE|DROP|TRUNCATE|ALTER|CREATE|GRANT|REVOKE)\\b",
            Pattern.CASE_INSENSITIVE);
            if (o == null) {
                o=new String();
                return true;
            }
            if (o.length() > 1024) {
                return false;
            }
            if (patronInvalido.matcher(o).find()) {
                return false;
            }
            return true;
        }
        






}
