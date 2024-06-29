package isi.agiles.logica;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import isi.agiles.App;
import isi.agiles.dao.LicenciaDAO;
import isi.agiles.dto.*;
import isi.agiles.entidad.*;
import isi.agiles.excepcion.NoCumpleCondicionesLicenciaException;
import isi.agiles.excepcion.NoPuedeEmitirExisteLicenciaException;
import isi.agiles.excepcion.NoPuedeRenovarExisteLicencia;
import isi.agiles.excepcion.NoPuedeRenovarVigenciaTemprana;
import isi.agiles.excepcion.ObjetoNoEncontradoException;

public class GestorLicencia {
    
    private LicenciaDAO licenciaDao = new LicenciaDAO();
    private GestorTitular gestorTitular = new GestorTitular();
    private GestorClaseLicencia gestorClaseLic = new GestorClaseLicencia();
    private GestorUsuario gestorUsuario = new GestorUsuario();

    public Licencia getLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        Licencia licencia = licenciaDao.getById(dto.getIdLicencia()).orElseThrow(()-> new ObjetoNoEncontradoException());
        return licencia;
    }

    public LicenciaDTO getLicenciaDTO(Licencia licencia){
        LicenciaDTO dto = new LicenciaDTO();
        dto.setIdLicencia(licencia.getIdLicencia());
        dto.setClaseLic(gestorClaseLic.getClaseLicenciaDTO(licencia.getClaseLicencia()));
        dto.setCosto(licencia.getCosto());
        dto.setEstado(licencia.getEstado());
        dto.setFinVigencia(licencia.getFinVigencia());
        dto.setInicioVigencia(licencia.getInicioVigencia());
        dto.setObservaciones(licencia.getObservaciones());
        dto.setTitular(gestorTitular.getTitularDTO(licencia.getTitular()));
        dto.setCantCopias(licencia.getCopias().size());
        return dto;
    }

    public Licencia crearLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        Licencia licencia = new Licencia();
        licencia.setObservaciones(dto.getObservaciones());
        licencia.setInicioVigencia(LocalDate.now());
        licencia.setFinVigencia(dto.getFinVigencia());
        licencia.setEstado(EstadoLicencia.VIGENTE);
        licencia.setCosto(dto.getCosto());
        /*Asociaciones */
        licencia.setClaseLicencia(gestorClaseLic.getClaseLicencia(dto.getClaseLic()));
        licencia.setTitular(gestorTitular.getTitular(dto.getTitular()));
        //Setear Usuario con el usuario que este logeado en la sesion
        licencia.setRealizoTramite(gestorUsuario.getUsuario(App.getUsuarioLogueado()));
        return licencia;
    }

    public Float getCostoLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        Float ret;
        ClaseLicencia c = gestorClaseLic.getClaseLicencia(dto.getClaseLic());
        
        Period period = dto.getInicioVigencia().until(dto.getFinVigencia());
        Integer duracionVigencia = Integer.valueOf(period.getYears());
        List<CostoLicencia> lcosto= c.getCostoClase().stream().sorted((t1,t2) -> t1.getDuracion().compareTo(t2.getDuracion())).collect(Collectors.toList());
        List<CostoLicencia> or = lcosto.stream().filter(t -> t.getDuracion().compareTo(duracionVigencia)>0).collect(Collectors.toList());

        if(or.isEmpty()){
            ret=Float.valueOf(lcosto.get(lcosto.size()-1).getCosto()+lcosto.get(lcosto.size()-1).getCostoAdministrativo());
        }
        else{
            ret = Float.valueOf(or.get(0).getCosto()+or.get(0).getCostoAdministrativo());
        }

        return ret;
    }

    public void calcularVigenciaLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        Titular titular = gestorTitular.getTitular(dto.getTitular());
        Integer aniosVigencia = gestorTitular.aniosDeVigenciaLicencia(titular);
        //posibleProxCumpleanios: Es la fecha de nacimiento del titular llevada al año actual.
        LocalDate hoy = LocalDate.now();
        LocalDate posibleProxCumpleanios = titular.getFechaNacimiento().withYear(hoy.getYear());
        LocalDate vigenteHasta;
        if(posibleProxCumpleanios.compareTo(hoy) < 0){
            //Si la fecha de cumpleaños ya pasó para este año (es "menor" que hoy), se cuenta desde el cumpleaños del año que viene
            vigenteHasta = titular.getFechaNacimiento().withYear(hoy.getYear() + aniosVigencia + 1);
        }else{
            //Si la fecha de cumpleaños no pasó todavia para este año (o incluso es hoy), se cuenta desde el cumpleaños de este año
            vigenteHasta = titular.getFechaNacimiento().withYear(hoy.getYear() + aniosVigencia);
        }
        dto.setInicioVigencia(hoy);
        dto.setFinVigencia(vigenteHasta);
    }

    public Licencia altaLicencia(LicenciaDTO dto)
    throws NoCumpleCondicionesLicenciaException, ObjetoNoEncontradoException, NoPuedeEmitirExisteLicenciaException{
        if(!gestorTitular.puedeTenerLicencia(dto.getTitular(),dto.getClaseLic())){
            throw new NoCumpleCondicionesLicenciaException();
        }
        if(gestorTitular.tieneLicenciasVigentes(dto.getTitular(),dto.getClaseLic())){
            throw new NoPuedeEmitirExisteLicenciaException();
        }
        Licencia licencia = this.crearLicencia(dto);
        licenciaDao.saveInstance(licencia);
        return licencia;
    }

    public Licencia renuevaLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        this.marcarRenovada(dto);
        Licencia licencia = this.crearLicencia(dto);
        licenciaDao.saveInstance(licencia);
        return licencia;
    }

    public boolean tieneLicenciasVigentes(TitularDTO titularDto, ClaseLicenciaDTO clase)
    throws ObjetoNoEncontradoException{
        Titular titular = gestorTitular.getTitular(titularDto);
        List<LicenciaDTO> licenciasTitular = titular.getLicencias().stream().
            map(l -> this.getLicenciaDTO(l)).
            toList();
        return muchasLicenciasVigentes(clase.getClase(),licenciasTitular);
    }

    public List<Licencia> getLicenciasExpiradas()
    throws ObjetoNoEncontradoException{
        List<Licencia> listadoExpiradas = licenciaDao.getLicenciasExpiradas();
        if(listadoExpiradas.isEmpty()){
            throw new ObjetoNoEncontradoException();
        }
        return listadoExpiradas;
    }

    public List<LicenciaDTO> getLicenciasExpiradasDTO()
    throws ObjetoNoEncontradoException{
        List<Licencia> listaExpiradas = this.getLicenciasExpiradas();
        return listaExpiradas.stream().map(l -> this.getLicenciaDTO(l)).toList();
    }

    public void marcarRenovada(LicenciaDTO licencia) throws ObjetoNoEncontradoException{
        Licencia aux = licenciaDao.getById(licencia.getIdLicencia()).orElseThrow(()-> new ObjetoNoEncontradoException());
        aux.setEstado(EstadoLicencia.EXPIRADA);
        licenciaDao.updateInstance(aux);
    }
    public void emitirCopia(LicenciaDTO dto)throws ObjetoNoEncontradoException{
        Licencia l=this.getLicencia(dto);
        CopiaLicencia copia = new CopiaLicencia();

        copia.setEmisor(gestorUsuario.getUsuario(App.getUsuarioLogueado()));
        copia.setFechaEmision(LocalDate.now());
        copia.setLicencia(l);
        l.agregarCopia(copia);
        licenciaDao.saveInstance(l);
    }

    public void puedeSerRenovada(LicenciaDTO licencia, List<LicenciaDTO> licencias)
    throws NoPuedeRenovarVigenciaTemprana, NoPuedeRenovarExisteLicencia{
        Character claselic = licencia.getClaseLic().getClase();
        switch (claselic) {
            case 'G':
                if(licencia.getEstado().equals(EstadoLicencia.EXPIRADA) && licencias.stream()
                                        .filter(lic -> lic.getEstado().equals(EstadoLicencia.VIGENTE)
                                            && (lic.getClaseLic().getClase() == claselic))
                                        .count() >=1 ){
                    throw new NoPuedeRenovarExisteLicencia();
                }
                if(muchasLicenciasVigentes(claselic, licencias)){
                    throw new NoPuedeRenovarExisteLicencia();
                }else if(licencia.getFinVigencia().isAfter(LocalDate.now().plus(3,ChronoUnit.MONTHS))){
                    throw new NoPuedeRenovarVigenciaTemprana();
                }
            break;

            case 'F':
                if(licencia.getEstado().equals(EstadoLicencia.EXPIRADA) && licencias.stream()
                                        .filter(lic -> lic.getEstado().equals(EstadoLicencia.VIGENTE)
                                            && (lic.getClaseLic().getClase() == claselic))
                                        .count() >=1 ){
                    throw new NoPuedeRenovarExisteLicencia();
                }
                if(muchasLicenciasVigentes(claselic, licencias)){
                    throw new NoPuedeRenovarExisteLicencia();
                }else if(licencia.getFinVigencia().isAfter(LocalDate.now().plus(3,ChronoUnit.MONTHS))){
                    throw new NoPuedeRenovarVigenciaTemprana();
                }
            break;

            default:
                if(licencia.getEstado().equals(EstadoLicencia.EXPIRADA) && licencias.stream()
                                        .filter(lic -> lic.getEstado().equals(EstadoLicencia.VIGENTE))
                                        .count() >=1 ){
                    throw new NoPuedeRenovarExisteLicencia();
                }
                if(muchasLicenciasVigentes(claselic, licencias)){
                    throw new NoPuedeRenovarExisteLicencia();
                }else if(licencia.getFinVigencia().isAfter(LocalDate.now().plus(3,ChronoUnit.MONTHS))){
                    throw new NoPuedeRenovarVigenciaTemprana();
                }
                break;
        }    
    }

    private boolean muchasLicenciasVigentes(Character clase, List<LicenciaDTO> licencias) {
        Long contador = licencias.stream()
                        .filter(lic -> lic.getEstado().equals(EstadoLicencia.VIGENTE) 
                            && (lic.getClaseLic().getClase() == clase))
                        .count();
        if(contador>=2)
        return true;
        else
        return false;
    }
}
