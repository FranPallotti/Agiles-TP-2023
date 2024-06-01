package isi.agiles.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label;

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

public class DarAltaTitularController implements Initializable{
    
    @FXML
    private AnchorPane frameDarAltaTitular;

    @FXML
    private Text tituloDarAltaTitular;

    @FXML
    private ComboBox<String> comboTipoDocumento;

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
    private ComboBox<String> comboGrupoSanguineo;

    @FXML
    private ComboBox<String> comboFactorRH;

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
    private Button botonVolver;

    @FXML
    private Button botonGuardar;
    

    public void accionVolver(){

    }

    public void accionGuardar(){

    }

   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> tipoDoc = FXCollections.observableArrayList("DNI", "PASAPORTE");
        comboTipoDocumento.setItems(tipoDoc);
    }
}