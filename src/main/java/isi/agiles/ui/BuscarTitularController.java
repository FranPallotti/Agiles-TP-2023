package isi.agiles.ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BuscarTitularController {

    @FXML
    private TableColumn<?, ?> apellidosColumn;

    @FXML
    private Button buscarButton;

    @FXML
    private Button confirmarButton;

    @FXML
    private TableColumn<?, ?> nombresCollumn;

    @FXML
    private TextField nroDoc;

    @FXML
    private TableColumn<?, ?> numDocColumn;

    @FXML
    private TableView<?> tablaTitulares;

    @FXML
    private ComboBox<?> tipoDoc;

    @FXML
    private TableColumn<?, ?> tipoDocColumn;

    @FXML
    private Button volverAtrasButton;

}
