package isi.agiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;

import java.time.LocalDate;

import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoRol;
import isi.agiles.entidad.TipoSexo;
import isi.agiles.entidad.Usuario;
import isi.agiles.util.EntityManagerUtil;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static Scene scene;
    private static Usuario usuarioLogueado = new Usuario();

    public static void main(String[] args) {
        EntityManagerUtil.createEntityManagerFactory();
        completarUsuario(usuarioLogueado); 
        launch();
    }

    //ESTO DSPS DEBERIA REEMPLAZARSE POR LAS CREDENCIALES DE INICIO DE SESION
    private static void completarUsuario(Usuario usuario) {
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setFechaNacimiento(LocalDate.of(2003,03,04));
        usuario.setMail("ejemplo@gmail.com");
        usuario.setTipoDoc(TipoDoc.DNI);
        usuario.setNmoDoc("22103847");
        usuario.setSexo(TipoSexo.MASCULINO);
        usuario.setNombreUsuario("JPerez");
        usuario.setRol(TipoRol.ADMINISTRADOR);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Image icono =  new Image("isi/agiles/logoStaFe.png");
        scene = new Scene(loadFXML("MenuPrincipal"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(icono);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Usuario getUsuarioLogueado(){
        return usuarioLogueado;
    }

    public static void cambiarVentana(String fxml, Stage ventActual) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.getIcons().add(new Image("isi/agiles/logoStaFe.png"));
        ventActual.close();
        stage.show();
    }
}