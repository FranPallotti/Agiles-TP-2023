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
import isi.agiles.ui.elementos.Page;
import isi.agiles.ui.elementos.TableViewWithPagination;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
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
    @FXML
    private Pagination tablaPagination;
    
    private List<LicenciaDTO> listadoExpiradas;

    private TableViewWithPagination<LicenciaDTO> gestorTablaPagination;

    private final int FILAS_POR_PAGINA = 15;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            listadoExpiradas = gestorLicencia.getLicenciasExpiradasDTO();
            inicializarTabla();
            gestorTablaPagination = new TableViewWithPagination<LicenciaDTO>(
                new Page<>(listadoExpiradas,FILAS_POR_PAGINA), listadoExpiradasTable
            );
            tablaPagination = gestorTablaPagination.getTableViewWithPaginationPane();
            listadoExpiradasTable.visibleProperty().bind(tablaPagination.visibleProperty());
            tablaPagination.setVisible(true);
        }catch(ObjetoNoEncontradoException e){
            licenciasNoEncontradasText.setVisible(true);
        }
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
