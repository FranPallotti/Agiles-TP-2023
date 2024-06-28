import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import isi.agiles.dao.LicenciaDAO;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.dto.TitularDTO;

import isi.agiles.entidad.EstadoLicencia;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoFactorRH;
import isi.agiles.entidad.TipoGrupoS;
import isi.agiles.entidad.TipoSexo;
import isi.agiles.excepcion.NoCumpleCondicionesLicenciaException;
import isi.agiles.excepcion.NoPuedeRenovarExisteLicencia;
import isi.agiles.excepcion.NoPuedeRenovarVigenciaTemprana;
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
    GestorLicencia gestorLicencia;
    @BeforeEach
    void setUp(){
        //licenciaDao = Mockito.mock(LicenciaDAO.class);
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

    

   
}
