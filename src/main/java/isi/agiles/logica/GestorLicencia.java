package isi.agiles.logica;

import java.time.LocalDate;

import isi.agiles.dao.ClaseLicenciaDAO;
import isi.agiles.dao.LicenciaDAO;
import isi.agiles.dao.TitularDAO;
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

        return licencia;
    }

    public static Float getCostoLicencia(LicenciaDTO dto){
        return 0F; //(!)
    }

    public static void calcularVigenciaLicencia(LicenciaDTO dto){

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
