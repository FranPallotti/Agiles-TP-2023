package isi.agiles.dao;

import java.util.Optional;

import isi.agiles.entidad.ClaseLicencia;

public class ClaseLicenciaDAO extends AbstractDAO<ClaseLicencia> {
    public ClaseLicenciaDAO(){
        this.setClase(ClaseLicencia.class);
    }
    public Optional<ClaseLicencia> getById(Character id) {
		return Optional.ofNullable(entityManager.find(ClaseLicencia.class, id)); //ver de cambiar por elsethrow
	}
}
