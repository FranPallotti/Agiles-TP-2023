package isi.agiles.ui;

import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoSexo;
import isi.agiles.logica.GestorUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ModificarUsuarioController {
    
    @FXML
    private Button botonGuardar;

    @FXML
    private Button botonVolver;

    @FXML
    private TextField campoApellido;

    @FXML
    private DatePicker campoFechaNacimiento;

    @FXML
    private TextField campoMail;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoNombreUsuario;

    @FXML
    private TextField campoNroDoc;

    @FXML
    private AnchorPane frameModificarUsuario;

    @FXML
    private Label labelModifUsuario;

    @FXML
    private Label labelApellido;

    @FXML
    private Label labelErrorApellido;

    @FXML
    private Label labelErrorFechaNacimiento;

    @FXML
    private Label labelErrorMail;

    @FXML
    private Label labelErrorNombre;

    @FXML
    private Label labelErrorNombreUsuario;

    @FXML
    private Label labelErrorNroDoc;

    @FXML
    private Label labelErrorSexo;

    @FXML
    private Label labelErrorTipoDoc;

    @FXML
    private Label labelFechaNacimiento;

    @FXML
    private Label labelMail;

    @FXML
    private Label labelNombre;

    @FXML
    private Label labelNombreUsuario;

    @FXML
    private Label labelNroDoc;

    @FXML
    private Label labelSexo;

    @FXML
    private Label labelTipoDoc;

    @FXML
    private ChoiceBox<TipoDoc> listaTipoDoc;

    @FXML
    private ChoiceBox<TipoSexo> listaTipoSexo;

    @FXML
    private ImageView imagenMuniStaFe;

    private GestorUsuario gestorUsuario = new GestorUsuario();

}
