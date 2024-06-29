import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import isi.agiles.dao.UsuarioDAO;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoRol;
import isi.agiles.entidad.TipoSexo;
import isi.agiles.entidad.Usuario;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorUsuario;
import isi.agiles.util.DatosInvalidosException;
import isi.agiles.util.EntityManagerUtil;


public class GestorUsuarioTest {
    private UsuarioDAO dao;
    private GestorUsuario gestor;
    
    @BeforeEach
    public void setUp(){
        EntityManagerUtil.createEntityManagerFactory();
        dao = Mockito.mock(UsuarioDAO.class);
        gestor = new GestorUsuario();

        /*try{
            injectMocks(gestor, "usuarioDao", dao);
        }
        catch(Exception e){
            System.out.println("Error inyectando dependencias en testing");
        }*/
      
    }

    private void injectMocks(Object target, String fieldName, Object mock) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }

    @Test
    void exitoNoModificarDatos() throws ObjetoNoEncontradoException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        
        assertDoesNotThrow(() -> {gestor.modificarUsuario(dto);});   
    }

    @Test
    void errorNombreMas32Caracteres() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("CamilaaaaaCamilaaaaaCamilaaaaaCamilaaaaa");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        

        assertThrows(DatosInvalidosException.class, () -> {gestor.modificarUsuario(dto);});  
    }


    @Test
    void errorNombreNull() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        

        assertThrows(DatosInvalidosException.class, () -> {gestor.modificarUsuario(dto);});  
    }

    @Test
    void exitoNombreCorrecto() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Paula");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        
        assertDoesNotThrow((() -> {gestor.modificarUsuario(dto);}));
        assertEquals(user.getNombre(), dto.getNombre()); 
    }

    @Test
    void errorApellidoMas32Caracteres() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("KerpsKerpsKerpsKerpsKerpsKerpsKerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        

        assertThrows(DatosInvalidosException.class, () -> {gestor.modificarUsuario(dto);});  
    }


    @Test
    void errorApellidoNull() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        

        assertThrows(DatosInvalidosException.class, () -> {gestor.modificarUsuario(dto);});  
    }

    @Test
    void exitoApellidoCorrecto() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Perez");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        
        assertDoesNotThrow((() -> {gestor.modificarUsuario(dto);}));
        assertEquals(user.getApellido(), dto.getApellido()); 
    }

    @Test
    void errorFechaNull() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(null);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }

        assertThrows(DatosInvalidosException.class, (() -> {gestor.modificarUsuario(dto);}));
    }

    @Test
    void errorFechaMenorA18() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        String fecha2 = "2020-09-11";
        LocalDate date2 = LocalDate.parse(fecha2); 
        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date2);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }

        assertThrows(DatosInvalidosException.class, (() -> {gestor.modificarUsuario(dto);}));
    }

    
    @Test
    void exitoFechaCorrecta() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        String fecha2 = "2002-09-12";
        LocalDate date2 = LocalDate.parse(fecha2); 
        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Perez");
        dto.setFechaNacimiento(date2);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        
        assertDoesNotThrow((() -> {gestor.modificarUsuario(dto);}));
        assertEquals(user.getFechaNacimiento(), dto.getFechaNaciemiento()); 
    }

    @Test
    void errorMailNull() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }

        assertThrows(DatosInvalidosException.class, (() -> {gestor.modificarUsuario(dto);}));
    }

    @Test
    void errorMailFormatoIncorrecto() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }

        assertThrows(DatosInvalidosException.class, (() -> {gestor.modificarUsuario(dto);}));
    }

    
    @Test
    void exitoMailCorrecto() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        String fecha2 = "2002-09-12";
        LocalDate date2 = LocalDate.parse(fecha2); 
        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date2);
        dto.setMail("camila@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        
        assertDoesNotThrow((() -> {gestor.modificarUsuario(dto);}));
        assertEquals(user.getMail(), dto.getMail()); 
    }

    @Test
    void errorNombreUsuarioMas32Caracteres() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerpsamilakerpsamilakerpsamilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }

        assertThrows(DatosInvalidosException.class, (() -> {gestor.modificarUsuario(dto);}));
    }

    @Test
    void errorNombreUsuarioNull() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }

        assertThrows(DatosInvalidosException.class, (() -> {gestor.modificarUsuario(dto);}));
    }

    @Test
    void exitoNombreUsuarioCorrecto() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camikerpss");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        
        assertDoesNotThrow((() -> {gestor.modificarUsuario(dto);}));
        assertEquals(user.getNombreUsuario(), dto.getNombreUsuario());
    }

    @Test
    void errorSexoNull() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(null);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }

    }

    @Test
    void exitoSexoCorrecto() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camikerpss");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.MASCULINO);
        dto.setRol(TipoRol.ADMINISTRADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        
        assertDoesNotThrow((() -> {gestor.modificarUsuario(dto);}));
        assertEquals(user.getSexo(), dto.getSexo());
    }

    @Test
    void errorRolNull() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(null);


        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }

        assertThrows(DatosInvalidosException.class, (() -> {gestor.modificarUsuario(dto);}));
    }

    @Test
    void exitoRolCorrecto() throws DatosInvalidosException{
        Usuario user = new Usuario(); 
        String fecha = "2002-09-11";
        LocalDate date = LocalDate.parse(fecha); 
        user.setApellido("Kerps");
        user.setFechaNacimiento(date);
        user.setMail("camilakerps@hotmail.com");
        user.setNombre("Camila");
        user.setNombreUsuario("camilakerps");
        user.setNumDoc("99999999");
        user.setTipoDoc(TipoDoc.DNI);
        user.setSexo(TipoSexo.FEMENINO);
        user.setRol(TipoRol.ADMINISTRADOR);

        UsuarioDTO dto = new UsuarioDTO();
        dto.setApellido("Kerps");
        dto.setFechaNacimiento(date);
        dto.setMail("camilakerps@hotmail.com");
        dto.setNombre("Camila");
        dto.setNombreUsuario("camilakerps");
        dto.setNumDoc("99999999");
        dto.setTipoDoc(TipoDoc.DNI);
        dto.setSexo(TipoSexo.FEMENINO);
        dto.setRol(TipoRol.OPERADOR);

        try {
            Mockito.when(dao.getbyDocumento(TipoDoc.DNI, "99999999")).thenReturn(Optional.of(user));
            injectMocks(gestor, "usuarioDao", dao);
        } catch (Exception e) {
            System.out.println("Error inyectando dependencias en testing");
        }
        
        assertDoesNotThrow((() -> {gestor.modificarUsuario(dto);}));
        assertEquals(user.getNombreUsuario(), dto.getNombreUsuario());
    }


}
