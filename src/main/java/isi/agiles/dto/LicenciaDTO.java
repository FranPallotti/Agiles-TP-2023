package isi.agiles.dto;

import java.time.LocalDate;

import isi.agiles.entidad.EstadoLicencia;

public class LicenciaDTO {

    private Long idLicencia;
    private ClaseLicenciaDTO claseLic;
    private String observaciones;
    private TitularDTO titular;
    private LocalDate inicioVigencia;
    private LocalDate finVigencia;
    private EstadoLicencia estado;
    private Float costo;
    
    /*Getters y setters */
    public EstadoLicencia getEstado() {
        return estado;
    }
    public void setEstado(EstadoLicencia estado) {
        this.estado = estado;
    }
    public ClaseLicenciaDTO getClaseLic() {
        return claseLic;
    }
    public void setClaseLic(ClaseLicenciaDTO clase) {
        this.claseLic = clase;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public TitularDTO getTitular() {
        return titular;
    }
    public void setTitular(TitularDTO titular) {
        this.titular = titular;
    }
    public LocalDate getInicioVigencia() {
        return inicioVigencia;
    }
    public void setInicioVigencia(LocalDate inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }
    public LocalDate getFinVigencia() {
        return finVigencia;
    }
    public void setFinVigencia(LocalDate finVigencia) {
        this.finVigencia = finVigencia;
    }
    public Float getCosto() {
        return costo;
    }
    public void setCosto(Float costo) {
        this.costo = costo;
    }
    public Long getIdLicencia() {
        return idLicencia;
    }
    public void setIdLicencia(Long idLicencia) {
        this.idLicencia = idLicencia;
    }
    
}
