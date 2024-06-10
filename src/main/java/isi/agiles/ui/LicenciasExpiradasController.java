package isi.agiles.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import isi.agiles.App;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorLicencia;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LicenciasExpiradasController implements Initializable {
    @FXML
    private Text tituloText;
    @FXML
    private ImageView muniImageView;
    @FXML
    private TableView<LicenciaDTO> listadoExpiradasTable;
    @FXML
    private TableColumn<LicenciaDTO,LocalDate> fechaVencimientoColumn;
    @FXML
    private TableColumn<LicenciaDTO,Character> claseLicColumn;
    @FXML
    private TableColumn<LicenciaDTO,String> nombreColumn;
    @FXML
    private TableColumn<LicenciaDTO,String> apellidoColumn;
    @FXML
    private TableColumn<LicenciaDTO,TipoDoc> tipoDocColumn;
    @FXML
    private TableColumn<LicenciaDTO,String> nroDocColumn;
    @FXML
    private Button volverButton;
    @FXML
    private Text licenciasNoEncontradasText;

    private GestorLicencia gestorLicencia = new GestorLicencia();

    public void inicializarTabla(){
        fechaVencimientoColumn.setCellValueFactory(
            cellData -> new SimpleObjectProperty<LocalDate>(cellData.getValue().getFinVigencia())
        );
        claseLicColumn.setCellValueFactory(
            cellData -> new SimpleObjectProperty<Character>(cellData.getValue().getClaseLic().getClase())
        );
        nombreColumn.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getTitular().getNombre())
        );
        apellidoColumn.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getTitular().getApellido())
        );
        tipoDocColumn.setCellValueFactory(
            cellData -> new SimpleObjectProperty<TipoDoc>(cellData.getValue().getTitular().getTipoDoc())
        );
        nroDocColumn.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getTitular().getNroDoc())
        );
    }

    public void actualizarTabla(){
        try{
            listadoExpiradasTable.getItems().clear();
            List<LicenciaDTO> listadoExpiradas = gestorLicencia.getLicenciasExpiradasDTO();
            ObservableList<LicenciaDTO> datosTabla = FXCollections.observableArrayList(listadoExpiradas);
            listadoExpiradasTable.setItems(datosTabla);
            listadoExpiradasTable.setVisible(true);
        }catch(ObjetoNoEncontradoException e){
            listadoExpiradasTable.setVisible(false);
        }
        return;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarTabla();
        listadoExpiradasTable.setVisible(false);
        licenciasNoEncontradasText.setVisible(false);
        actualizarTabla();
        licenciasNoEncontradasText.visibleProperty().bind(listadoExpiradasTable.visibleProperty().not());
    }
        @FXML
    void accionVolver(ActionEvent event) {
        try{
            Stage currentStage = (Stage) volverButton.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml", currentStage);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
