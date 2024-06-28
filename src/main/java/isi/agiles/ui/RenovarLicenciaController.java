package isi.agiles.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import isi.agiles.App;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.EstadoLicencia;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.excepcion.NoPuedeRenovarExisteLicencia;
import isi.agiles.excepcion.NoPuedeRenovarVigenciaTemprana;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorLicencia;
import isi.agiles.logica.GestorTitular;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RenovarLicenciaController implements Initializable {

    @FXML
    private Button botonBuscar;

    @FXML
    private Button botonContinuar;

    @FXML
    private Button botonVolverAtras;

    @FXML
    private TextField campoNroDoc;

    @FXML
    private ComboBox<TipoDoc> campoTipoDocumento;
    @FXML
    private Label errorFaltaTitular;

    //atributos tabla
    @FXML
    private TableColumn<LicenciaDTO, String> apellidoColumn;
    @FXML
    private TableColumn<LicenciaDTO, Character> claseColumn;
    @FXML
    private TableColumn<LicenciaDTO, EstadoLicencia> estadoColumn;

    @FXML
    private TableColumn<LicenciaDTO, LocalDate> fechaVencimientoColumn;

    @FXML
    private TableView<LicenciaDTO> listadoLicenciasTable;

    @FXML
    private TableColumn<LicenciaDTO, String> nombreColumn;
    

    //Atributos internos

    private TitularDTO titular;

    private List<LicenciaDTO> licencias = new ArrayList<LicenciaDTO>();

    private GestorLicencia gestorLicencia = new GestorLicencia();
    
    private GestorTitular gestorTitular = new GestorTitular();


    @FXML
    void buscarCliqueado(ActionEvent event) {
        try{
        if(campoTipoDocumento.getValue()==null || campoNroDoc.getText().isEmpty()){
            errorFaltaTitular.setVisible(true);
        }
        else{
            titular = gestorTitular.getTitularDTOByDocumento(this.getTitularDTO().getNroDoc(), this.getTitularDTO().getTipoDoc());
            errorFaltaTitular.setVisible(false);
        }

            actualizarTabla();
    }
    catch(ObjetoNoEncontradoException e){
        titularNoEncontrado();
    }
    }

    @FXML
    void continuarCliqueado(ActionEvent event) {
        //Si no se selecciona ninguna licencia, se muestra un cartel pertinente
        if(listadoLicenciasTable.getSelectionModel().isEmpty()){
            seleccioneUnaLicencia();
        }
        else{
            try{
                //Se verifica que la licencia cumple con los requisitos para ser renovada 
                gestorLicencia.puedeSerRenovada(listadoLicenciasTable.getSelectionModel().getSelectedItem(), licencias);
                //Se carga la nueva pantalla
                cargarPantallaModificacionDatos();
            }//Manejo de excepciones
            catch(NoPuedeRenovarVigenciaTemprana e){
                errorNoPuedeRenovar(e.getMessage());
            }
            catch(NoPuedeRenovarExisteLicencia a){
                errorNoPuedeRenovar(a.getMessage());
            }
            catch(IOException o){
                o.printStackTrace();
            }
        }
        
    }

    private void cargarPantallaModificacionDatos() throws IOException{
        //Se carga el archivo fxml correspondiente a la pantalla y se le setea el título
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("ModificarDatosRenovacion.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Confirme los datos y el Costo");
        
            
        Parent root = loader.load();
        //Se carga el controlador
        ModificarDatosRenovacionController formularioDatos = loader.getController();
        //Se obtiene la nueva escena y setean valores fijos de la misma
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.getIcons().add(new Image("isi/agiles/logoStaFe.png"));
        stage.show();
        LicenciaDTO l= listadoLicenciasTable.getSelectionModel().getSelectedItem();
        formularioDatos.setLicencia(l);
        formularioDatos.setearDatos();
        Stage currentStage = (Stage) this.botonContinuar.getScene().getWindow();
        //Se destruye la escena que se estaba utilizando
        currentStage.close();
    }

    @FXML
    void volverAtrasCliqueado(ActionEvent event) {
        try{
            Stage currentStage = (Stage) botonVolverAtras.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public TitularDTO getTitularDTO(){
        TitularDTO dto = new TitularDTO();
        dto.setNroDoc(this.campoNroDoc.getText());
        dto.setTipoDoc(this.campoTipoDocumento.getValue());
        return dto;

    }
    public void actualizarTabla(){
        try{
            if(titular==null){
            
                listadoLicenciasTable.setVisible(false);
                
            }
            else{
                licencias= gestorTitular.getTitular(titular).getLicencias().stream().map(t-> gestorLicencia.getLicenciaDTO(t)).collect(Collectors.toList());
                ObservableList<LicenciaDTO> datosTabla = FXCollections.observableArrayList(licencias);



	            listadoLicenciasTable.setItems(datosTabla);
                listadoLicenciasTable.setVisible(true);
                botonContinuar.setDisable(false);
            }
        }
        catch(ObjetoNoEncontradoException e){
            e.printStackTrace();
        }
        
 
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        campoTipoDocumento.setItems(FXCollections.observableArrayList(TipoDoc.values()));
        listadoLicenciasTable.setVisible(false);
        botonContinuar.setDisable(true);
        fechaVencimientoColumn.setCellValueFactory(
            cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getFinVigencia())
        );
        claseColumn.setCellValueFactory(
            cellData -> new SimpleObjectProperty<Character>(cellData.getValue().getClaseLic().getClase())
        );
        nombreColumn.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getTitular().getNombre())
        );
        apellidoColumn.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getTitular().getApellido())
        );
        estadoColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<EstadoLicencia>(cellData.getValue().getEstado()));
        
        
        
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
    private void seleccioneUnaLicencia(){

        Alert alert = new Alert(AlertType.WARNING, "Advertencia: Seleccione una licencia", ButtonType.OK);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }

    private void errorNoPuedeRenovar(String message) {
        Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
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
