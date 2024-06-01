package isi.agiles.logica;

import java.time.LocalDate;
import java.time.Period;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.ClaseLicencia;
import isi.agiles.entidad.Titular;

public class GestorTitular {

    private Integer getEdad(LocalDate fechaNacimiento){
        LocalDate hoy = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, hoy);
        return edad.getYears();
    }

    public Integer getEdadTitular(TitularDTO dto){
        return this.getEdad(dto.getFechaNacimiento());
    }

    /*public Integer getEdadTitular(Titular titular){
        return this.getEdad(titular.getFechaNacimiento());
    }

    public Boolean tieneEdadParaLicenciaProf(Titular titular){
        //declara LicenciaDAO

        List<Licencia> licenciasProf =  licenciaDao.getLicenciasProfesionales(titular);

        return !licenciasProf.isEmpty() || this.getEdadTitular(titular) <= ClaseLicencia.getEdadMaxPrimeraLicenciaProf();
    }

    public Boolean tieneLicenciasParaLicenciaProf(Titular titular){
        //declara LicenciaDAO

        List<Licencia> licenciasClaseB =  licenciaDao.getLicenciasClaseB(titular);
    }*/
}
