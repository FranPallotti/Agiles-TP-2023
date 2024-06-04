package isi.agiles.dto;

import java.time.LocalDate;

import isi.agiles.entidad.TipoDoc;

public class TitularDTO {
    private Long idTitular;
    private String nombre;
    private String apellido;
    private String documento;
    private LocalDate fechaNacimiento;
    private TipoDoc tipoDoc;
    
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
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public Long getIdTitular() {
        return idTitular;
    }
    public void setIdTitular(Long idTitular) {
        this.idTitular = idTitular;
    }
    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }
    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }

    
}
