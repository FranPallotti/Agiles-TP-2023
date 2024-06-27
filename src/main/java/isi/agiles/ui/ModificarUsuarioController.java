package isi.agiles.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import isi.agiles.App;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoRol;
import isi.agiles.entidad.TipoSexo;
import isi.agiles.entidad.Usuario;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.excepcion.UsernameNoUnicoException;
import isi.agiles.logica.GestorUsuario;
import isi.agiles.util.DatosInvalidosException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModificarUsuarioController {
    
    @FXML
    private Button botonGuardar;

    @FXML
    private Button botonVolver;

    @FXML
    private Button botonBuscar;
    
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
    private Text labelModifUsuario;

    @FXML
    private Label labelApellido;

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
    private ChoiceBox<TipoDoc> listaTipoDoc;

    @FXML
    private ChoiceBox<TipoSexo> listaTipoSexo;

    @FXML
    private ImageView imagenMuniStaFe;

    @FXML
    private Label labelBuscar;

    @FXML
    private Label labelTipoRol;

    @FXML
    private Label labelErrorTipoRol;
    
    @FXML
    private ChoiceBox<TipoRol> listaTipoRol;

    private GestorUsuario gestorUsuario = new GestorUsuario();

    private UsuarioDTO usuario;

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
            UsuarioDTO dto = this.generarUsuarioDTO();
            gestorUsuario.modificarUsuario(dto);
            informacionClienteGuardado();
            Stage currentStage = (Stage) botonGuardar.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }catch (DatosInvalidosException e){
            errorDatosInvalidos(e.getMessage());
        }catch(ObjetoNoEncontradoException e){
            errorDatosInvalidos(e.getMessage());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void deseaCrear(String message) {
        ButtonType ButtonSi = new ButtonType("Si", ButtonData.YES);
        ButtonType ButtonNo = new ButtonType("No", ButtonData.NO);
        Alert alert = new Alert(AlertType.ERROR, message, ButtonSi, ButtonNo);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        
        Optional<ButtonType> resultado = alert.showAndWait();
        alert.getDialogPane().lookupButton(ButtonSi).setCursor(Cursor.HAND);
        alert.getDialogPane().lookupButton(ButtonNo).setCursor(Cursor.HAND);
        alert.setResizable(false);
        
        if(resultado.isPresent() && resultado.get() == ButtonSi){
            try{
                Stage currentStage = (Stage) botonVolver.getScene().getWindow();
                App.cambiarVentana("AltaDeUsuario.fxml", currentStage);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private UsuarioDTO generarUsuarioDTO(){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido(this.campoApellido.getText());
        dto.setFechaNacimiento(this.campoFechaNacimiento.getValue());
        dto.setMail(this.campoMail.getText());
        dto.setNombre(this.campoNombre.getText());
        dto.setNombreUsuario(this.campoNombreUsuario.getText());
        dto.setNumDoc(this.campoNroDoc.getText());
        dto.setRol(listaTipoRol.getValue());
        dto.setSexo(this.listaTipoSexo.getValue());
        dto.setTipoDoc(this.listaTipoDoc.getValue());
        return dto;
    }

    @FXML
    public void initialize(){
        ocultarlabelErrores();
        iniciarlistas();
        deshabilitarcampos();
       campoNroDoc.setText(null);
    }

    private void deshabilitarcampos() {
        campoNombre.setEditable(false);
        campoApellido.setEditable(false);
        campoFechaNacimiento.setEditable(false);
        campoMail.setEditable(false);
        listaTipoSexo.setDisable(true);   
        campoNombreUsuario.setEditable(false);
        botonGuardar.setDisable(true);
    }

    private void iniciarlistas() {
        listaTipoDoc.getItems().addAll(TipoDoc.values());
        listaTipoSexo.getItems().addAll(TipoSexo.values());  
        listaTipoRol.getItems().addAll(TipoRol.values());  
    }

    private void ocultarlabelErrores() {
        labelErrorNombre.setVisible(false);
        labelErrorApellido.setVisible(false);
        labelErrorFechaNacimiento.setVisible(false);
        labelErrorMail.setVisible(false);
        labelErrorSexo.setVisible(false);
        labelErrorNombreUsuario.setVisible(false);
        labelErrorTipoDoc.setVisible(false);    
        labelErrorNroDoc.setVisible(false);
        labelErrorTipoRol.setVisible(false);
    }

    @FXML
    public void accionBuscar(){
        try {
            validarDatosBuscar();
            usuario = gestorUsuario.getbyDocumento(listaTipoDoc.getValue(), campoNroDoc.getText());
            rellenarCampos(usuario);
            botonGuardar.setDisable(false);
        } catch (DatosInvalidosException e) {
            errorDatosInvalidos(e.getMessage());
        } catch (ObjetoNoEncontradoException o){
            deseaCrear("Atención: el usuario no se encuentra en la base de datos. ¿Desea darlo de alta?");
        } 
    }

    private void rellenarCampos(UsuarioDTO usuario) {
        campoNombre.setEditable(true);
        campoApellido.setEditable(true);
        campoFechaNacimiento.setEditable(true);
        campoMail.setEditable(true);
        campoNombreUsuario.setEditable(true);
        listaTipoSexo.setDisable(false);
        campoNombre.setText(usuario.getNombre());
        campoApellido.setText(usuario.getApellido());
        campoFechaNacimiento.setValue(usuario.getFechaNaciemiento()); //revisar si es esto lo que quiero
        campoMail.setText(usuario.getMail());
        campoNombreUsuario.setText(usuario.getNombreUsuario());
        listaTipoSexo.setValue(usuario.getSexo());
        listaTipoRol.setValue(usuario.getRol());
    }

    private void validarDatosBuscar() throws DatosInvalidosException {
        Boolean datosValidos = true;
        
        if(listaTipoDoc.getValue() != null){
            labelErrorTipoDoc.setVisible(false);
            if(!campoNroDoc.getText().isEmpty() || campoNroDoc.getText() == null){
                labelErrorNroDoc.setText("*Campo Obligatorio*");
                labelErrorNroDoc.setVisible(false);
                if(listaTipoDoc.getValue().equals(TipoDoc.DNI)){
                    if(!campoNroDoc.getText().matches("^\\d{8}$")){
                        datosValidos = false;
                        labelErrorNroDoc.setText("*Formato: 99999999*");
                        labelErrorNroDoc.setVisible(true);
                    }
                } else if (listaTipoDoc.getValue().equals(TipoDoc.PASAPORTE)){
                    if(!campoNroDoc.getText().matches("^[a-zA-Z]{3}\\d{6}$")){
                        datosValidos = false;
                        labelErrorNroDoc.setText("*Formato: AAA999999*");
                        labelErrorNroDoc.setVisible(true);
                    }
                }
            } else {
                datosValidos = false;
                labelErrorNroDoc.setVisible(true);
            }
        } else {
            datosValidos = false;
            labelErrorTipoDoc.setVisible(true);
            labelErrorNroDoc.setVisible(true);
        }

        if(!datosValidos){
            throw new DatosInvalidosException("Advertencia: Por favor, revise los campos ingresados y vuelva a intentarlo");
        }
    }

    private void datosValidos() throws DatosInvalidosException{
        Boolean datosInvalidos = false;
        datosInvalidos |=nombreInvalido();
        datosInvalidos |=apellidoInvalido();
        datosInvalidos |=fechaNacimientoInvalido();
        datosInvalidos |=mailInvalido();
        datosInvalidos |=sexoInvalido();
       // invalidos |=tipoDocNroDocInvalido();
        datosInvalidos |= rolInvalido();
        datosInvalidos |= nombreUsuarioInvalido();
        if(datosInvalidos){
            throw new DatosInvalidosException("Advertencia: Por favor, revise los campos ingresados y vuelva a intentarlo.");
        }
    }

    private boolean nombreUsuarioInvalido() {
        Boolean invalido =  false;
        if (campoNombreUsuario.getText() == null || campoNombreUsuario.getText().isEmpty()){
            labelErrorNombreUsuario.setVisible(true);
            invalido=true;
            campoNombreUsuario.setText(null);
        } else if(campoNombreUsuario.getText().length()>16){
            labelErrorNombreUsuario.setText("*Máximo 16 caracteres\r\n sin espacios.*");
            labelErrorNombreUsuario.setVisible(true);
            campoNombreUsuario.setText(null);
            invalido=true;
        }else if(!campoNombreUsuario.getText().matches("^[a-zA-Z0-9_]+$")){
            labelErrorNombreUsuario.setText("*Sólo letras y/o números sin\r\n espacios ni simbolos.*");
            labelErrorNombreUsuario.setVisible(true);
            campoNombreUsuario.setText(null);
            invalido=true;
        }

        if(invalido == false){
            labelErrorNombreUsuario.setVisible(false);
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

    private Boolean rolInvalido(){
        if(listaTipoRol.getValue() == null){
            labelErrorTipoRol.setVisible(true);
            return true;
        } else {
            labelErrorTipoRol.setVisible(false);
            return false;
        }
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
        Alert alert = new Alert(AlertType.INFORMATION, "Importante: El usuario ha sido modificado correctamente.", ButtonType.OK);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }
}
