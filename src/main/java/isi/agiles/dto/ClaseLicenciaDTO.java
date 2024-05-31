package isi.agiles.dto;

import java.util.List;

public class ClaseLicenciaDTO {
    private Character clase;
    private Integer edadMinima;
    private Integer esProfesional;
    private List<Character> incluye;

    public Character getClase() {
        return clase;
    }
    public void setClase(Character clase) {
        this.clase = clase;
    }
    public Integer getEdadMinima() {
        return edadMinima;
    }
    public void setEdadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
    }
    public Integer getEsProfesional() {
        return esProfesional;
    }
    public void setEsProfesional(Integer esProfesional) {
        this.esProfesional = esProfesional;
    }
    public List<Character> getIncluye() {
        return incluye;
    }
    public void setIncluye(List<Character> incluye) {
        this.incluye = incluye;
    }
    
}
