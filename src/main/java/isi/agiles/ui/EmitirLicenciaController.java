package isi.agiles.ui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import isi.agiles.App;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.Titular;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorTitular;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EmitirLicenciaController{
    private TitularDTO titular;



    

    @FXML
    private Button botonVolverAtras;

    @FXML
    void volverAtrasCliqueado(ActionEvent event) {
        try{
            Stage currentStage = (Stage) botonVolverAtras.getScene().getWindow();
            App.cambiarVentana("BuscarTitular.fxml", currentStage);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void setTitularDTO(TitularDTO t){
        this.titular=t;
    }
    public TitularDTO getTitularDTO(){
        return this.titular;
    }

    


}
