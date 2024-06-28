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
import isi.agiles.excepcion.NoPuedeRenovarExisteLicencia;
import isi.agiles.excepcion.NoPuedeRenovarVigenciaTemprana;
import isi.agiles.excepcion.ObjetoNoEncontradoException;
import isi.agiles.logica.GestorLicencia;
import isi.agiles.logica.GestorTitular;
import isi.agiles.util.EntityManagerUtil;



public class RenovarLicenciaTest {
    @BeforeEach
    void setup(){
        EntityManagerUtil.createEntityManagerFactory();
    }
    @Test
    void falloNoExisteTitular() throws ObjetoNoEncontradoException{
        //Se prepara el mock con los valores deseados de retorno
        GestorTitular gestor = Mockito.mock(GestorTitular.class);
        Mockito.when(gestor.getTitularByDocumento("00000000",TipoDoc.DNI)).thenThrow(new ObjetoNoEncontradoException());
        //Se verifica que se arroje la excepcion cuando se ejecuta el metodo
        assertThrows(ObjetoNoEncontradoException.class, 
                    () ->{gestor.getTitularByDocumento("00000000", TipoDoc.DNI);});
    }

    @Test
    void exitoListaExpiradas() throws NoPuedeRenovarVigenciaTemprana, NoPuedeRenovarExisteLicencia{
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        List<LicenciaDTO> listaprueba = new ArrayList<>();
        GestorLicencia gestor = new GestorLicencia();
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

    @Test
    void falloLicenciaVigentePorRenovarExpirada () throws NoPuedeRenovarExisteLicencia{
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        LicenciaDTO lic2 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        List<LicenciaDTO> listaprueba = new ArrayList<>();
        GestorLicencia gestor = new GestorLicencia();
        
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

    @Test 
    void exitoLicenciaExpiradaDistintaClase(){
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        LicenciaDTO lic2 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        ClaseLicenciaDTO claseG = new ClaseLicenciaDTO();        
        List<LicenciaDTO> listaprueba = new ArrayList<>();
        GestorLicencia gestor = new GestorLicencia();
        
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

    @Test
    void falloRenovacionTemprana() throws NoPuedeRenovarVigenciaTemprana{
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        LicenciaDTO lic2 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        List<LicenciaDTO> listaprueba = new ArrayList<>();
        GestorLicencia gestor = new GestorLicencia();
        
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

    @Test
    void exitoRenovacionTemprana(){
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        LicenciaDTO lic2 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        List<LicenciaDTO> listaprueba = new ArrayList<>();
        GestorLicencia gestor = new GestorLicencia();
        
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

}
