package isi.agiles.logica;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import isi.agiles.App;
import isi.agiles.dao.LicenciaDAO;
import isi.agiles.dto.*;
import isi.agiles.entidad.*;
import isi.agiles.excepcion.NoCumpleCondicionesLicenciaException;
import isi.agiles.excepcion.ObjetoNoEncontradoException;

public class GestorLicencia {
    
    public static Licencia crearLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        Licencia licencia = new Licencia();

        licencia.setObservaciones(dto.getObservaciones());
        licencia.setInicioVigencia(LocalDate.now());
        licencia.setFinVigencia(dto.getFinVigencia());
        licencia.setEstado(EstadoLicencia.VIGENTE);
        licencia.setCantDeCopias(0);
        licencia.setCosto(dto.getCosto());

        /*Asociaciones */
        licencia.setClaseLicencia(GestorClaseLicencia.getClaseLicencia(dto.getClase()));
        licencia.setTitular(GestorTitular.getTitular(dto.getTitular()));

        //TODO: Setear Usuario con el usuario que este logeado en la sesion
        licencia.setRealizoTramite(GestorUsuario.getUsuario(App.getUsuarioLogueado()));
        return licencia;
    }

    public static Float getCostoLicencia(LicenciaDTO dto)throws ObjetoNoEncontradoException{
        Float ret;
        ClaseLicencia c = GestorClaseLicencia.getClaseLicencia(dto.getClase());
        
        Period period = dto.getInicioVigencia().until(dto.getFinVigencia());
        Integer duracionVigencia = Integer.valueOf(period.getYears());
        List<CostoLicencia> lcosto= c.getCostoClase().stream().sorted((t1,t2) -> t1.getDuracion().compareTo(t2.getDuracion())).collect(Collectors.toList());
        List<CostoLicencia> or = lcosto.stream().filter(t -> t.getDuracion().compareTo(duracionVigencia)>0).collect(Collectors.toList());
        if(or.isEmpty()){
            ret=Float.valueOf(lcosto.get(lcosto.size()-1).getCosto()+Float.parseFloat("8.0"));
        }
        else{
            
            ret = Float.valueOf(or.get(0).getCosto()+Float.parseFloat("8.0"));
            
        }


        return ret; //(!)
    }

    public static void calcularVigenciaLicencia(LicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        Titular titular = GestorTitular.getTitular(dto.getTitular());
        Integer edadTitular = GestorTitular.getEdadTitular(titular);
        Integer aniosVigencia;

        if(edadTitular <= 20 /*Menores de 21*/){
            if(titular.getLicencias().isEmpty()){
                aniosVigencia = 1;
            }else{
                aniosVigencia = 3;
            }
        }else if(edadTitular <= 46){
            aniosVigencia = 5;
        }else if (edadTitular <= 60) {
            aniosVigencia = 4;
        }else if (edadTitular <= 70) {
            aniosVigencia = 3;
        }else{
            aniosVigencia = 1;
        }
        
        //posibleProxCumpleanios: Es la fecha de nacimiento del titular llevada al año actual.
        LocalDate posibleProxCumpleanios = titular.getFechaNacimiento().withYear(LocalDate.now().getYear());
        LocalDate vigenteHasta;

        if(posibleProxCumpleanios.compareTo(LocalDate.now()) > 0){
            //Si la fecha de cumpleaños ya pasó para este año, se cuenta desde el cumpleaños del año que viene
            vigenteHasta = titular.getFechaNacimiento().withYear(LocalDate.now().getYear() + aniosVigencia + 1);
        }else{
            //Si la fecha de cumpleaños no pasó todavia para este año (o incluso es hoy), se cuenta desde el cumpleaños de este año
            vigenteHasta = titular.getFechaNacimiento().withYear(LocalDate.now().getYear() + aniosVigencia);
        }

        dto.setInicioVigencia(LocalDate.now());
        dto.setFinVigencia(vigenteHasta);
    }

    public static Licencia altaLicencia(LicenciaDTO dto)
    throws NoCumpleCondicionesLicenciaException, ObjetoNoEncontradoException{
        if(!GestorTitular.puedeTenerLicencia(dto.getTitular(),dto.getClase())){
            throw new NoCumpleCondicionesLicenciaException();
        }

        Licencia licencia = crearLicencia(dto);
        LicenciaDAO dao = new LicenciaDAO();
        dao.saveInstance(licencia);

        return licencia;
    }
}
