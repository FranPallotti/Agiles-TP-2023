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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.Parent;

public class EmitirLicenciaController implements Initializable{
    private TitularDTO titular;

    

    @FXML
    private Button botonBuscar;

    @FXML
    private Button botonVolverAtras;

    @FXML
    private Button botonEmitir;

    @FXML
    private TextField campoNroDoc;

    @FXML
    private ComboBox<TipoDoc> campoTipoDoc;
 
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
        botonEmitir.setVisible(false);
        campoClaseLicencia.setItems(FXCollections.observableArrayList(gestorClaseLic.getAllDTOs()));
        campoNombre.setVisible(false);
        campoApellido.setVisible(false);
        campoFecha.setVisible(false);
       
    }

    public void actualizarTabla(){
        if(titular==null){
            
            campoApellido.setVisible(false);
            campoFecha.setVisible(false);
            campoNombre.setVisible(false);
            botonEmitir.setVisible(false);
            
        }
        else{
            campoApellido.setText(titular.getApellido());
            campoFecha.setText(titular.getFechaNacimiento().toString());
            campoNombre.setText(titular.getNombre());
    
            campoApellido.setVisible(true);
            campoFecha.setVisible(true);
            campoNombre.setVisible(true);
            botonEmitir.setVisible(true);
        }
 
    }

    public TitularDTO getTitularDTO(){
        TitularDTO dto = new TitularDTO();
        dto.setNroDoc(this.campoNroDoc.getText());
        dto.setTipoDoc(this.campoTipoDoc.getValue());
        return dto;

    }

    public void buscarCliqueado(){
        
    try{
        if(campoTipoDoc.getValue()==null || campoNroDoc.getText().isEmpty()){
            errorFaltaTitular.setVisible(true);
        }
        else{
            titular = gestorTitular.getTitularDTOByDocumento(this.getTitularDTO().getNroDoc(), this.getTitularDTO().getTipoDoc());
            errorFaltaTitular.setVisible(false);
        }

        

        this.actualizarTabla();
    }
    catch(ObjetoNoEncontradoException e){
        titularNoEncontrado();
        e.printStackTrace();
    }
       
    }

    public void poblarTipoDoc() {
        campoTipoDoc.setItems(FXCollections.observableArrayList(TipoDoc.values()));
    }
    
   
    @FXML
    private void emitirCliqueado(ActionEvent event) throws IOException{
        if(validarDatos()){
            LicenciaDTO l = getLicenciaDTO(titular, campoObservaciones.getText(), campoClaseLicencia.getSelectionModel().getSelectedItem());
            try{
                if(gestorTitular.puedeTenerLicencia(titular,l.getClaseLic())){
                    FXMLLoader loader = new FXMLLoader();
                       
                        loader.setLocation(App.class.getResource("EmitirLicenciaCosto.fxml"));
                        
                        
                        Stage stage = new Stage();
                        stage.setTitle("Confirme los datos y el Costo");
                        
                         
                        Parent root = loader.load();
                        //aca recien me carga el controlador
                        EmitirLicenciaCostoController formularioCosto = loader.getController();
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.getIcons().add(new Image("isi/agiles/logoStaFe.png"));
                        stage.show();

                        gestorLicencia.calcularVigenciaLicencia(l);
                        l.setCosto(gestorLicencia.getCostoLicencia(l));
                        formularioCosto.setLicencia(l);
                        formularioCosto.setearDatos();

                        Stage currentStage = (Stage) this.botonEmitir.getScene().getWindow();
                        currentStage.close();
                }
                else{
                    noCumpleCondicionesLicencia();
                    // TODO resetear datos
                }
            }
            catch(ObjetoNoEncontradoException e){
                // Si no encuentra el objeto a la hora de validar si puede tener licencia o no
                errorEmitirLicencia();
                // TODO resetear datos
                e.printStackTrace();

            }
            catch(IOException io){
                io.printStackTrace();
                errorEnBaseDeDatos();
                //TODO resetear datos
            }

           

        }
        else{
            /// poner un popup de datos invalidos
            datosInvalidos();
        }
        

        
    }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            poblarTipoDoc();
            
            try{

                inicializarTabla();
			
            }
            catch(ObjetoNoEncontradoException e){
                this.faltanDatosLicencias();
                
            }
            
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

            Alert alert = new Alert(AlertType.WARNING, "Advertencia: Titular no encontrado", ButtonType.OK);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
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

        private void noCumpleCondicionesLicencia(){
            Alert alert = new Alert(AlertType.WARNING, "Advertencia: El titular no cumple con las condiciones para la licencia", ButtonType.OK);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
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
        private void errorEmitirLicencia(){
            Alert alert = new Alert(AlertType.ERROR, "Error: Ocurrio un inconveniente al dar de alta su licencia", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
        }

        private void errorEnBaseDeDatos(){
            Alert alert = new Alert(AlertType.ERROR, "Error: Ocurrio un error relacionado a la base datos. Consulte a su administrador", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
        }

        private void datosInvalidos(){
            Alert alert = new Alert(AlertType.WARNING, "Advertencia: Alguno de los campos ingresados no tiene el formato correcto o faltan datos", ButtonType.OK);
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
