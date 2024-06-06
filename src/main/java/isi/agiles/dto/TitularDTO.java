package isi.agiles.dto;

import java.time.LocalDate;
import java.util.ArrayList;

import isi.agiles.entidad.TipoClasesLicencia;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.TipoFactorRH;
import isi.agiles.entidad.TipoGrupoS;
import isi.agiles.entidad.TipoSexo;

public class TitularDTO {
    private Long idTitular;
    private String nombre;
    private String apellido;
    private String nroDoc;
    private LocalDate fechaNacimiento;
    private TipoDoc tipoDoc;
    private String direccion;
    private ArrayList<TipoClasesLicencia> claseSol;
    private TipoGrupoS grupoSanguineo;
    private TipoFactorRH factorRH;
    private Boolean esDonante;
    private TipoSexo sexo;
    
    public TipoSexo getSexo() {
        return sexo;
    }
    public void setSexo(TipoSexo sexo) {
        this.sexo = sexo;
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
    public Long getIdTitular() {
        return idTitular;
    }
    public void setIdTitular(Long idTitular) {
        this.idTitular = idTitular;
    }

    
}
