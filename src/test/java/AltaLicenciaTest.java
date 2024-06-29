import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import isi.agiles.dao.LicenciaDAO;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.dto.TitularDTO;

import isi.agiles.entidad.Licencia;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.excepcion.NoCumpleCondicionesLicenciaException;
import isi.agiles.excepcion.NoPuedeEmitirExisteLicenciaException;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorClaseLicencia;
import isi.agiles.logica.GestorLicencia;
import isi.agiles.logica.GestorTitular;
import isi.agiles.logica.GestorUsuario;
import isi.agiles.util.EntityManagerUtil;

public class AltaLicenciaTest {
    //Esta clase testea el metodo de alta licencia del Gestor de Licencias

    
    private GestorTitular gestorTitular;
    private GestorClaseLicencia gestorClaseLic;
    private GestorUsuario gestorUsuario;
    private GestorLicencia gestorLicencia;
    private LicenciaDAO licenciaDAO;
    @BeforeEach
    void setUp(){
        licenciaDAO = Mockito.mock(LicenciaDAO.class);
        EntityManagerUtil.createEntityManagerFactory();
        gestorLicencia = new GestorLicencia();
        gestorTitular = Mockito.mock(GestorTitular.class);
        gestorClaseLic = Mockito.mock(GestorClaseLicencia.class);
        gestorUsuario = Mockito.mock(GestorUsuario.class);
        
        
        
        try{
            injectMocks(gestorLicencia, "gestorClaseLic", gestorClaseLic);
            injectMocks(gestorLicencia, "gestorUsuario", gestorUsuario);
        }
        catch(Exception e){
            System.out.println("Error inyectando dependencias en testing");
        }
        
    }

    private void injectMocks(Object target, String fieldName, Object mock) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }

    //Testeamos titular no existente
    @Test 
    void testTitularNoExistente(){
        LicenciaDTO l = new LicenciaDTO();
        TitularDTO t= new TitularDTO();
        t.setNroDoc("00000000");
        t.setTipoDoc(TipoDoc.DNI);
        l.setTitular(t);
        ClaseLicenciaDTO clase=  new ClaseLicenciaDTO();
        clase.setClase('A');
        l.setClaseLic(clase);
        l.setCosto(Float.valueOf("42.0"));
        try{
            Mockito.when(gestorTitular.puedeTenerLicencia(any(TitularDTO.class), any(ClaseLicenciaDTO.class))).thenThrow(new ObjetoNoEncontradoException());
            injectMocks(gestorLicencia, "gestorTitular", gestorTitular);
        }
        catch(Exception e){
            System.out.println("Error inyectando dependencias en testing");
        }

        //Mockito.when(gestorLicencia.altaLicencia(l)).thenThrow(new ObjetoNoEncontradoException());
        assertThrows(ObjetoNoEncontradoException.class, 
        () ->{gestorLicencia.altaLicencia(l);});
    }
    //Testeamos titular null
    @Test 
    void testTitularNull(){
        LicenciaDTO l = new LicenciaDTO();
        
        ClaseLicenciaDTO clase=  new ClaseLicenciaDTO();
        clase.setClase('A');
        l.setClaseLic(clase);
        l.setCosto(Float.valueOf("42.0"));

        
        assertThrows(NullPointerException.class, 
        () ->{gestorLicencia.altaLicencia(l);});
    }
    //Testeamos un titular que no cumple con las condiciones para la licencia
    @Test 
    void testTitularNoCumpleCondiciones(){
        try{
            Mockito.when(gestorTitular.puedeTenerLicencia(any(TitularDTO.class), any(ClaseLicenciaDTO.class))).thenReturn(false);
            injectMocks(gestorLicencia, "gestorTitular", gestorTitular);
        }
        catch(Exception e){
            System.out.println("Error inyectando dependencias en testing");
        }
        
        LicenciaDTO l = new LicenciaDTO();
        TitularDTO t = new TitularDTO();
        l.setTitular(t);
        
        ClaseLicenciaDTO clase=  new ClaseLicenciaDTO();
        clase.setClase('A');
        l.setClaseLic(clase);
        l.setCosto(Float.valueOf("42.0"));

        assertThrows(NoCumpleCondicionesLicenciaException.class, 
        () ->{gestorLicencia.altaLicencia(l);});
    }
    //Intenta crear una licencia de una clase para la cual el titular ya tiene una licencia vigente
    @Test 
    void testCrearLicenciaYaVigente(){
        try{
            Mockito.when(gestorTitular.puedeTenerLicencia(any(TitularDTO.class), any(ClaseLicenciaDTO.class))).thenReturn(true);
            Mockito.when(gestorTitular.tieneLicenciasVigentes(any(TitularDTO.class), any(ClaseLicenciaDTO.class))).thenReturn(true);
            injectMocks(gestorLicencia, "gestorTitular", gestorTitular);
        }
        catch(Exception e){
            System.out.println("Error inyectando dependencias en testing");
        }
        LicenciaDTO l = new LicenciaDTO();
        TitularDTO t = new TitularDTO();
        l.setTitular(t);
        
        ClaseLicenciaDTO clase=  new ClaseLicenciaDTO();
        clase.setClase('A');
        l.setClaseLic(clase);
        l.setCosto(Float.valueOf("42.0"));
        assertThrows(NoPuedeEmitirExisteLicenciaException.class, 
        () ->{gestorLicencia.altaLicencia(l);});
    }
    //Intenta crear una licencia para una clase inexistente
    @Test
    void testCrearLicenciaClaseInexistente(){
        try{
            Mockito.when(gestorTitular.puedeTenerLicencia(any(TitularDTO.class), any(ClaseLicenciaDTO.class))).thenThrow(new ObjetoNoEncontradoException());
            
            injectMocks(gestorLicencia, "gestorTitular", gestorTitular);
        }
        catch(Exception e){
            System.out.println("Error inyectando dependencias en testing");
        }
        LicenciaDTO l = new LicenciaDTO();
        TitularDTO t = new TitularDTO();
        l.setTitular(t);
        
        ClaseLicenciaDTO clase=  new ClaseLicenciaDTO();
        clase.setClase('A');
        l.setClaseLic(clase);
        l.setCosto(Float.valueOf("42.0"));
        assertThrows(ObjetoNoEncontradoException.class, 
        () ->{gestorLicencia.altaLicencia(l);});
    }

    @Test
    void licenciaCreadaConExito(){
        try{
            Mockito.when(gestorTitular.puedeTenerLicencia(any(TitularDTO.class), any(ClaseLicenciaDTO.class))).thenReturn(true);
            Mockito.when(gestorTitular.tieneLicenciasVigentes(any(TitularDTO.class), any(ClaseLicenciaDTO.class))).thenReturn(false);
            injectMocks(gestorLicencia, "gestorTitular", gestorTitular);
            Mockito.doNothing().when(licenciaDAO).saveInstance(any(Licencia.class));
        }
        catch(Exception e){
            System.out.println("Error inyectando dependencias en testing");
        }

        LicenciaDTO l = new LicenciaDTO();
        TitularDTO t = new TitularDTO();
        l.setTitular(t);
        
        ClaseLicenciaDTO clase=  new ClaseLicenciaDTO();
        clase.setClase('A');
        l.setClaseLic(clase);
        l.setCosto(Float.valueOf("42.0"));
        try{
            assertTrue(gestorLicencia.altaLicencia(l).getClass().equals(Licencia.class));
            verify(licenciaDAO,times(1)).saveInstance(any(Licencia.class));
        }
        catch(Exception e){
            System.out.println("Error en test");
        }
        



    }



    

   
}
