import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import isi.agiles.logica.GestorTitular;
import isi.agiles.ui.RenovarLicenciaController;

public class RenovarLicenciaControllerTest {
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
        RenovarLicenciaController controller = Mockito.mock(RenovarLicenciaController.class);
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

        //Testeo
        assertDoesNotThrow( () -> {controller.puedeSerRenovada(lic1);}, "No debería tirar excepcion");
    }

    @Test
    void falloLicenciaVigentePorRenovarExpirada () throws NoPuedeRenovarExisteLicencia{
        TitularDTO titular = new TitularDTO();
        LicenciaDTO lic1 = new LicenciaDTO();
        LicenciaDTO lic2 = new LicenciaDTO();
        ClaseLicenciaDTO claseA = new ClaseLicenciaDTO();
        RenovarLicenciaController controller = Mockito.mock(RenovarLicenciaController.class);
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
        lic2.setIdLicencia((long) 1);
        lic2.setInicioVigencia(LocalDate.of(2022, 05, 01));
        lic2.setFinVigencia(LocalDate.of(2026, 05, 01));
        lic2.setObservaciones("Ejemplo");

        controller.setLicencias(lic1, lic2);
        assertThrows(NoPuedeRenovarExisteLicencia.class, () -> {controller.puedeSerRenovada(lic1);});
    }
}
