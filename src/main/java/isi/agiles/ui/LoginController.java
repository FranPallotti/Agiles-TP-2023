package isi.agiles.ui;



import java.io.IOException;

import isi.agiles.App;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button botonIngresar;

    @FXML
    private TextField campoNombreUsuario;

    @FXML
    public void ingresarCliqueado(ActionEvent e){
        UsuarioDTO dto= new UsuarioDTO();
        dto.setNombreUsuario(campoNombreUsuario.getText());
        try{
            dto=GestorUsuario.getUsuarioDTO(GestorUsuario.getUsuario(dto));
            App.setUsuarioLogeado(dto);
            Stage currentStage = (Stage) botonIngresar.getScene().getWindow();
            App.cambiarVentana("MenuPrincipal.fxml",currentStage );
        }
        catch(ObjetoNoEncontradoException o){
            System.out.println(o.getMessage());
        }
        catch(IOException io){
            io.printStackTrace();
        }
        

    }
}

