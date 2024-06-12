package isi.agiles.entidad;

import java.time.LocalDate;
import java.util.*;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "titular")
public class Titular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_titular")
    private Long idTitular;

    
    @Column(name = "fecha_nacimiento", nullable = true)
    private LocalDate fechaNacimiento;

    @NaturalId
    @Column(name="nro_doc", nullable = false)
    private String nroDoc;
    
    @NaturalId
    @Column(name="tipo_doc", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDoc tipoDoc;
   
    
    @Column(nullable = false)
    private String nombre;

    
    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private TipoSexo sexo;
    
    @Column(nullable = true)
    private String direccion;

    @Column(name = "clases_solicitadas") //nullable false? deberia aunq sea tener 1 clase solicitada para darlo de alta?
    private List<Character> claseSol;

    @Column(name = "grupo_sanguineo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoGrupoS grupoSanguineo;
    
    @Column(name = "factor_rh", nullable = true)
    @Enumerated(EnumType.STRING)
    private TipoFactorRH factorRH;

    @Column(name = "es_donante", nullable = false)
    private Boolean esDonante;

    /*Asocaciones */
    @OneToMany(fetch = FetchType.LAZY,
               cascade = {CascadeType.REMOVE},
               mappedBy = "titular")
    private List<Licencia> licencias;

    /*Getters y setters */
    public Long getIdTitular() {
        return idTitular;
    }

    public void setIdTitular(Long idTitular) {
        this.idTitular = idTitular;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Character> getClaseSol() {
        return claseSol;
    }

    public void setClaseSol(List<Character> claseSol) {
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

    public List<Licencia> getLicencias() {
        return licencias;
    }

    public void setLicencias(List<Licencia> licencias) {
        this.licencias = licencias;
    }


    public TipoSexo getSexo() {
        return sexo;
    }


    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
    }
    
}