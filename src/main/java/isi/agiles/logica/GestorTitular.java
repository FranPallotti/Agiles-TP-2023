package isi.agiles.logica;

import java.time.LocalDate;
import java.time.Period;

import isi.agiles.dao.TitularDAO;
import isi.agiles.dto.TitularDTO;
import isi.agiles.entidad.ClaseLicencia;
import isi.agiles.entidad.Titular;
import jakarta.persistence.EntityManager;

public class GestorTitular {

    private TitularDAO titularDao;

    public GestorTitular(EntityManager entityManager){
        titularDao = new TitularDAO();
    }

    private Integer getEdad(LocalDate fechaNacimiento){
        LocalDate hoy = LocalDate.now();
        Period edad = Period.between(fechaNacimiento, hoy);
        return edad.getYears();
    }

    public Integer getEdadTitular(TitularDTO dto){
        return this.getEdad(dto.getFechaNacimiento());
    }

    public void persistir(TitularDTO titular) {
        Titular entidad = new Titular();
        entidad.setApellido(titular.getApellido());
        entidad.setClaseSol(titular.getClaseSol());
        entidad.setDireccion(titular.getDireccion());
        entidad.setEsDonante(titular.getEsDonante());
        entidad.setFactorRH(titular.getFactorRH());
        entidad.setFechaNacimiento(titular.getFechaNacimiento());
        entidad.setSexo(titular.getSexo());
        entidad.setGrupoSanguineo(titular.getGrupoSanguineo());
        entidad.setNombre(titular.getNombre());
        entidad.setNroDoc(titular.getDocumento());
        entidad.setTipoDoc(titular.getTipoDoc());
        titularDao.saveInstance(entidad);
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
