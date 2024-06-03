package isi.agiles.entidad;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.Table;

@Table
public class Titular {
    
    private TipoDoc tipoDoc;
    private String nroDoc;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String direccion;
    private ArrayList<String> claseSol;
    private TipoGrupoS grupoSanguineo;
    private TipoFactorRH factorRH;
    private Boolean esDonante;

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
    public ArrayList<String> getClaseSol() {
        return claseSol;
    }
    public void setClaseSol(ArrayList<String> claseSol) {
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
