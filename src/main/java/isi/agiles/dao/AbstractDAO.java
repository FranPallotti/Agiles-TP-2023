package isi.agiles.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import isi.agiles.util.*;

public abstract class AbstractDAO<T> implements DAO<T> {

	private EntityManager entityManager = EntityManagerUtil.getEntityManager();
	private Class<T> clase;
	
	@Override
	public Optional<T> getById(long id) {
		return Optional.ofNullable(entityManager.find(clase, id)); //ver de cambiar por elsethrow
	}

	@Override
	public void saveInstance(T instance) {
		this.transaccion(entityManager -> entityManager.persist(instance));
	}

	@Override
	public void updateInstance(T instance) {
		this.transaccion(entityManager -> entityManager.merge(instance));
		
	}

	@Override
	public void deleteInstance(T instance) {
		this.transaccion(entityManager-> entityManager.remove(instance));
		
	}

	@Override
	public List<T> getAll() {
		String stringquery = "FROM "+this.clase.getName();
		Query query = entityManager.createQuery(stringquery);
		return query.getResultList();
	}
	
	public void setClase(Class<T> c) {
		this.clase=c;
	}
	
	private void transaccion(Consumer<EntityManager> query) {
		EntityTransaction t = entityManager.getTransaction();
		try {
			t.begin();
			query.accept(entityManager);
			t.commit();
		} catch(RuntimeException exep) {
			t.rollback();
			throw exep;
		}
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}

}