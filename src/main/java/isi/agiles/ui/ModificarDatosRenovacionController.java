package isi.agiles.ui;

import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.entidad.ClaseLicencia;
import isi.agiles.entidad.TipoClasesLicencia;
import isi.agiles.logica.GestorClaseLicencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModificarDatosRenovacionController {

    @FXML
    private Button botonContinuar;

    @FXML
    private Button botonVolverAtras;

    @FXML
    private TextField campoApellido;

    @FXML
    private ComboBox<ClaseLicenciaDTO> campoClaseLicencia;

    @FXML
    private TextField campoCostoVigencia;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoNroDoc;

    @FXML
    private DatePicker campoVIgenteHasta;

    @FXML
    private DatePicker campoVigenteDesde;

    @FXML
    private CheckBox checkboxRenovarVigencia;

    @FXML
    private Label labelCostoLicencia;

    private LicenciaDTO licencia;
    private GestorClaseLicencia gestorClase= new GestorClaseLicencia();


    @FXML
    void renovarCliqueado(ActionEvent event) {

    }

    @FXML
    void volverAtrasCliqueado(ActionEvent event) {

    }

    public void setLicencia(LicenciaDTO l){
        licencia=l;
    }
    public LicenciaDTO getLicenciaDTO(){
        return licencia;
    }

    public void setearDatos(){
        campoApellido.setText(licencia.getTitular().getApellido());
        campoNombre.setText(licencia.getTitular().getNombre());
        
        campoClaseLicencia.setValue(licencia.getClaseLic());
        
        campoVigenteDesde.setValue(licencia.getInicioVigencia());
        campoVIgenteHasta.setValue(licencia.getFinVigencia());
        
        campoNroDoc.setText(licencia.getTitular().getNroDoc());
        campoCostoVigencia.setText(licencia.getCosto().toString());
    }

}
