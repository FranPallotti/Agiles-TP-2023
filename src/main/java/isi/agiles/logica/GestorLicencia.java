package isi.agiles.logica;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import isi.agiles.dao.LicenciaDAO;
import isi.agiles.dto.*;
import isi.agiles.entidad.*;
import isi.agiles.excepcion.NoCumpleCondicionesLicenciaException;
import isi.agiles.excepcion.ObjetoNoEncontradoException;

public class GestorLicencia {
    
    @Autowired
    private LicenciaDAO licenciaDao;
    @Autowired
    private GestorTitular gestorTitular;
    @Autowired
    private GestorClaseLicencia gestorClaseLic;

    public Licencia getLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        Licencia licencia = licenciaDao.getById(dto.getIdLicencia()).orElseThrow(()-> new ObjetoNoEncontradoException());
        return licencia;
    }

    public LicenciaDTO getLicenciaDTO(Licencia licencia){
        LicenciaDTO dto = new LicenciaDTO();
        dto.setIdLicencia(licencia.getIdLicencia());
        dto.setClase(gestorClaseLic.getClaseLicenciaDTO(licencia.getClaseLicencia()));
        dto.setCosto(licencia.getCosto());
        dto.setEstado(licencia.getEstado());
        dto.setFinVigencia(licencia.getFinVigencia());
        dto.setInicioVigencia(licencia.getInicioVigencia());
        dto.setObservaciones(licencia.getObservaciones());
        dto.setTitular(gestorTitular.getTitularDTO(licencia.getTitular()));
        return dto;
    }

    public Licencia crearLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        Licencia licencia = new Licencia();
        licencia.setObservaciones(dto.getObservaciones());
        licencia.setInicioVigencia(LocalDate.now());
        licencia.setFinVigencia(dto.getFinVigencia());
        licencia.setEstado(EstadoLicencia.VIGENTE);
        licencia.setCantDeCopias(0);
        licencia.setCosto(dto.getCosto());
        /*Asociaciones */
        licencia.setClaseLicencia(gestorClaseLic.getClaseLicencia(dto.getClase()));
        licencia.setTitular(gestorTitular.getTitular(dto.getTitular()));
        //TODO: Setear Usuario con el usuario que este logeado en la sesion
        return licencia;
    }

    public Float getCostoLicencia(LicenciaDTO dto){
        //TODO: Lo hace Fran.
        return 0F; //(!)
    }

    public void calcularVigenciaLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        Titular titular = gestorTitular.getTitular(dto.getTitular());
        Integer aniosVigencia = gestorTitular.aniosDeVigenciaLicencia(titular);
        //posibleProxCumpleanios: Es la fecha de nacimiento del titular llevada al año actual.
        LocalDate hoy = LocalDate.now();
        LocalDate posibleProxCumpleanios = titular.getFechaNacimiento().withYear(hoy.getYear());
        LocalDate vigenteHasta;
        if(posibleProxCumpleanios.compareTo(hoy) > 0){
            //Si la fecha de cumpleaños ya pasó para este año, se cuenta desde el cumpleaños del año que viene
            vigenteHasta = titular.getFechaNacimiento().withYear(hoy.getYear() + aniosVigencia + 1);
        }else{
            //Si la fecha de cumpleaños no pasó todavia para este año (o incluso es hoy), se cuenta desde el cumpleaños de este año
            vigenteHasta = titular.getFechaNacimiento().withYear(hoy.getYear() + aniosVigencia);
        }
        dto.setInicioVigencia(LocalDate.now());
        dto.setFinVigencia(vigenteHasta);
    }

    public Licencia altaLicencia(LicenciaDTO dto)
    throws NoCumpleCondicionesLicenciaException, ObjetoNoEncontradoException{
        if(!gestorTitular.puedeTenerLicencia(dto.getTitular(),dto.getClase())){
            throw new NoCumpleCondicionesLicenciaException();
        }
        Licencia licencia = this.crearLicencia(dto);
        licenciaDao.saveInstance(licencia);
        return licencia;
    }
}
