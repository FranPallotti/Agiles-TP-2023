package isi.agiles.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import isi.agiles.dao.ClaseLicenciaDAO;
import isi.agiles.dto.ClaseLicenciaDTO;
import isi.agiles.entidad.ClaseLicencia;
import isi.agiles.excepcion.ObjetoNoEncontradoException;

public class GestorClaseLicencia {

    @Autowired
    private ClaseLicenciaDAO claseLicDao;

    public List<ClaseLicenciaDTO> getAllDTOs()
    throws ObjetoNoEncontradoException{
        List<ClaseLicencia> clases = getAll();
        return clases.stream().map(c -> this.getClaseLicenciaDTO(c)).toList();
    }

    public List<ClaseLicencia> getAll()
    throws ObjetoNoEncontradoException{
        List<ClaseLicencia> clases = claseLicDao.getAll();
        if(clases.isEmpty()){
            throw new ObjetoNoEncontradoException();
        }
        return clases;
    }

    public ClaseLicenciaDTO getClaseLicenciaDTO(ClaseLicencia claseLic){
        ClaseLicenciaDTO dto = new ClaseLicenciaDTO();
        dto.setClase(claseLic.getClase());
        dto.setEdadMinima(claseLic.getEdadMinima());
        dto.setEsProfesional(claseLic.getEsProfesional());
        return dto;
    }

    public ClaseLicencia getClaseLicencia(ClaseLicenciaDTO dto)
    throws ObjetoNoEncontradoException{
        ClaseLicencia claseLic = claseLicDao.getById(dto.getClase()).orElseThrow(() -> new ObjetoNoEncontradoException());
        return claseLic;
    }

    public Boolean esClase(ClaseLicencia claseLic, Character clase){
        if(claseLic.getClase().equals(clase)){
            return true;
        }else{
            //Si la lista "incluye" esta vacia, retorna false.
            return claseLic.getIncluye().stream().anyMatch(c -> this.esClase(c,clase));
        }
    }
}
