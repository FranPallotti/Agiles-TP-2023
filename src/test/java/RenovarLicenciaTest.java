import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import isi.agiles.dao.TitularDAO;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.dto.LicenciaDTO;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.EstadoLicencia;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoFactorRH;
import isi.agiles.entidad.TipoGrupoS;
import isi.agiles.entidad.TipoSexo;
import isi.agiles.entidad.Titular;
import isi.agiles.excepcion.NoPuedeRenovarExisteLicencia;
import isi.agiles.excepcion.NoPuedeRenovarVigenciaTemprana;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorLicencia;
import isi.agiles.logica.GestorTitular;
import isi.agiles.util.EntityManagerUtil;



public class RenovarLicenciaTest {
    private GestorLicencia gestor;
    private TitularDAO titularDAO;
    private GestorTitular gestorTitular;
    
    @BeforeEach
        void setup(){
        EntityManagerUtil.createEntityManagerFactory();
        gestor = new GestorLicencia();
        titularDAO = Mockito.mock(TitularDAO.class);
        gestorTitular = new GestorTitular();
        try{
            injectMocks(gestorTitular, "titularDao", titularDAO);
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
    
    /*Intentar buscar un titular que no existe.  */
    @Test
    void falloNoExisteTitular() throws ObjetoNoEncontradoException{
        //Se prepara el mock con los valores deseados de retorno
        Mockito.when(gestorTitular.getTitularByDocumento("00000000",TipoDoc.DNI)).thenThrow(new ObjetoNoEncontradoException());
        //Se verifica que se arroje la excepcion cuando se ejecuta el metodo
        assertThrows(ObjetoNoEncontradoException.class, 
                    () ->{gestorTitular.getTitularByDocumento("00000000", TipoDoc.DNI);});
    }

    /*En base a un titular existente que tiene solamente una o más licencias expiradas en el listado,
    tratar de renovar alguna de ellas (sin modificar datos del titular). */
    @Test
    void exitoListaExpiradas() throws NoPuedeRenovarVigenciaTemprana, NoPuedeRenovarExisteLicencia{
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        List<LicenciaDTO> listaprueba = new ArrayList<>();

        //Seteo del titular
        titular.setApellido("Gaggiotti");
        titular.setNombre("Pato");
        titular.setDireccion("Velez Sarsfield 999");
        titular.setEsDonante(true);
        titular.setFactorRH(TipoFactorRH.NEGATIVO);
        titular.setFechaNacimiento(LocalDate.of(2003, 03, 04));
        titular.setGrupoSanguineo(TipoGrupoS.ABN);
        titular.setIdTitular((long) 1);
        titular.setNroDoc("44307268");
        titular.setTipoDoc(TipoDoc.DNI);
        titular.setSexo(TipoSexo.MASCULINO);
        //Seteo de la licencia, solamente valores requeridos
        claseA.setClase('A');
        lic1.setTitular(titular);
        lic1.setClaseLic(claseA);
        lic1.setCosto((float) 48.0);
        lic1.setEstado(EstadoLicencia.EXPIRADA);
        lic1.setIdLicencia((long) 1);
        lic1.setInicioVigencia(LocalDate.of(2018, 05, 01));
        lic1.setFinVigencia(LocalDate.of(2022, 05, 01));
        lic1.setObservaciones("Ejemplo");
        listaprueba.add(lic1);
        //Testeo
        assertDoesNotThrow( () -> {gestor.puedeSerRenovada(lic1, listaprueba);}, "No debería tirar excepcion");
    }

    /*En base a un titular existente que tiene al menos una licencia expirada en el listado y una licencia vigente,
    tratar de renovar alguna licencia expirada de la misma clase que la vigente
    (sin modificar datos del titular). */
    @Test
    void falloLicenciaVigentePorRenovarExpirada () throws NoPuedeRenovarExisteLicencia{
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        LicenciaDTO lic2 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        List<LicenciaDTO> listaprueba = new ArrayList<>();

        
        //Seteo del titular
        titular.setApellido("Gaggiotti");
        titular.setNombre("Pato");
        titular.setDireccion("Velez Sarsfield 999");
        titular.setEsDonante(true);
        titular.setFactorRH(TipoFactorRH.NEGATIVO);
        titular.setFechaNacimiento(LocalDate.of(2003, 03, 04));
        titular.setGrupoSanguineo(TipoGrupoS.ABN);
        titular.setIdTitular((long) 1);
        titular.setNroDoc("44307268");
        titular.setTipoDoc(TipoDoc.DNI);
        titular.setSexo(TipoSexo.MASCULINO);
        //Seteo de la licencia, solamente valores requeridos
        claseA.setClase('A');

        lic1.setTitular(titular);
        lic1.setClaseLic(claseA);
        lic1.setCosto((float) 48.0);
        lic1.setEstado(EstadoLicencia.EXPIRADA);
        lic1.setIdLicencia((long) 1);
        lic1.setInicioVigencia(LocalDate.of(2018, 05, 01));
        lic1.setFinVigencia(LocalDate.of(2022, 05, 01));
        lic1.setObservaciones("Ejemplo");
        
        lic2.setTitular(titular);
        lic2.setClaseLic(claseA);
        lic2.setCosto((float) 48.0);
        lic2.setEstado(EstadoLicencia.VIGENTE);
        lic2.setIdLicencia((long) 2);
        lic2.setInicioVigencia(LocalDate.of(2022, 05, 01));
        lic2.setFinVigencia(LocalDate.of(2026, 05, 01));
        lic2.setObservaciones("Ejemplo2");
        listaprueba.add(lic1);
        listaprueba.add(lic2);

        assertThrows(NoPuedeRenovarExisteLicencia.class, () -> gestor.puedeSerRenovada(lic1, listaprueba));    
    }

    /*En base a un titular existente que tiene al menos una licencia expirada en el listado y una licencia vigente,
    tratar de renovar alguna licencia expirada de distinta clase que la vigente
    (sin modificar datos del titular).  */
    @Test 
    void exitoLicenciaExpiradaDistintaClase(){
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        LicenciaDTO lic2 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        ClaseLicenciaDTO claseG = new ClaseLicenciaDTO();        
        List<LicenciaDTO> listaprueba = new ArrayList<>();

        
        //Seteo del titular
        titular.setApellido("Gaggiotti");
        titular.setNombre("Pato");
        titular.setDireccion("Velez Sarsfield 999");
        titular.setEsDonante(true);
        titular.setFactorRH(TipoFactorRH.NEGATIVO);
        titular.setFechaNacimiento(LocalDate.of(2003, 03, 04));
        titular.setGrupoSanguineo(TipoGrupoS.ABN);
        titular.setIdTitular((long) 1);
        titular.setNroDoc("44307268");
        titular.setTipoDoc(TipoDoc.DNI);
        titular.setSexo(TipoSexo.MASCULINO);
        //Seteo de la licencia, solamente valores requeridos
        claseA.setClase('A');
        claseG.setClase('G');

        lic1.setTitular(titular);
        lic1.setClaseLic(claseG);
        lic1.setCosto((float) 48.0);
        lic1.setEstado(EstadoLicencia.EXPIRADA);
        lic1.setIdLicencia((long) 1);
        lic1.setInicioVigencia(LocalDate.of(2018, 05, 01));
        lic1.setFinVigencia(LocalDate.of(2022, 05, 01));
        lic1.setObservaciones("Ejemplo");
        
        lic2.setTitular(titular);
        lic2.setClaseLic(claseA);
        lic2.setCosto((float) 48.0);
        lic2.setEstado(EstadoLicencia.VIGENTE);
        lic2.setIdLicencia((long) 2);
        lic2.setInicioVigencia(LocalDate.of(2022, 05, 01));
        lic2.setFinVigencia(LocalDate.of(2026, 05, 01));
        lic2.setObservaciones("Ejemplo2");
        listaprueba.add(lic1);
        listaprueba.add(lic2);

        assertDoesNotThrow(() -> gestor.puedeSerRenovada(lic1, listaprueba),"No debería dar ninguna excepción");    
    }

    /*En base a un titular existente que tiene al menos una licencia expirada en el listado y una licencia vigente
    con fecha de vencimiento a más de tres meses de la fecha actual, tratar de renovar la licencia vigente
    (sin modificar datos del titular). */
    @Test
    void falloRenovacionTemprana() throws NoPuedeRenovarVigenciaTemprana{
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        LicenciaDTO lic2 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        List<LicenciaDTO> listaprueba = new ArrayList<>();

        
        //Seteo del titular
        titular.setApellido("Gaggiotti");
        titular.setNombre("Pato");
        titular.setDireccion("Velez Sarsfield 999");
        titular.setEsDonante(true);
        titular.setFactorRH(TipoFactorRH.NEGATIVO);
        titular.setFechaNacimiento(LocalDate.of(2003, 03, 04));
        titular.setGrupoSanguineo(TipoGrupoS.ABN);
        titular.setIdTitular((long) 1);
        titular.setNroDoc("44307268");
        titular.setTipoDoc(TipoDoc.DNI);
        titular.setSexo(TipoSexo.MASCULINO);
        //Seteo de la licencia, solamente valores requeridos
        claseA.setClase('A');

        lic1.setTitular(titular);
        lic1.setClaseLic(claseA);
        lic1.setCosto((float) 48.0);
        lic1.setEstado(EstadoLicencia.EXPIRADA);
        lic1.setIdLicencia((long) 1);
        lic1.setInicioVigencia(LocalDate.of(2018, 05, 01));
        lic1.setFinVigencia(LocalDate.of(2022, 05, 01));
        lic1.setObservaciones("Ejemplo");
        
        lic2.setTitular(titular);
        lic2.setClaseLic(claseA);
        lic2.setCosto((float) 48.0);
        lic2.setEstado(EstadoLicencia.VIGENTE);
        lic2.setIdLicencia((long) 2);
        lic2.setInicioVigencia(LocalDate.of(2022, 05, 01));
        lic2.setFinVigencia(LocalDate.of(2026, 05, 01));
        lic2.setObservaciones("Ejemplo2");
        listaprueba.add(lic1);
        listaprueba.add(lic2);

        assertThrows(NoPuedeRenovarVigenciaTemprana.class, () -> gestor.puedeSerRenovada(lic2, listaprueba));    
    }

    /*En base a un titular existente que tiene al menos una licencia expirada en el listado y una licencia vigente con 
    fecha de vencimiento a menos de tres meses de la fecha actual, tratar de renovar la licencia vigente
    (sin modificar datos del titular). */
    @Test
    void exitoRenovacionTemprana(){
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        LicenciaDTO lic2 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        List<LicenciaDTO> listaprueba = new ArrayList<>();

        
        //Seteo del titular
        titular.setApellido("Gaggiotti");
        titular.setNombre("Pato");
        titular.setDireccion("Velez Sarsfield 999");
        titular.setEsDonante(true);
        titular.setFactorRH(TipoFactorRH.NEGATIVO);
        titular.setFechaNacimiento(LocalDate.of(2003, 03, 04));
        titular.setGrupoSanguineo(TipoGrupoS.ABN);
        titular.setIdTitular((long) 1);
        titular.setNroDoc("44307268");
        titular.setTipoDoc(TipoDoc.DNI);
        titular.setSexo(TipoSexo.MASCULINO);
        //Seteo de la licencia, solamente valores requeridos
        claseA.setClase('A');

        lic1.setTitular(titular);
        lic1.setClaseLic(claseA);
        lic1.setCosto((float) 48.0);
        lic1.setEstado(EstadoLicencia.EXPIRADA);
        lic1.setIdLicencia((long) 1);
        lic1.setInicioVigencia(LocalDate.of(2018, 05, 01));
        lic1.setFinVigencia(LocalDate.of(2022, 05, 01));
        lic1.setObservaciones("Ejemplo");
        
        lic2.setTitular(titular);
        lic2.setClaseLic(claseA);
        lic2.setCosto((float) 48.0);
        lic2.setEstado(EstadoLicencia.VIGENTE);
        lic2.setIdLicencia((long) 2);
        lic2.setInicioVigencia(LocalDate.of(2022, 05, 01));
        lic2.setFinVigencia(LocalDate.of(2024, 06, 30));
        lic2.setObservaciones("Ejemplo2");
        listaprueba.add(lic1);
        listaprueba.add(lic2);

        assertDoesNotThrow(() -> gestor.puedeSerRenovada(lic2, listaprueba),"No debería arrojar excepción"); 
    }

    /*En base a un titular existente tratar de renovar una licencia válida para la
     renovación modificando solamente un campo del titular 
     (ya sea el nombre, apellido o número de documento).  */
    @Test
    void exitoModificacionTitular(){
        TitularDTO titular = new TitularDTO();    
        //Seteo del titular
        titular.setApellido("Bussi");
        titular.setNombre("Francesco");
        titular.setDireccion("Gdor Aldao 999");
        titular.setEsDonante(true);
        titular.setFactorRH(TipoFactorRH.NEGATIVO);
        titular.setFechaNacimiento(LocalDate.of(2000, 9, 10));
        titular.setGrupoSanguineo(TipoGrupoS.ABN);
        titular.setIdTitular((long) 1);
        titular.setNroDoc("42926900");
        titular.setTipoDoc(TipoDoc.DNI);
        titular.setSexo(TipoSexo.MASCULINO);

        Titular titularBD = new Titular();
        //Seteo del titular
        titularBD.setApellido("Gaggiotti");
        titularBD.setNombre("Pato");
        titularBD.setDireccion("Velez Sarsfield 999");
        titularBD.setEsDonante(true);
        titularBD.setFactorRH(TipoFactorRH.NEGATIVO);
        titularBD.setFechaNacimiento(LocalDate.of(2003, 03, 04));
        titularBD.setGrupoSanguineo(TipoGrupoS.ABN);
        titularBD.setIdTitular((long) 1);
        titularBD.setNroDoc("44307268");
        titularBD.setTipoDoc(TipoDoc.DNI);
        titularBD.setSexo(TipoSexo.MASCULINO);


        try{
            Mockito.when(titularDAO.getById(anyLong())).thenReturn(Optional.of(titularBD));
            Mockito.doNothing().when(titularDAO).updateInstance(any(Titular.class));
            injectMocks(gestorTitular, "titularDao", titularDAO);
        }
        catch(Exception e){
            System.out.println("Error inyectando dependencias en testing");
        }

        assertDoesNotThrow(() -> {gestorTitular.actualizarTitular(titular);}, "No debería arrojar excepcion");
        verify(titularDAO).getById(anyLong());
        verify(titularDAO).updateInstance(any(Titular.class));
        assertEquals("Francesco", titularBD.getNombre());
        assertEquals("Bussi", titularBD.getApellido());
        assertEquals("42926900", titularBD.getNroDoc());
    }


}
