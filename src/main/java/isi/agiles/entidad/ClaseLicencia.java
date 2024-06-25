package isi.agiles.entidad;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/* (!!) Ver como agregar el dato de "edad maxima para pedir por primera vez"
 * sin que este en el codigo, que se pida de la base de datos.
 * (El problema es que es una constante global)
*/

@Entity
@Table(name = "clase_licencia")
public class ClaseLicencia {

    @Id
    @Column(name = "clase")
    private Character clase;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "es_profesional", nullable = false)
    private Boolean esProfesional;

    @Column(name = "edad_minima", nullable = false)
    private Integer edadMinima;

    
    
    private static final Integer EDAD_MAX_PRIMERA_LICENCIA_PROF = 65; //(!)

    /*Asociaciones */

    @ManyToMany(fetch = FetchType.LAZY)
    private List<ClaseLicencia> incluye = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER,
               cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "clase")
    private List<CostoLicencia> costoClase;

    public static Integer getEdadMaxPrimeraLicenciaProf() {
        return EDAD_MAX_PRIMERA_LICENCIA_PROF;
    }

    public Character getClase() {
        return clase;
    }

    public void setClase(Character clase) {
        this.clase = clase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEsProfesional() {
        return esProfesional;
    }

    public void setEsProfesional(Boolean esProfesional) {
        this.esProfesional = esProfesional;
    }

    public Integer getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
    }

    public List<ClaseLicencia> getIncluye() {
        return incluye;
    }

    public void setIncluye(List<ClaseLicencia> incluye) {
        this.incluye = incluye;
    }
    public List<CostoLicencia> getCostoClase() {
        return costoClase;
    }
    public void setCostoClase(List<CostoLicencia> costoClase) {
        this.costoClase = costoClase;
    }

}
