package isi.agiles.ui;

import java.io.IOException;
import java.time.LocalDate;

import isi.agiles.App;
import isi.agiles.dto.*;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoFactorRH;
import isi.agiles.entidad.TipoGrupoS;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.excepcion.TitularYaCargadoException;
import isi.agiles.logica.GestorTitular;
import isi.agiles.util.DatosInvalidosException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ModificarDatosTitularController {

    @FXML
    private Button botonBuscar;

    @FXML
    private Button botonModificarTitular;

    @FXML
    private Button botonVolver;

    @FXML
    private TextField campoApellido;

    @FXML
    private TextField campoDireccion;

    @FXML
    private DatePicker campoFechaNacimiento;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoNroDoc;

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
    private ImageView imagenMunicipalidadStaFe;

    @FXML
    private Label labelApellido;

    @FXML
    private Label labelBuscarTitular;

    @FXML
    private Label labelModificarTitular;

    @FXML
    private Label labelClasesSolicitadas;

    @FXML
    private Label labelDireccion;

    @FXML
    private Label labelDonante;

    @FXML
    private Label labelErrorApellido;

    @FXML
    private Label labelErrorDireccion;

    @FXML
    private Label labelErrorDonante;

    @FXML
    private Label labelErrorFactorRH;

    @FXML
    private Label labelErrorFechaNacimiento;

    @FXML
    private Label labelErrorGrupoSanguineo;

    @FXML
    private Label labelErrorNombre;

    @FXML
    private Label labelErrorTipoYNroDoc;

    @FXML
    private Label labelFactorRH;

    @FXML
    private Label labelFechaNacimiento;

    @FXML
    private Label labelGrupoSanguineo;

    @FXML
    private Label labelModificarDatosTitular;

    @FXML
    private Label labelMunicipalidadStaFe;

    @FXML
    private Label labelNombre;

    @FXML
    private Label labelNroDoc;

    @FXML
    private Label labelTipoDoc;

    @FXML
    private ComboBox<String> listaDonante;

    @FXML
    private ComboBox<TipoFactorRH> listaFactorRH;

    @FXML
    private ComboBox<TipoGrupoS> listaGrupoSanguineo;

    @FXML
    private ComboBox<TipoDoc> listaTipoDoc;

    @FXML
    private Rectangle rectanguloVerde;

    private GestorTitular gestorTitular = new GestorTitular();

    private TitularDTO  titular;

    @FXML
    void accionBotonBuscar(ActionEvent event) {
        try{
            validarBusqueda();
            titular = (TitularDTO) gestorTitular.getTitularDTOByDocumento(campoNroDoc.getText(), listaTipoDoc.getSelectionModel().getSelectedItem());
            completarYMostrarCamposConDatos(titular);
        }catch(DatosInvalidosException e){
            esconderCamposModificacion();
            errorDatosInvalidos(e.getMessage());
        }catch(ObjetoNoEncontradoException a){
            titularNoEncontrado();
        }
    }

    @FXML
    void accionBotonModificarTitular(ActionEvent event) {
        try {
            validarDatos();
            //Se modifica el titular existente
            actualizarDTO();
            gestorTitular.actualizarTitular(titular);
            informacionTitularModificado();
            accionBotonVolver(event);
        } catch (DatosInvalidosException e) {
            errorDatosInvalidos(e.getMessage());
        } catch(ObjetoNoEncontradoException a){
            a.printStackTrace();
        }
    }

    @FXML
    void accionBotonVolver(ActionEvent event) {
        try{
            Stage currentStage = (Stage) botonVolver.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void initialize(){
        inicializarListas();
        esconderLabelsError();
        botonModificarTitular.setDisable(true);
        esconderCamposModificacion();
    }

    private void esconderCamposModificacion() {
        labelModificarTitular.setVisible(false);
        labelNombre.setVisible(false);
        labelFechaNacimiento.setVisible(false);
        labelDireccion.setVisible(false);
        labelGrupoSanguineo.setVisible(false);
        labelFactorRH.setVisible(false);
        labelDonante.setVisible(false);
        labelClasesSolicitadas.setVisible(false);
        labelApellido.setVisible(false);
        campoApellido.setVisible(false);
        campoNombre.setVisible(false);
        campoDireccion.setVisible(false);
        campoFechaNacimiento.setVisible(false);
        listaGrupoSanguineo.setVisible(false);
        listaFactorRH.setVisible(false);
        listaDonante.setVisible(false);
        choiceClaseA.setVisible(false);
        choiceClaseB.setVisible(false);
        choiceClaseC.setVisible(false);
        choiceClaseD.setVisible(false);
        choiceClaseE.setVisible(false);
        choiceClaseF.setVisible(false);
        choiceClaseG.setVisible(false);
    }

    private void esconderLabelsError() {
        labelErrorApellido.setVisible(false);
        labelErrorDireccion.setVisible(false);
        labelErrorDonante.setVisible(false);
        labelErrorFactorRH.setVisible(false);
        labelErrorFechaNacimiento.setVisible(false);
        labelErrorGrupoSanguineo.setVisible(false);
        labelErrorNombre.setVisible(false);
        labelErrorTipoYNroDoc.setVisible(false);
    }

    private void inicializarListas() {
        listaTipoDoc.getItems().addAll(TipoDoc.values());
        listaGrupoSanguineo.getItems().addAll(TipoGrupoS.values());
        listaFactorRH.getItems().addAll(TipoFactorRH.values());
        listaDonante.getItems().addAll("SI", "NO");
    }

    private void validarBusqueda() throws DatosInvalidosException{
        if(listaTipoDoc.getValue()==null || campoNroDoc.getText().isEmpty()){
            labelErrorTipoYNroDoc.setVisible(true);
            throw new DatosInvalidosException("Advertencia: Por favor, revise los campos ingresados y vuelva a intentarlo.");
        }else if(listaTipoDoc.getValue().equals(TipoDoc.DNI) && !campoNroDoc.getText().matches("^\\d{8}$")){
            labelErrorTipoYNroDoc.setText("*Tipo: DNI , Formato: 99999999.*");
            labelErrorTipoYNroDoc.setVisible(true);
            throw new DatosInvalidosException("Advertencia: Por favor, revise los campos ingresados y vuelva a intentarlo.");
        }else if(listaTipoDoc.getValue().equals(TipoDoc.PASAPORTE) && !campoNroDoc.getText().matches("^[a-zA-Z]{3}\\d{6}$")){
            labelErrorTipoYNroDoc.setText("*Tipo: PASAPORTE , Formato: AAA999999.*");
            labelErrorTipoYNroDoc.setVisible(true);
            throw new DatosInvalidosException("Advertencia: Por favor, revise los campos ingresados y vuelva a intentarlo.");
        }else{
            labelErrorTipoYNroDoc.setVisible(false);
        }
    }

    private void completarYMostrarCamposConDatos(TitularDTO titular) {
        labelModificarTitular.setVisible(true);
        labelNombre.setVisible(true);
        labelFechaNacimiento.setVisible(true);
        labelDireccion.setVisible(true);
        labelGrupoSanguineo.setVisible(true);
        labelFactorRH.setVisible(true);
        labelDonante.setVisible(true);
        labelClasesSolicitadas.setVisible(true);
        labelApellido.setVisible(true);
        campoApellido.setVisible(true);
        campoNombre.setVisible(true);
        campoDireccion.setVisible(true);
        campoFechaNacimiento.setVisible(true);
        listaGrupoSanguineo.setVisible(true);
        listaFactorRH.setVisible(true);
        listaDonante.setVisible(true);
        choiceClaseA.setVisible(true);
        choiceClaseB.setVisible(true);
        choiceClaseC.setVisible(true);
        choiceClaseD.setVisible(true);
        choiceClaseE.setVisible(true);
        choiceClaseF.setVisible(true);
        choiceClaseG.setVisible(true);
        botonModificarTitular.setDisable(false);

        campoApellido.setText(titular.getApellido());
        campoNombre.setText(titular.getNombre());
        campoDireccion.setText(titular.getDireccion());
        campoFechaNacimiento.setValue(titular.getFechaNacimiento());
        listaGrupoSanguineo.setValue(titular.getGrupoSanguineo());
        if(titular.getEsDonante()){
            listaDonante.setValue("SI");
        }else{
            listaDonante.setValue("NO");
        }
        listaFactorRH.setValue(titular.getFactorRH());
    }

    private void validarDatos() throws DatosInvalidosException {
        Boolean datoInvalido = false;
        datoInvalido |= validarNombreYapellido();
        datoInvalido |= validarNacimiento();
        datoInvalido |= validarDireccion();
        datoInvalido |= validarComboboxes();
        if (datoInvalido){
            throw new DatosInvalidosException("Advertencia: por favor, revise los campos ingresados y vuelva a intentarlo");
        }
    }

    private Boolean validarComboboxes() {
        Boolean esInvalido = false;
        
        if(listaDonante.getValue() == null){
            esInvalido = true;
            labelErrorDonante.setVisible(true);
        } else {
            labelErrorDonante.setVisible(false);
        }
        if(listaFactorRH.getValue() == null) {
            esInvalido = true;
            labelErrorFactorRH.setVisible(true);
        } else{
            labelErrorFactorRH.setVisible(false);
        }
        if(listaGrupoSanguineo.getValue() == null){
            esInvalido = true;
            labelErrorGrupoSanguineo.setVisible(true);
        } else {
            labelErrorFactorRH.setVisible(false);
        }
        return esInvalido;
    }

    private Boolean validarDireccion() {
        Boolean esInvalido = false;
        if(!campoDireccion.getText().isEmpty()){
            labelErrorDireccion.setVisible(false);
            if(campoDireccion.getText().length()>64){
                labelErrorDireccion.setText("*Máximo 64 caracteres.*");
                labelErrorDireccion.setVisible(true);
                esInvalido = true;
            }
        } else {
            labelErrorDireccion.setText("*Campo Obligatorio.*");
            labelErrorDireccion.setVisible(true);
            esInvalido = true;
        }
        return esInvalido;
    }

    private Boolean validarNacimiento() {
        Boolean esInvalido = false;

        if(campoFechaNacimiento.getValue() != null){
            labelErrorFechaNacimiento.setVisible(false);
            if(campoFechaNacimiento.getValue().isAfter(LocalDate.now().minusYears(18))){
                esInvalido=true;
                labelErrorFechaNacimiento.setText("*El titular debe tener más de 18 años.*");
                labelErrorFechaNacimiento.setVisible(true);
            } 
        } else {
            labelErrorFechaNacimiento.setText("*Campo Obligatorio.*");
            labelErrorFechaNacimiento.setVisible(true);
            esInvalido=true;
        }
        return esInvalido;
    }

    private Boolean validarNombreYapellido() {
        Boolean esInvalido = false;
        if(!campoNombre.getText().isEmpty() || !campoApellido.getText().isEmpty()){
            labelErrorNombre.setVisible(false);
            labelErrorApellido.setVisible(false);
            if(campoNombre.getText().length() >32 || campoApellido.getText().length() > 32){
                labelErrorNombre.setText("*Máximo 32 caracteres.*");
                labelErrorNombre.setVisible(true);
                labelErrorApellido.setText("*Máximo 32 caracteres.*");
                labelErrorApellido.setVisible(true);
                esInvalido = true;
            } else {
                labelErrorApellido.setVisible(false);
                labelErrorNombre.setVisible(false);
                if(!campoNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ-]+")){
                    labelErrorNombre.setText("*Caracteres inválidos.*");
                    labelErrorNombre.setVisible(true);
                    esInvalido = true;
                } 
                if(!campoApellido.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ-]+")){
                    labelErrorApellido.setText("*Caracteres inválidos.*");
                    labelErrorApellido.setVisible(true);
                    esInvalido = true;
                }
            }
        } else {
            esInvalido = true;
            labelErrorNombre.setText("*Campo Obligatorio*");
            labelErrorApellido.setText("*Campo Obligatorio*");
            labelErrorApellido.setVisible(true);
            labelErrorNombre.setVisible(true);
        }
        return esInvalido;
    }

    private void actualizarDTO() {
        titular.setNombre(campoNombre.getText());
        titular.setApellido(campoApellido.getText());
        titular.setFechaNacimiento(campoFechaNacimiento.getValue());
        titular.setDireccion(campoDireccion.getText());
        titular.setGrupoSanguineo(listaGrupoSanguineo.getSelectionModel().getSelectedItem());
        titular.setFactorRH(listaFactorRH.getSelectionModel().getSelectedItem());
        //titular.setClaseSol(null);
        if(listaDonante.getSelectionModel().getSelectedItem().equals("SI")){
            titular.setEsDonante(true);
        }else{
            titular.setEsDonante(false);
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

    private void informacionTitularModificado() {
        Alert alert = new Alert(AlertType.INFORMATION, "Importante: El titular ha sido modificado con éxito.", ButtonType.OK);
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
