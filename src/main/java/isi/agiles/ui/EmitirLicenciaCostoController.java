package isi.agiles.ui;

import isi.agiles.dto.LicenciaDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EmitirLicenciaCostoController {

    @FXML
    private Button botonEmitir;

    @FXML
    private TextField campoApellido;

    @FXML
    private TextField campoClaseLicencia;

    @FXML
    private TextField campoCostoVigencia;

    @FXML
    private TextField campoFechaNacimiento;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoNumeroDoc;

    @FXML
    private TextField campoTipoDocumento;

    @FXML
    private TextField campoVigenteDesde;

    @FXML
    private TextField campoVigenteHasta;

    private LicenciaDTO licencia;


    public LicenciaDTO getLicencia() {
        return licencia;
    }

    public void setLicencia(LicenciaDTO l) {
        this.licencia=l;
    }



    


}
