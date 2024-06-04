package isi.agiles.dao;

import isi.agiles.entidad.Titular;
import jakarta.persistence.EntityManager;

public class TitularDAO extends AbstractDAO<Titular> {

    public TitularDAO(EntityManager entityManager) {
        super(entityManager);
        setClase(Titular.class);
    }

}
