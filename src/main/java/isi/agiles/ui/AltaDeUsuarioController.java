package isi.agiles.ui;

import java.io.IOException;
import java.time.LocalDate;

import isi.agiles.App;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoRol;
import isi.agiles.entidad.TipoSexo;
import isi.agiles.entidad.Usuario;
import isi.agiles.excepcion.UsernameNoUnicoException;
import isi.agiles.logica.GestorUsuario;
import isi.agiles.util.DatosInvalidosException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    void accionGuardar(ActionEvent event) {
        try{
            datosValidos();
            //Lógica para guardar cliente
            UsuarioDTO dto = this.getUsuarioDTO();
            GestorUsuario.altaUsuario(dto);
            informacionClienteGuardado();
            //Vuelta al menú principal
            Stage currentStage = (Stage) botonGuardar.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }catch (DatosInvalidosException e){
            errorDatosInvalidos(e.getMessage());
        }
        catch(UsernameNoUnicoException u){
            errorDatosInvalidos(u.getMessage());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        ocultarlabelErrores();
        iniciarlistas();
       //reguladorCampoFechaNacimiento();
       campoFechaNacimiento.setEditable(false);
       campoNroDoc.setText(null);
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

    private void errorDatosInvalidos(String message) {
        Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }

    private void informacionClienteGuardado() {
        Alert alert = new Alert(AlertType.INFORMATION, "Importante: El usuario ha sido se creó correctamente.", ButtonType.OK);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }

    private void datosValidos() throws DatosInvalidosException{
        Boolean invalidos = false;
        invalidos |=nombreInvalido();
        invalidos |=apellidoInvalido();
        invalidos |=fechaNacimientoInvalido();
        invalidos |=mailInvalido();
        invalidos |=sexoInvalido();
        invalidos |=tipoDocNroDocInvalido();
        invalidos =nombreUsuarioInvalido();
        if(invalidos){
            throw new DatosInvalidosException("Advertencia: Por favor, revise los campos ingresados y vuelva a intentarlo.");
        }
    }

    private boolean nombreUsuarioInvalido() {
        Boolean invalido =  false;
        if (campoNombreUsuario.getText() == null){
            labelErrorNombreUsuario.setVisible(true);
            invalido=true;
            campoNombreUsuario.setText(null);
        } else if(campoNombreUsuario.getText().matches("^\\s+$") || campoNombreUsuario.getText().isEmpty()){
            labelErrorNombreUsuario.setVisible(true);
            campoNombreUsuario.setText(null);
            invalido = true;
        }else if(campoNombreUsuario.getText().length()>16){
            labelErrorNombreUsuario.setText("*Máximo 16 caracteres\r\n sin espacios.*");
            labelErrorNombreUsuario.setVisible(true);
            campoNombreUsuario.setText(null);
            invalido=true;
        }else if(campoNombreUsuario.getText().matches("^(\\s+[a-zA-Z0-9]+|[a-zA-Z0-9]+\\s+[a-zA-Z0-9]+|[a-zA-Z0-9]+\\s+)$")){
            labelErrorNombreUsuario.setText("*Sólo letras y/o números sin\r\n espacios.*");
            labelErrorNombreUsuario.setVisible(true);
            campoNombreUsuario.setText(null);
            invalido=true;
        }

        if(invalido == false){
            labelErrorNombreUsuario.setVisible(false);
        }
        return invalido;
    }

    private boolean tipoDocNroDocInvalido() {
        Boolean invalido = false;

        if(listaTipoDoc.getValue() != null && campoNroDoc.getText() != null){
            switch ((TipoDoc)listaTipoDoc.getValue()) {
                case DNI:
                    labelErrorTipoDoc.setVisible(false);
                    if(campoNroDoc.getText().isEmpty()){
                        invalido = true;
                        labelErrorNroDoc.setVisible(true);
                    }else if (!campoNroDoc.getText().matches("^\\d{8}$")){
                        invalido = true;
                        labelErrorNroDoc.setText("*Formato: 99999999*");
                        labelErrorNroDoc.setVisible(true);
                        campoNroDoc.setText(null);
                    }
                break;
                case PASAPORTE:
                    labelErrorTipoDoc.setVisible(false);
                    if(campoNroDoc.getText().isEmpty()){
                        invalido = true;
                        labelErrorNroDoc.setVisible(true);
                    }else if (!campoNroDoc.getText().matches("^[a-zA-Z]{3}\\d{6}$")){
                        invalido = true;
                        labelErrorNroDoc.setText("*Formato: AAA999999*");
                        labelErrorNroDoc.setVisible(true);
                        campoNroDoc.setText(null);
                    }
                break;
                default:
                    break;
            }
        }else if (campoNroDoc.getText() == null && listaTipoDoc.getValue() == null){
            invalido = true;
            labelErrorNroDoc.setVisible(true);
            labelErrorTipoDoc.setVisible(true);
        }else if (listaTipoDoc.getValue() == null){
            invalido = true;
            labelErrorTipoDoc.setVisible(true);
            labelErrorNroDoc.setVisible(false);            
        }else {
            invalido = true;
            labelErrorNroDoc.setVisible(true);
            labelErrorTipoDoc.setVisible(false);
        }

        if(invalido == false){
            labelErrorTipoDoc.setVisible(false);
            labelErrorNroDoc.setVisible(false);
        }
        return invalido;
    }

    private boolean sexoInvalido() {
        Boolean invalido = listaTipoSexo.getValue() == null;
        if(invalido){
            labelErrorSexo.setVisible(true);
        }else{
            labelErrorSexo.setVisible(false);
        }
        return invalido;
    }

    private boolean mailInvalido() {
       Boolean invalido = false;
        if (campoMail.getText() == null){
            invalido = true;
            labelErrorMail.setVisible(true);
            campoMail.setText(null);
        }else if(campoMail.getText().isEmpty()){
            invalido = true;
            labelErrorMail.setVisible(true);
            campoMail.setText(null);
        }else if(!campoMail.getText().matches("^[\\w.-]+@(gmail|hotmail)\\.com$")){
                invalido = true;
                labelErrorMail.setText("*ejemplo@gmail.com \r\n o ejemplo@hotmail.com*");
                labelErrorMail.setVisible(true);
                campoMail.setText(null);
        }
        if(invalido == false){
            labelErrorMail.setVisible(false);
        }
       return invalido;
    }

    private boolean fechaNacimientoInvalido() {
        Boolean invalido = campoFechaNacimiento.getValue() == null;
        if(invalido){
            labelErrorFechaNacimiento.setVisible(true);
        }else if(campoFechaNacimiento.getValue().isAfter(LocalDate.now().minusYears(18))){
            invalido = true;
            labelErrorFechaNacimiento.setText("*El usuario debe\r\ntener más de 18 años.*");
            labelErrorFechaNacimiento.setVisible(true);
            campoFechaNacimiento.setValue(null);
        }
        if(invalido == false){
            labelErrorFechaNacimiento.setVisible(false);
        }
        return invalido;
    }

    private boolean apellidoInvalido() {
        Boolean invalido =  false;
        if(campoApellido.getText() == null){
            labelErrorApellido.setVisible(true);
            campoApellido.setText(null);
            invalido = true;
        }else if(campoApellido.getText().matches("^\\s+$") || campoApellido.getText().isEmpty()){
            labelErrorApellido.setVisible(true);
            campoApellido.setText(null);
            invalido = true;
        }else if(campoApellido.getText().length()>32){
            labelErrorApellido.setText("*Máximo 32 caracteres.*");
            labelErrorApellido.setVisible(true);
            campoApellido.setText(null);
            invalido=true;
        }
        if(invalido == false){
            labelErrorApellido.setVisible(false);
        }
        return invalido;
    }

    private boolean nombreInvalido() {
        Boolean invalido = false;
        if(campoNombre.getText() == null){
            labelErrorNombre.setVisible(true);
            campoNombre.setText(null);
            invalido = true;
        }else if(campoNombre.getText().matches("^\\s+$") || campoNombre.getText().isEmpty()){
            labelErrorNombre.setVisible(true);
            campoNombre.setText(null);
            invalido = true;
        }else if(campoNombre.getText().length()>32){
            labelErrorNombre.setText("*Máximo 32 caracteres.*");
            labelErrorNombre.setVisible(true);
            campoNombre.setText(null);
            invalido=true;
        }
        if(invalido == false){
            labelErrorNombre.setVisible(false);
        }
        return invalido;
    }


    private UsuarioDTO getUsuarioDTO(){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido(this.campoApellido.getText());
        dto.setFechaNacimiento(this.campoFechaNacimiento.getValue());
        dto.setMail(this.campoMail.getText());
        dto.setNombre(this.campoNombre.getText());
        dto.setNombreUsuario(this.campoNombreUsuario.getText());
        dto.setNumDoc(this.campoNroDoc.getText());
        dto.setRol(TipoRol.OPERADOR);
        dto.setSexo(this.listaTipoSexo.getValue());
        dto.setTipoDoc(this.listaTipoDoc.getValue());
        return dto;
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
