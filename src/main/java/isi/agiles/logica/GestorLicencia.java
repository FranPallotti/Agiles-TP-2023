package isi.agiles.logica;

import isi.agiles.dto.*;
import isi.agiles.entidad.*;

public class GestorLicencia {
    
    public Licencia createLicencia(LicenciaDTO dto){
        return null; //(!)
    }

    public Float getCostoLicencia(LicenciaDTO dto){
        return 0F; //(!)
    }

    public void setVigenciaLicencia(LicenciaDTO dto){

    }

    public Boolean esLicenciaProfesionalValida(LicenciaDTO dto){
        if(dto.getClase().getEsProfesional().equals(false)){
            return true;
        }
        
        GestorTitular gestorTitular = new GestorTitular();
        
        Boolean condicionLicencia = gestorTitular.puedeTenerLicenciaProfesional();
        
    }

    public Boolean edadValida(LicenciaDTO dto){
        GestorTitular gestor = new GestorTitular();

        return  gestor.getEdadTitular(dto.getTitular()) >= dto.getClase().getEdadMinima();
    }
}
