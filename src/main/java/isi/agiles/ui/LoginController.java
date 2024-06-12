package isi.agiles.ui;



import java.io.IOException;

import isi.agiles.App;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button botonIngresar;
    @FXML
    private TextField campoNombreUsuario;
    @FXML
    private Label errorFaltaNombreUsuario;

    private GestorUsuario gestorUsuario = new GestorUsuario();

    @FXML
    public void ingresarCliqueado(ActionEvent e){
        errorFaltaNombreUsuario.setVisible(false);
        if(validarDatos()){
            UsuarioDTO dto= new UsuarioDTO();
            dto.setNombreUsuario(campoNombreUsuario.getText());
            try{
                dto=gestorUsuario.getUsuarioDTO(gestorUsuario.getUsuario(dto));
                App.setUsuarioLogeado(dto);
                Stage currentStage = (Stage) botonIngresar.getScene().getWindow();
                App.cambiarVentana("MenuPrincipal.fxml",currentStage );
            }
            catch(ObjetoNoEncontradoException o){
                usuarioNoEncontrado();
            }
            catch(IOException io){
                io.printStackTrace();
            }
        }
        else{
            datosInvalidos();
        }
        
        

    }


    private Boolean validarDatos(){
        Boolean ret;
        if(campoNombreUsuario.getText().isBlank() || campoNombreUsuario.getText().isEmpty()){
            ret=false;
            errorFaltaNombreUsuario.setVisible(true);

        }
        else{
            ret=true;
        }
        return ret;
    }

    private void datosInvalidos(){
        Alert alert = new Alert(AlertType.WARNING, "Ingrese un usuario", ButtonType.OK);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.getDialogPane().getChildren().stream()
                    .filter(node -> node instanceof Label)
                    .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
            alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
            alert.setResizable(false);
            alert.showAndWait();
    }
    private void usuarioNoEncontrado(){
        Alert alert = new Alert(AlertType.WARNING, "Usuario no encontrado", ButtonType.OK);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(Font.font("Arial Rounded MT Bold", 14)));
        alert.getDialogPane().lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
        alert.setResizable(false);
        alert.showAndWait();
    }
}

