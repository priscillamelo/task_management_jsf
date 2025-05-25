package br.com.estagio.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.estagio.model.Task;
import br.com.estagio.model.enums.Status;
import br.com.estagio.repository.TaskRepository;
import br.com.estagio.util.JPAUtil;

public class TaskRepositoryTest {

	private TaskRepository taskRepository;

	private EntityManager entityManager;

	@BeforeEach
	public void initializer() {
		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
		taskRepository = new TaskRepository();
	}
	
	@AfterEach
    public void tearDown() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback(); // desfaz alterações
        }
        JPAUtil.closeEntityManager(entityManager);
    }
	
	@Test
	@DisplayName("Deve retornar uma lista de tarefas maior ou igual a 2.")
	public void findAllTasksTest() {
	    Task t1 = new Task();
	    t1.setTitle("Tarefa 1");
	    t1.setDescription("Descrição 1");

	    Task t2 = new Task();
	    t2.setTitle("Tarefa 2");
	    t2.setDescription("Descrição 2");

	    taskRepository.saveTask(t1);
	    taskRepository.saveTask(t2);

	    List<Task> tasks = taskRepository.findAllTasks();

	    assertTrue(tasks.size() >= 2);
	}


	@Test
	@DisplayName("Deve salvar uma tarefa.")
	public void saveTaskTest() {
		Task task = new Task();
		task.setTitle("Tarefa 1");
		task.setDescription("Descrição da tarefa 1");
				
		taskRepository.saveTask(task);
		
		Task found = entityManager.find(Task.class, task.getId());
		
		assertNotNull(found);
		assertEquals("Tarefa 1", found.getTitle());
        assertEquals("Descrição da tarefa 1", found.getDescription());
	}
	
	@Test
	@DisplayName("Deve atualizar uma tarefa.")
	public void testUpdateTask() {
	    Task task = new Task();
	    task.setTitle("Tarefa 1");
		task.setDescription("Descrição da tarefa 1");

	    taskRepository.saveTask(task);

	    task.setTitle("Tarefa 1 atualizada");
	    task.setDescription("Descrição da tarefa 1 atualizada");

	    taskRepository.updateTask(task);

	    Task updated = entityManager.find(Task.class, task.getId());

	    assertEquals("Tarefa 1 atualizada", updated.getTitle());
	    assertEquals("Descrição da tarefa 1 atualizada", updated.getDescription());
	}
	
	@Test
	@DisplayName("Buscar uma tarefa pelo ID.")
	public void findTaskByIdTest() {
	    Task task = new Task();
	    task.setTitle("Buscar por ID");
	    task.setDescription("Descrição qualquer");

	    taskRepository.saveTask(task);

	    Task found = taskRepository.findTaskById(task.getId());

	    assertNotNull(found);
	    assertEquals(task.getId(), found.getId());
	}
	
	@Test
	@DisplayName("Deletar uma tarefa.")
	public void deleteTaskTest() {
		Task task = new Task();
		task.setTitle("Tarefa de teste");
		task.setDescription("Descrição de teste");
		
		taskRepository.saveTask(task);		
		taskRepository.deleteTask(task.getId());
		
		Task found = entityManager.find(Task.class, task.getId());
		
		assertNull(found);
	}
	
	@Test
	public void testFindByStatus() {
	    Task task = new Task();
	    task.setTitle("Filtrar por status");
	    task.setDescription("Teste de status");
	    task.setStatus(Status.CONCLUIDO.getStatus());

	    taskRepository.saveTask(task);

	    List<Task> finished = taskRepository.findAllTasksFinished();

	    assertTrue(finished.stream().anyMatch(t -> t.getId().equals(task.getId())));
	}

}
