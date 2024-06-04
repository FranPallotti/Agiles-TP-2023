package isi.agiles.dto;

import java.time.LocalDate;

import isi.agiles.entidad.TipoDoc;

public class TitularDTO {
    private Long idTitular;
    private String nombre;
    private String apellido;
    private String nroDoc;
    private TipoDoc tipoDoc;
    private LocalDate fechaNacimiento;

    public Long getIdTitular() {
        return idTitular;
    }
    public void setIdTitular(Long idTitular) {
        this.idTitular = idTitular;
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
}
