package isi.agiles.ui;

import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import isi.agiles.App;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.TipoClasesLicencia;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoFactorRH;
import isi.agiles.entidad.TipoGrupoS;
import isi.agiles.entidad.TipoSexo;
import isi.agiles.logica.GestorTitular;
import isi.agiles.util.DatosInvalidosException;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
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
    private ComboBox<TipoSexo> sexo;

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
    private Label sexoObligatorio;

    @FXML
    private Button botonVolver;

    @FXML
    private Button botonGuardar;

    @Autowired
    private GestorTitular gestorTitular;


    public void accionVolver(){
        try{
            Stage currentStage = (Stage) botonVolver.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void accionGuardar(){
        try {
            validarDatos();
            TitularDTO titular = crearTitularDTO();
            gestorTitular.persistir(titular);
            informacionClienteGuardado();
        } catch (Exception e) {
            errorDatosInvalidos(e.getMessage());
        }
    }

    private TitularDTO crearTitularDTO() {
        TitularDTO titularDTO = new TitularDTO();
        titularDTO.setNombre(textNombre.getText());
        titularDTO.setApellido(textApellido.getText());
        //TODO: titularDTO.setClaseSol(generarListaClases());
        titularDTO.setDireccion(textDireccion.getText());
        titularDTO.setNroDoc(nroDocumento.getText());
        Boolean esDonante = false;
        if(comboDonante.getValue() == "SI"){
            esDonante = true;}
        titularDTO.setEsDonante(esDonante);
        titularDTO.setFactorRH(comboFactorRH.getValue());
        titularDTO.setFechaNacimiento(dateFechaNacimiento.getValue());
        titularDTO.setGrupoSanguineo(comboGrupoSanguineo.getValue());
        titularDTO.setTipoDoc(comboTipoDocumento.getValue());
        titularDTO.setSexo(sexo.getValue());
    
        return titularDTO;
    }

    //TODO: Hablar con quien hizo esta parte para charlarlo.
    private ArrayList<TipoClasesLicencia> generarListaClases() {
        ArrayList<TipoClasesLicencia> claseSolicitadas = new ArrayList<>();
        if(choiceClaseA.isSelected()){
            claseSolicitadas.add(TipoClasesLicencia.A);
        }
        if(choiceClaseB.isSelected()){
            claseSolicitadas.add(TipoClasesLicencia.B);
        }
        if(choiceClaseC.isSelected()){
            claseSolicitadas.add(TipoClasesLicencia.C);
        }
        if(choiceClaseD.isSelected()){
            claseSolicitadas.add(TipoClasesLicencia.D);
        }
        if(choiceClaseE.isSelected()){
            claseSolicitadas.add(TipoClasesLicencia.E);
        }
        if(choiceClaseF.isSelected()){
            claseSolicitadas.add(TipoClasesLicencia.F);
        }
        if(choiceClaseG.isSelected()){
            claseSolicitadas.add(TipoClasesLicencia.G);
        }
        return claseSolicitadas;
    }

    private void validarDatos() throws DatosInvalidosException {
        Boolean datoInvalido = false;
        datoInvalido |= validarDocumento();
        datoInvalido |= validarNombreYapellido();
        datoInvalido |= validarNacimiento();
        datoInvalido |= validarDireccion();
        datoInvalido |= validarComboboxes();
        if (datoInvalido){
            throw new DatosInvalidosException("Advertencia: por favor, revise los campos ingresados y vuelva a intentarlo");
        }
    }

     private Boolean validarNacimiento() {
        Boolean esInvalido = false;

        if(dateFechaNacimiento.getValue() != null){
            fechaObligatorio.setVisible(false);
           /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(dateFechaNacimiento.getValue().parse("yyyy-MM-dd")))
            FALTA LA LOGICA SI SE INGRESA A MANO*/
            if(dateFechaNacimiento.getValue().isAfter(LocalDate.now().minusYears(18))){
                esInvalido=true;
                fechaObligatorio.setText("El titular debe tener más de 18 años");
                fechaObligatorio.setVisible(true);
            } 
        } else {
            fechaObligatorio.setText("*Campo Obligatorio*");
            fechaObligatorio.setVisible(true);
            esInvalido=true;
        }
        return esInvalido;
    }

    private Boolean validarComboboxes() {
        Boolean esInvalido = false;
        
        if(comboDonante.getValue() == null){
            esInvalido = true;
            donanteObligatorio.setVisible(true);
        } else {
            donanteObligatorio.setVisible(false);
        }
        if(comboFactorRH.getValue() == null) {
            esInvalido = true;
            factorRHObligatorio.setVisible(true);
        } else{
            factorRHObligatorio.setVisible(false);
        }
        if(comboGrupoSanguineo.getValue() == null){
            esInvalido = true;
            gruposObligatorio.setVisible(true);
        } else {
            gruposObligatorio.setVisible(false);
        }
        if(sexo.getValue() == null){
            esInvalido = true;
            sexoObligatorio.setVisible(true);
        } else {
            sexoObligatorio.setVisible(false);
        }

        return esInvalido;
    }

    private Boolean validarDireccion() {
        Boolean esInvalido = false;
        if(!textDireccion.getText().isEmpty()){
            dirObligatorio.setVisible(false);
            if(textDireccion.getText().length()>64){
                dirObligatorio.setText("Máximo 64 caracteres");
                dirObligatorio.setVisible(true);
                esInvalido = true;
            }
        } else {
            dirObligatorio.setText("*Campo Obligatorio*");
            dirObligatorio.setVisible(true);
            esInvalido = true;
        }
        return esInvalido;
    }

    private Boolean validarNombreYapellido() {
        Boolean esInvalido = false;
        if(!textNombre.getText().isEmpty() || !textApellido.getText().isEmpty()){
            nombreObligatorio.setVisible(false);
            apellidoObligatorio.setVisible(false);
            if(textNombre.getText().length() >32 || textApellido.getText().length() > 32){
                nombreObligatorio.setText("Máximo 32 caracteres");
                nombreObligatorio.setVisible(true);
                apellidoObligatorio.setText("Máximo 32 caracteres");
                apellidoObligatorio.setVisible(true);
                esInvalido = true;
            } else {
                nombreObligatorio.setVisible(false);
                apellidoObligatorio.setVisible(false);
                if(!textNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ-]+")){
                    nombreObligatorio.setText("Caracteres inválidos");
                    nombreObligatorio.setVisible(true);
                    esInvalido = true;
                } 
                if(!textApellido.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ-]+")){
                    apellidoObligatorio.setText("Caracteres inválidos");
                    apellidoObligatorio.setVisible(true);
                    esInvalido = true;
                }
            }
        } else {
            esInvalido = true;
            nombreObligatorio.setText("*Campo Obligatorio*");
            apellidoObligatorio.setText("*Campo Obligatorio*");
            nombreObligatorio.setVisible(true);
            apellidoObligatorio.setVisible(true);
        }
        return esInvalido;
    }

    private Boolean validarDocumento() {
        Boolean esInvalido = false;

        if(comboTipoDocumento.getValue()!= null){
            tipoObligatorio.setVisible(false);
            if(comboTipoDocumento.getValue() == TipoDoc.DNI){
                if (nroDocumento.getText().isEmpty()) {
                    esInvalido = true;     
                    numObligatorio.setVisible(true);               
                } else {
                    numObligatorio.setVisible(false);
                    if(!nroDocumento.getText().matches("^\\d{8}$")){
                        numObligatorio.setText("Formato: 99999999");
                        numObligatorio.setVisible(true);
                        nroDocumento.setText(null);
                        esInvalido=true;
                    }
                }
            } else {
                if (nroDocumento.getText().isEmpty()) {
                    esInvalido = true;     
                    numObligatorio.setVisible(true);               
                } else {
                    numObligatorio.setVisible(false);
                    if(!nroDocumento.getText().matches("^[a-zA-Z]{3}\\d{6}$")){
                        numObligatorio.setText("Formato: AAA999999");
                        numObligatorio.setVisible(true);
                        nroDocumento.setText(null);
                        esInvalido=true;
                    }
                }
            }
        } else {
            esInvalido = true;
            tipoObligatorio.setVisible(true);
            numObligatorio.setText("*Campo Obligatorio*");
            numObligatorio.setVisible(true);
        }
        
        return esInvalido;
    }

    private void errorDatosInvalidos(String message) {
        Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Times New Roman", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }

    private void informacionClienteGuardado() {
        Alert alert = new Alert(AlertType.INFORMATION, "Importante: El titular ha sido dado de alta.", ButtonType.OK);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Times New Roman", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
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
        sexo.getItems().addAll(TipoSexo.values());
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
        sexoObligatorio.setVisible(false);
    }
}