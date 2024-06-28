import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
import isi.agiles.logica.GestorLicencia;
import isi.agiles.logica.GestorTitular;

public class AltaLicenciaTest {
    //Esta clase testea el metodo de alta licencia del Gestor de Licencias

    GestorLicencia gestorLicencia;

    @BeforeEach
    void setUp(){
        gestorLicencia = Mockito.mock(GestorLicencia.class);
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

        
        assertThrows(ObjetoNoEncontradoException.class, 
        () ->{gestorLicencia.altaLicencia(l);});
    }
    //Testeamos un titular que no cumple con las condiciones para la licencia
    @Test 
    void testTitularNoCumpleCondiciones(){
        LicenciaDTO l = new LicenciaDTO();
        
        ClaseLicenciaDTO clase=  new ClaseLicenciaDTO();
        clase.setClase('A');
        l.setClaseLic(clase);
        l.setCosto(Float.valueOf("42.0"));

        Mockito.when(gestorLicencia.altaLicencia(l)).thenThrow(new NoCumpleCondicionesLicenciaException());
        assertThrows(NoCumpleCondicionesLicenciaException.class, 
        () ->{gestorLicencia.altaLicencia(l);});
    }

   
}
