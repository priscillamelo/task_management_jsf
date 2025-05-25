package br.com.estagio.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private final static String PERSISTENCE_UNIT_NAME = "task_managementPU";
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static void closeEntityManager(EntityManager em) {
		if (em != null && em.isOpen())
			em.close();
	}

}
