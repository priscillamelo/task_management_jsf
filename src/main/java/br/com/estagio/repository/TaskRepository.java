package br.com.estagio.repository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.estagio.model.Task;
import br.com.estagio.model.enums.Status;
import br.com.estagio.util.JPAUtil;

@ApplicationScoped
public class TaskRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	public Task findTaskById(Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		try {
			String jpql = "SELECT t FROM Task t WHERE t.id = :id";
			TypedQuery<Task> query = entityManager.createQuery(jpql, Task.class);
			query.setParameter("id", id);
			return query.getSingleResult();
		} finally {
			JPAUtil.closeEntityManager(entityManager);
		}
	}

	public List<Task> findTaskByTitleOrDesc(String titleOrDesc) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		try {
			System.out.println("Título ou descrição: " + titleOrDesc);
			String jpql = "SELECT t FROM Task t WHERE LOWER(t.title) LIKE CONCAT('%', :titleOrDesc, '%') OR LOWER(t.description) LIKE CONCAT('%', :titleOrDesc, '%')";
			TypedQuery<Task> query = entityManager.createQuery(jpql, Task.class);
			query.setParameter("titleOrDesc", titleOrDesc.toLowerCase());
			return query.getResultList();
		} finally {
			JPAUtil.closeEntityManager(entityManager);
		}
	}

	public List<Task> findTaskByResponsible(String responsible) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		try {
			String jpql = ("SELECT t FROM Task t WHERE t.responsible = :responsible");
			TypedQuery<Task> query = entityManager.createQuery(jpql, Task.class);
			query.setParameter("responsible", responsible);
			return query.getResultList();
		} finally {
			JPAUtil.closeEntityManager(entityManager);
		}
	}

	public List<Task> findTaskByStatus(String status) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		try {
			String jpql = "SELECT t FROM Task t WHERE t.status = :status";
			TypedQuery<Task> query = entityManager.createQuery(jpql, Task.class);
			query.setParameter("status", status);
			return query.getResultList();
		} finally {
			JPAUtil.closeEntityManager(entityManager);
		}
	}

	public void saveTask(Task task) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		entityManager.persist(task);
		entityManager.getTransaction().commit();

		JPAUtil.closeEntityManager(entityManager);

	}

	public List<Task> findAllTasks() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			String jpql = "SELECT t FROM Task t WHERE t.status = :status ORDER BY t.deadline ASC ";
			TypedQuery<Task> query = em.createQuery(jpql, Task.class);
			query.setParameter("status", Status.EM_ANDAMENTO.getStatus());
			return query.getResultList();
		} finally {
			JPAUtil.closeEntityManager(em);
		}
	}
	
	public List<Task> findAllTasksFinished() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			String jpql = "SELECT t FROM Task t WHERE t.status = :status";
			TypedQuery<Task> query = em.createQuery(jpql, Task.class);
			query.setParameter("status", Status.CONCLUIDO);
			return query.getResultList();
		} finally {
			JPAUtil.closeEntityManager(em);
		}
	}

	public void updateTask(Task task) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();
		entityManager.merge(task);
		entityManager.getTransaction().commit();

		JPAUtil.closeEntityManager(entityManager);
	}

	public void deleteTask(Long id) {
		EntityManager entityManager = JPAUtil.getEntityManager();

		entityManager.getTransaction().begin();

		Task task = entityManager.find(Task.class, id);
		
		if (task != null) {
			entityManager.remove(task);
		}

		entityManager.getTransaction().commit();

		JPAUtil.closeEntityManager(entityManager);
	}

}
