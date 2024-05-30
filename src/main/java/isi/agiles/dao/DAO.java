package isi.agiles.dao;

import java.util.List;

import java.util.Optional;

public interface DAO <T> {
	Optional<T> getById(long id);
	void saveInstance(T instance);
	void updateInstance(T instance);
	void deleteInstance(T instance);
	List<T> getAll();

}
