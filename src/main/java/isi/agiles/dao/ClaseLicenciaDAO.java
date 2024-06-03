package isi.agiles.dao;

import isi.agiles.entidad.ClaseLicencia;

public class ClaseLicenciaDAO extends AbstractDAO<ClaseLicencia> {
    public ClaseLicenciaDAO(){
        this.setClase(ClaseLicencia.class);
    }
}
