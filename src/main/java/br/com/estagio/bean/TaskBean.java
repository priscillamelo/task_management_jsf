package br.com.estagio.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.estagio.model.Task;
import br.com.estagio.model.enums.Priority;
import br.com.estagio.model.enums.Responsible;
import br.com.estagio.model.enums.Status;
import br.com.estagio.repository.TaskRepository;

@Named(value = "taskBean") // bean usado nas páginas
@ViewScoped // mantém o estado do bean enquanto estiver na mesma pagina
public class TaskBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String titleOrDescFilter;
	private String responsibleFilter;
	private String statusFilter;
	
	@Inject
	private TaskRepository taskRepository;
	
	private Task task;
	private List<Task> listTasks;

	@PostConstruct
	public void init() {
		loadListTasks();
		clearTask();
		System.out.println("TaskBean: Tarefas carregadas: " + (listTasks != null ? listTasks.size() : 0));
	}
	
	public void loadListTasks() {
		this.listTasks = taskRepository.findAllTasks();
	}
	
	public void clearTask() {
		this.task = new Task();
	}

	public boolean filterEmpty() {
		return (titleOrDescFilter == null || titleOrDescFilter.trim().isEmpty()) &&
				(statusFilter == null || statusFilter.trim().isEmpty()) &&
				(responsibleFilter == null || responsibleFilter.trim().isEmpty());
	}

	public boolean filterWithValue() {
		return !filterEmpty();
	}

	public void search() {
		System.out.println(titleOrDescFilter);
		if (filterWithValue()) {
			if(titleOrDescFilter != null && !titleOrDescFilter.trim().isEmpty()){
				findByTitleOrDesc(titleOrDescFilter);
			} else if(responsibleFilter != null && !responsibleFilter.trim().isEmpty()) {
				System.out.println(responsibleFilter);
				findByResponsible(responsibleFilter);
			} else if(statusFilter != null && !statusFilter.trim().isEmpty()) {
				findByStatus(statusFilter);
			}
		}
	}

	public void clearFilter() {
		this.titleOrDescFilter = null;
		this.responsibleFilter = null;
		this.statusFilter = null;
		loadListTasks();
	}


	public void save() {
		try {
            System.out.println("Salvando tarefa: " + task.getTitle());
            taskRepository.saveTask(task);
            loadListTasks(); // Recarrega a lista
            clearTask(); // Limpa o formulário
            //FacesMessage.FACES_MESSAGES.toString();
            // Adicionar mensagem de sucesso (FacesMessage) aqui seria bom
        } catch (Exception e) {
            System.err.println("Erro ao salvar no TaskBean: " + e.getMessage());
            // Adicionar mensagem de erro (FacesMessage) aqui seria bom
            //FacesMessage.SEVERITY_ERROR.toString();
        }
	}

	public void findByTitleOrDesc(String titleOrDesc) {
		try {
            System.out.println("Buscando tarefa por título/descrição: " + titleOrDesc);
			listTasks = taskRepository.findTaskByTitleOrDesc(titleOrDesc);
        } catch (Exception e) {
            System.err.println("Erro ao buscar no TaskBean: " + e.getMessage());
            // Adicionar mensagem de erro (FacesMessage) aqui seria bom
        }
	}

	public void findByResponsible(String responsible) {
		try {
            System.out.println("Buscando tarefa por responsável: " + responsible);
			listTasks = taskRepository.findTaskByResponsible(responsible);
        } catch (Exception e) {
            System.err.println("Erro ao buscar no TaskBean: " + e.getMessage());
        }
	}

	public void findByStatus(String status) {
		try {
            System.out.println("Buscando tarefa por status: " + status);
			listTasks = taskRepository.findTaskByStatus(status);
		} catch (Exception e) {
			System.err.println("Erro ao buscar no TaskBean: " + e.getMessage());
		}
	}

	public void finishedTask(Task task){
		task.setStatus(Status.CONCLUIDO.getStatus());
		update(task);
	}

	public void update(Task task) {
		try {
            System.out.println("Atualizando tarefa: " + task.getTitle());
            taskRepository.updateTask(task);
            loadListTasks(); // Recarrega a lista
            clearTask(); // Limpa o formulário
            // Adicionar mensagem de sucesso (FacesMessage) aqui seria bom
        } catch (Exception e) {
            System.err.println("Erro ao atualizar no TaskBean: " + e.getMessage());
        }
	}
	
	public void delete(Task task) {
		try {
            System.out.println("Deletando tarefa: " + task.getTitle());
            taskRepository.deleteTask(task.getId());
            loadListTasks(); // Recarrega a lista
        } catch (Exception e) {
            System.err.println("Erro ao deletar no TaskBean: " + e.getMessage());
            // Adicionar mensagem de erro (FacesMessage) aqui seria bom
        }
	}

	// GETTERS E SETTERS

	public String getTitleOrDescFilter() {
		return titleOrDescFilter;
	}

	public void setTitleOrDescFilter(String titleOrDescFilter) {
		this.titleOrDescFilter = titleOrDescFilter;
	}

	public String getResponsibleFilter() {
		return responsibleFilter;
	}

	public void setResponsibleFilter(String responsibleFilter) {
		this.responsibleFilter = responsibleFilter;
	}

	public String getStatusFilter() {
		return statusFilter;
	}

	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<Task> getListTasks() {
		return listTasks;
	}

	public void setListTasks(List<Task> listTasks) {
		this.listTasks = listTasks;
	}

	public List<String> getPriorities() {
		List<String> priorities = new ArrayList<>();

		for (Priority p : Priority.values()) {
			priorities.add(p.getPriority());
		}
		return priorities;
	}

	public List<String> getResponsible() {
		List<String> responsible = new ArrayList<>();

		for (Responsible r : Responsible.values()) {
			responsible.add(r.getResponsible());
		}
		return responsible;
	}

	public List<String> getStatus() {
		List<String> status = new ArrayList<>();

		for (Status s : Status.values()) {
			status.add(s.getStatus());
		}
		return status;
	}

}
