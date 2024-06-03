package isi.agiles.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import isi.agiles.App;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoFactorRH;
import isi.agiles.entidad.TipoGrupoS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class DarAltaTitularController{
    
    @FXML
    private AnchorPane frameDarAltaTitular;

    @FXML
    private Text tituloDarAltaTitular;

    @FXML
    private ComboBox<TipoDoc> comboTipoDocumento;

    @FXML
    private TextField nroDocumento;

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textApellido;

    @FXML
    private DatePicker dateFechaNacimiento;

    @FXML
    private TextField textDireccion;

    @FXML
    private ComboBox<TipoGrupoS> comboGrupoSanguineo;

    @FXML
    private ComboBox<TipoFactorRH> comboFactorRH;

    @FXML
    private Label labelClaseSolicitada;

    @FXML
    private CheckBox choiceClaseA;

    @FXML
    private CheckBox choiceClaseB;

    @FXML
    private CheckBox choiceClaseC;

    @FXML
    private CheckBox choiceClaseD;

    @FXML
    private CheckBox choiceClaseE;

    @FXML
    private CheckBox choiceClaseF;

    @FXML
    private CheckBox choiceClaseG;
    
    @FXML
    private ComboBox<String> comboDonante;

    @FXML
    private Label tipoObligatorio;

    @FXML
    private Label numObligatorio;

    @FXML
    private Label nombreObligatorio;

    @FXML
    private Label apellidoObligatorio;

    @FXML
    private Label fechaObligatorio;

    @FXML
    private Label dirObligatorio;

    @FXML
    private Label gruposObligatorio;

    @FXML
    private Label factorRHObligatorio;

    @FXML
    private Label donanteObligatorio;

    @FXML
    private Button botonVolver;

    @FXML
    private Button botonGuardar;


    public void accionVolver(){
        try{
            Stage currentStage = (Stage) botonVolver.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void accionGuardar(){

    }

    @FXML
    public void initialize() {
        ocultarObligatorios();
        inicializarDesplegables();
    }

    private void inicializarDesplegables() {
        comboTipoDocumento.getItems().addAll(TipoDoc.values());
        comboGrupoSanguineo.getItems().addAll(TipoGrupoS.values());
        comboFactorRH.getItems().addAll(TipoFactorRH.values());
        comboDonante.getItems().addAll("SI", "NO");
    }

    private void ocultarObligatorios() {
        tipoObligatorio.setVisible(false);
        numObligatorio.setVisible(false);
        nombreObligatorio.setVisible(false);
        apellidoObligatorio.setVisible(false);
        fechaObligatorio.setVisible(false);
        dirObligatorio.setVisible(false);
        gruposObligatorio.setVisible(false);
        factorRHObligatorio.setVisible(false);
        donanteObligatorio.setVisible(false);
    }
}