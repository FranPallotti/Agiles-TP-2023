package isi.agiles.logica;

import isi.agiles.dao.ClaseLicenciaDAO;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.entidad.ClaseLicencia;
import isi.agiles.excepcion.ObjetoNoEncontradoException;

public class GestorClaseLicencia {

    public static ClaseLicenciaDTO getClaseLicenciaDTO(ClaseLicencia claseLic){
        ClaseLicenciaDTO dto = new ClaseLicenciaDTO();

        dto.setClase(claseLic.getClase());
        dto.setEdadMinima(claseLic.getEdadMinima());
        dto.setEsProfesional(claseLic.getEsProfesional());
        
        return dto;
    }

    public static ClaseLicencia getClaseLicencia(ClaseLicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        ClaseLicenciaDAO dao = new ClaseLicenciaDAO();
        ClaseLicencia claseLic = dao.getById(dto.getClase()).orElseThrow(() -> new ObjetoNoEncontradoException());
        return claseLic;
    }

    public static Boolean esClase(ClaseLicencia claseLic, Character clase){
        if(claseLic.getClase().equals(clase)){
            return true;
        }else{
            return claseLic.getIncluye().stream().anyMatch(c -> esClase(c,clase));
        }
    }
}
