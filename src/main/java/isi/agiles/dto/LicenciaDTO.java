package isi.agiles.dto;

import java.time.LocalDate;

public class LicenciaDTO {

    private ClaseLicenciaDTO clase;
    private String observaciones;
    private TitularDTO titular;
    private LocalDate inicioVigencia;
    private LocalDate finVigencia;

    /*Getters y setters */
    
    public ClaseLicenciaDTO getClase() {
        return clase;
    }
    public void setClase(ClaseLicenciaDTO clase) {
        this.clase = clase;
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
    
}
