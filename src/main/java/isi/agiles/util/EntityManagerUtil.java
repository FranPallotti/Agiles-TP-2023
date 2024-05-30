package isi.agiles.util;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {
    private static EntityManagerFactory emf;
    public static EntityManager getEntityManager() {
		//EntityManagerFactory factory = Persistence.createEntityManagerFactory("tpAgiles");
		EntityManager manager= emf.createEntityManager();
		return manager;
	}
    public static void createEntityManagerFactory() {
		emf=Persistence.createEntityManagerFactory("tpAgiles");
	}
}
