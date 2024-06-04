package isi.agiles.logica;

import java.util.List;

import isi.agiles.dao.ClaseLicenciaDAO;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.entidad.ClaseLicencia;
import isi.agiles.excepcion.ObjetoNoEncontradoException;

public class GestorClaseLicencia {

    public static List<ClaseLicenciaDTO> getAllDTOs()
    throws ObjetoNoEncontradoException{
        List<ClaseLicencia> clases = getAll();
        return clases.stream().map(c -> getClaseLicenciaDTO(c)).toList();
    }

    public static List<ClaseLicencia> getAll()
    throws ObjetoNoEncontradoException{
        ClaseLicenciaDAO dao = new ClaseLicenciaDAO();
        List<ClaseLicencia> clases = dao.getAll();
        if(clases.isEmpty()){
            throw new ObjetoNoEncontradoException();
        }
        return clases;
    }

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
