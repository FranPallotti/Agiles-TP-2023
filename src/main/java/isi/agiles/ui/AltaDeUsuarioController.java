package isi.agiles.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.UnaryOperator;

import isi.agiles.App;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoSexo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

public class AltaDeUsuarioController{

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
    private AnchorPane frameAltaUsuario;

    @FXML
    private Label labelAltaDeUsuario;

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
    void accionVolver(ActionEvent event) {
        try{
            Stage currentStage = (Stage) botonVolver.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        ocultarlabelErrores();
        iniciarlistas();
       //reguladorCampoFechaNacimiento();
       campoFechaNacimiento.setEditable(false);
    }

    private void iniciarlistas() {
        listaTipoDoc.getItems().addAll(TipoDoc.values());
        listaTipoSexo.getItems().addAll(TipoSexo.values());
    }

    private void ocultarlabelErrores() {
        labelErrorApellido.setVisible(false);
        labelErrorFechaNacimiento.setVisible(false);
        labelErrorMail.setVisible(false);
        labelErrorNombre.setVisible(false);
        labelErrorNombreUsuario.setVisible(false);
        labelErrorNroDoc.setVisible(false);
        labelErrorSexo.setVisible(false);
        labelErrorTipoDoc.setVisible(false);
    }
    
    /* DEJO ESTO POR SI SE QUIERE INGRESAR LA FECHA MANUAL Y NO USAR EL ICONITO, IGUAL HAY Q VERLO Y VALIDAR PQ SI SE SALE
        EL FOCO Y LA FECHA ES INVALIDA TIRA EXCEPCION
    private void reguladorCampoFechaNacimiento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringConverter<LocalDate> converter = new LocalDateStringConverter(formatter, null);

        // Configurar el TextFormatter
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,2}(/\\d{0,2}){0,1}(/\\d{0,4}){0,1}")) {
                return change;
            }
            return null;
        };

        TextFormatter<LocalDate> textFormatter = new TextFormatter<>(converter, null, filter);
        campoFechaNacimiento.getEditor().setTextFormatter(textFormatter);

        // Listener para actualizar el valor del DatePicker cuando se pierde el enfoque
        campoFechaNacimiento.getEditor().focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Cuando pierde el enfoque
                try {
                    String text = campoFechaNacimiento.getEditor().getText();
                    if (!text.isEmpty()) {
                        LocalDate date = LocalDate.parse(text, formatter);
                        campoFechaNacimiento.setValue(date);
                    }
                } catch (DateTimeParseException e) {
                    campoFechaNacimiento.getEditor().setText("");
                }
            }
        });

        // Actualizar el editor cuando cambia el valor del DatePicker
        campoFechaNacimiento.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                campoFechaNacimiento.getEditor().setText(formatter.format(newDate));
            }
        });

        // Actualizar el editor cuando cambia el valor del DatePicker
        campoFechaNacimiento.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                campoFechaNacimiento.getEditor().setText(formatter.format(newDate));
            }
        });
    }*/
}
