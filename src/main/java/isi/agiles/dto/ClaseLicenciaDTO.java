package isi.agiles.dto;

public class ClaseLicenciaDTO {
    private Character clase;
    private Integer edadMinima;
    private Boolean esProfesional;

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
    public Boolean getEsProfesional() {
        return esProfesional;
    }
    public void setEsProfesional(Boolean esProfesional) {
        this.esProfesional = esProfesional;
    }
    @Override
    public String toString() {
        
        return Character.toString(this.clase);
    }
    
}
