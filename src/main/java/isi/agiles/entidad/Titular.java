package isi.agiles.entidad;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//@Entity
public class Titular {
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_titular")*/
    private Long idTitular;

    //@Column(name="nro_doc", nullable = false)
    private String nroDoc;
    
    /*@Column(name="tipo_doc")
    @Enumerated(EnumType.STRING)*/
    private TipoDoc tipoDoc;
   
    //@Column(nullable = false)
    private String nombre;

    //@Column(nullable = false)
    private String apellido;

    //@Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    //@Column(nullable = false)
    private String direccion;

    //@Column(name = "clases_solicitadas") //nullable false? deberia aunq sea tener 1 clase solicitada para darlo de alta?
    private ArrayList<TipoClasesLicencia> claseSol;

    /*@Column(name = "grupo_sanguineo", nullable = false)
    @Enumerated(EnumType.STRING)*/
    private TipoGrupoS grupoSanguineo;

    /*@Column(name = "factor_rh", nullable = false)
    @Enumerated(EnumType.STRING)*/
    private TipoFactorRH factorRH;

    //@Column(name = "es_donante")
    private Boolean esDonante;

    //@Column(name = "sexo");
    private TipoSexo sexo;

    public TipoSexo getSexo() {
        return sexo;
    }
    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }
    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }
    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }
    public String getNroDoc() {
        return nroDoc;
    }
    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public ArrayList<TipoClasesLicencia> getClaseSol() {
        return claseSol;
    }
    public void setClaseSol(ArrayList<TipoClasesLicencia> claseSol) {
        this.claseSol = claseSol;
    }
    public TipoGrupoS getGrupoSanguineo() {
        return grupoSanguineo;
    }
    public void setGrupoSanguineo(TipoGrupoS grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }
    public TipoFactorRH getFactorRH() {
        return factorRH;
    }
    public void setFactorRH(TipoFactorRH factorRH) {
        this.factorRH = factorRH;
    }
    public Boolean getEsDonante() {
        return esDonante;
    }
    public void setEsDonante(Boolean esDonante) {
        this.esDonante = esDonante;
    }

}
