package isi.agiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import isi.agiles.entidad.*;
import isi.agiles.logica.GestorUsuario;

import isi.agiles.dao.ClaseLicenciaDAO;
import isi.agiles.dao.TitularDAO;
import isi.agiles.dao.UsuarioDAO;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.entidad.ClaseLicencia;
import isi.agiles.entidad.CostoLicencia;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoRol;
import isi.agiles.entidad.TipoSexo;
import isi.agiles.entidad.Usuario;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorUsuario;
import isi.agiles.util.EntityManagerUtil;
import isi.agiles.util.Poblador;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static UsuarioDTO usuarioLogueado = new UsuarioDTO();

    public static void main(String[] args) {
        EntityManagerUtil.createEntityManagerFactory();
        //App.poblar();
        launch();
    }

    private static void primeraEjecucion(){
        App.poblar();
        completarUsuario(usuarioLogueado);
    }

    //ESTO DSPS DEBERIA REEMPLAZARSE POR LAS CREDENCIALES DE INICIO DE SESION
    private static void completarUsuario(UsuarioDTO usuario) {
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setFechaNacimiento(LocalDate.of(2003,03,04));
        usuario.setMail("ejemplo@gmail.com");
        usuario.setTipoDoc(TipoDoc.DNI);
        usuario.setNumDoc("22103847");
        usuario.setSexo(TipoSexo.MASCULINO);
        usuario.setNombreUsuario("JPerez");
        usuario.setRol(TipoRol.ADMINISTRADOR);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Image icono =  new Image("isi/agiles/logoStaFe.png");
        scene = new Scene(loadFXML("PanelLogin"));
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

    public static UsuarioDTO getUsuarioLogueado(){
        return usuarioLogueado;
    }
    public static void setUsuarioLogeado(UsuarioDTO u){
        usuarioLogueado=u;
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

    public static void loguear(UsuarioDTO dto){

        //UsuarioDTO dto= new UsuarioDTO();
        GestorUsuario gestorUsuario = new GestorUsuario();
        dto.setNombreUsuario("franpallotti");
        try{
            dto=gestorUsuario.getUsuarioDTO(gestorUsuario.getUsuario(dto));
        }
        catch(ObjetoNoEncontradoException e){
            System.out.println(e.getMessage());
        }

        
    }
    public static void poblar(){
        /*
        TitularDAO t = new TitularDAO();
            Titular titular1= new Titular();
            titular1.setApellido("Pallotti");
            titular1.setNombre("Francisco");
            titular1.setTipoDoc(TipoDoc.DNI);
            titular1.setNroDoc("42925453");
            
           t.saveInstance(titular1);

            Titular titular2 = new Titular();
            titular2.setApellido("Pallotti");
            titular2.setNombre("Fernando");
            titular2.setTipoDoc(TipoDoc.DNI);
            titular2.setNroDoc("42925454");

           t.saveInstance(titular2);
        */
            UsuarioDAO userDAO= new UsuarioDAO();
            Usuario user= new Usuario();
            user.setApellido("Pallotti");
            user.setFechaNacimiento(LocalDate.of(2000, 9, 28));
            user.setMail("franpallotti@gmail.com");
            user.setNombre("Francisco");
            user.setNombreUsuario("franpallotti");
            user.setNumDoc("42925453");
            user.setRol(TipoRol.ADMINISTRADOR);
            user.setSexo(TipoSexo.MASCULINO);
            user.setTipoDoc(TipoDoc.DNI);
            userDAO.saveInstance(user);
            poblarLicenciaClaseA();
            poblarLicenciaClaseB();
            poblarLicenciaClaseC();
            poblarLicenciaClaseD();
            poblarLicenciaClaseE();
            poblarLicenciaClaseG();
           
    
        }

        private static void poblarLicenciaClaseA(){
            ClaseLicenciaDAO d = new ClaseLicenciaDAO();
           ClaseLicencia a= new ClaseLicencia();
           a.setClase('A');
           a.setDescripcion("Licencia de clase A");
           a.setEdadMinima(17);
           a.setEsProfesional(false);
           List<CostoLicencia> c = new ArrayList<CostoLicencia>();
           CostoLicencia costoA1= new CostoLicencia();
           costoA1.setClase(a);
           costoA1.setCosto(Float.parseFloat("20.0"));
           costoA1.setDuracion(Integer.parseInt("1"));
           costoA1.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA1);
           CostoLicencia costoA2 = new CostoLicencia();
           costoA2.setClase(a);
           costoA2.setCosto(Float.parseFloat("25.0"));
           costoA2.setDuracion(Integer.parseInt("3"));
           costoA2.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA2);
           CostoLicencia costoA3 = new CostoLicencia();
           costoA3.setClase(a);
           costoA3.setCosto(Float.parseFloat("30.0"));
           costoA3.setDuracion(Integer.parseInt("4"));
           costoA3.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA3);
           CostoLicencia costoA4 = new CostoLicencia();
           costoA4.setClase(a);
           costoA4.setCosto(Float.parseFloat("40.0"));
           costoA4.setDuracion(Integer.parseInt("5"));
           costoA4.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA4);
           a.setCostoClase(c);
           d.saveInstance(a);
        }
        private static void poblarLicenciaClaseB(){
            ClaseLicenciaDAO d = new ClaseLicenciaDAO();
           ClaseLicencia a= new ClaseLicencia();
           a.setClase('B');
           a.setDescripcion("Licencia de clase B");
           a.setEdadMinima(17);
           a.setEsProfesional(false);
           List<CostoLicencia> c = new ArrayList<CostoLicencia>();
           CostoLicencia costoA1= new CostoLicencia();
           costoA1.setClase(a);
           costoA1.setCosto(Float.parseFloat("20.0"));
           costoA1.setDuracion(Integer.parseInt("1"));
           costoA1.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA1);
           CostoLicencia costoA2 = new CostoLicencia();
           costoA2.setClase(a);
           costoA2.setCosto(Float.parseFloat("25.0"));
           costoA2.setDuracion(Integer.parseInt("3"));
           costoA2.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA2);
           CostoLicencia costoA3 = new CostoLicencia();
           costoA3.setClase(a);
           costoA3.setCosto(Float.parseFloat("30.0"));
           costoA3.setDuracion(Integer.parseInt("4"));
           costoA3.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA3);
           CostoLicencia costoA4 = new CostoLicencia();
           costoA4.setClase(a);
           costoA4.setCosto(Float.parseFloat("40.0"));
           costoA4.setDuracion(Integer.parseInt("5"));
           costoA4.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA4);
           a.setCostoClase(c);
           d.saveInstance(a);
        }
        private static void poblarLicenciaClaseC(){
            ClaseLicenciaDAO d = new ClaseLicenciaDAO();
           ClaseLicencia a= new ClaseLicencia();
           a.setClase('C');
           a.setDescripcion("Licencia de clase C");
           a.setEdadMinima(21);
           a.setEsProfesional(true);
           List<CostoLicencia> c = new ArrayList<CostoLicencia>();
           CostoLicencia costoA1= new CostoLicencia();
           costoA1.setClase(a);
           costoA1.setCosto(Float.parseFloat("23.0"));
           costoA1.setDuracion(Integer.parseInt("1"));
           costoA1.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA1);
           CostoLicencia costoA2 = new CostoLicencia();
           costoA2.setClase(a);
           costoA2.setCosto(Float.parseFloat("30.0"));
           costoA2.setDuracion(Integer.parseInt("3"));
           costoA2.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA2);
           CostoLicencia costoA3 = new CostoLicencia();
           costoA3.setClase(a);
           costoA3.setCosto(Float.parseFloat("35.0"));
           costoA3.setDuracion(Integer.parseInt("4"));
           costoA3.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA3);
           CostoLicencia costoA4 = new CostoLicencia();
           costoA4.setClase(a);
           costoA4.setCosto(Float.parseFloat("47.0"));
           costoA4.setDuracion(Integer.parseInt("5"));
           costoA4.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA4);
           a.setCostoClase(c);
           d.saveInstance(a);
        }
        private static void poblarLicenciaClaseE(){
            ClaseLicenciaDAO d = new ClaseLicenciaDAO();
           ClaseLicencia a= new ClaseLicencia();
           a.setClase('E');
           a.setDescripcion("Licencia de clase E");
           a.setEdadMinima(21);
           a.setEsProfesional(true);
           List<CostoLicencia> c = new ArrayList<CostoLicencia>();
           CostoLicencia costoA1= new CostoLicencia();
           costoA1.setClase(a);
           costoA1.setCosto(Float.parseFloat("29.0"));
           costoA1.setDuracion(Integer.parseInt("1"));
           costoA1.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA1);
           CostoLicencia costoA2 = new CostoLicencia();
           costoA2.setClase(a);
           costoA2.setCosto(Float.parseFloat("39.0"));
           costoA2.setDuracion(Integer.parseInt("3"));
           costoA2.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA2);
           CostoLicencia costoA3 = new CostoLicencia();
           costoA3.setClase(a);
           costoA3.setCosto(Float.parseFloat("44.0"));
           costoA3.setDuracion(Integer.parseInt("4"));
           costoA3.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA3);
           CostoLicencia costoA4 = new CostoLicencia();
           costoA4.setClase(a);
           costoA4.setCosto(Float.parseFloat("59.0"));
           costoA4.setDuracion(Integer.parseInt("5"));
           costoA4.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA4);
           a.setCostoClase(c);
           d.saveInstance(a);
        }

        private static void poblarLicenciaClaseD(){
            ClaseLicenciaDAO d = new ClaseLicenciaDAO();
           ClaseLicencia a= new ClaseLicencia();
           a.setClase('D');
           a.setDescripcion("Licencia de clase D");
           a.setEdadMinima(21);
           a.setEsProfesional(true);
           List<CostoLicencia> c = new ArrayList<CostoLicencia>();
           CostoLicencia costoA1= new CostoLicencia();
           costoA1.setClase(a);
           costoA1.setCosto(Float.parseFloat("23.0"));
           costoA1.setDuracion(Integer.parseInt("1"));
           costoA1.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA1);
           CostoLicencia costoA2 = new CostoLicencia();
           costoA2.setClase(a);
           costoA2.setCosto(Float.parseFloat("30.0"));
           costoA2.setDuracion(Integer.parseInt("3"));
           costoA2.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA2);
           CostoLicencia costoA3 = new CostoLicencia();
           costoA3.setClase(a);
           costoA3.setCosto(Float.parseFloat("35.0"));
           costoA3.setDuracion(Integer.parseInt("4"));
           costoA3.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA3);
           CostoLicencia costoA4 = new CostoLicencia();
           costoA4.setClase(a);
           costoA4.setCosto(Float.parseFloat("47.0"));
           costoA4.setDuracion(Integer.parseInt("5"));
           costoA4.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA4);
           a.setCostoClase(c);
           d.saveInstance(a);
        }
        private static void poblarLicenciaClaseG(){
            ClaseLicenciaDAO d = new ClaseLicenciaDAO();
           ClaseLicencia a= new ClaseLicencia();
           a.setClase('G');
           a.setDescripcion("Licencia de clase G");
           a.setEdadMinima(17);
           a.setEsProfesional(false);
           List<CostoLicencia> c = new ArrayList<CostoLicencia>();
           CostoLicencia costoA1= new CostoLicencia();
           costoA1.setClase(a);
           costoA1.setCosto(Float.parseFloat("20.0"));
           costoA1.setDuracion(Integer.parseInt("1"));
           costoA1.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA1);
           CostoLicencia costoA2 = new CostoLicencia();
           costoA2.setClase(a);
           costoA2.setCosto(Float.parseFloat("25.0"));
           costoA2.setDuracion(Integer.parseInt("3"));
           costoA2.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA2);
           CostoLicencia costoA3 = new CostoLicencia();
           costoA3.setClase(a);
           costoA3.setCosto(Float.parseFloat("30.0"));
           costoA3.setDuracion(Integer.parseInt("4"));
           costoA3.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA3);
           CostoLicencia costoA4 = new CostoLicencia();
           costoA4.setClase(a);
           costoA4.setCosto(Float.parseFloat("40.0"));
           costoA4.setDuracion(Integer.parseInt("5"));
           costoA4.setCostoAdministrativo(Float.parseFloat("8.0"));
           c.add(costoA4);
           a.setCostoClase(c);
           d.saveInstance(a);
        }
    
}