package br.com.estagio.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.estagio.model.Task;
import br.com.estagio.model.enums.Priority;
import br.com.estagio.model.enums.Responsible;

public class TaskValidationTest {
	
	private static Validator validator;
	
	@BeforeAll
    public static void initializerValidator() {
        ValidatorFactory factory = Validation
        		.byDefaultProvider()
        		.configure()
        		.messageInterpolator(new ParameterMessageInterpolator())
        		.buildValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
	@DisplayName("Campos obrigatórios não podem estar vazios.")
	public void requiredFieldsNotBlankTest() {
		Task task = new Task();
        task.setTitle("");
        task.setPriority("");
        task.setResponsible("");        
        
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        
        assertFalse(violations.isEmpty(), "Deve haver violações de validação para campos obrigatórios.");
        
	}
	
	@Test
	@DisplayName("Título da tarefa não pode estar vazio.")
	public void titleNotBlankTest() {
		Task task = new Task();
        task.setTitle("");
        task.setPriority(Priority.ALTA.getPriority());
        task.setResponsible(Responsible.JOAO.getResponsible());        
        
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        
        assertFalse(violations.isEmpty(), "Deve haver violação de validação para título vazio");
        
        ConstraintViolation<Task> violation = violations.iterator().next();
        assertEquals("Escreva um título para a tarefa.", violation.getMessage());
        
	}
	
	@Test
	@DisplayName("Prioridade da tarefa não pode estar vazio.")
	public void priorityNotBlankTest() {
		Task task = new Task();
        task.setTitle("Tarefa 1");
        task.setPriority("");
        task.setResponsible(Responsible.JOAO.getResponsible()); 
        
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        
        assertFalse(violations.isEmpty(), "Deve haver violação de validação para prioridade vazio");
        
        ConstraintViolation<Task> violation = violations.iterator().next();
        assertEquals("Selecione uma prioridade para a tarefa.", violation.getMessage());
        
	}
	
	@Test
	@DisplayName("Responsável pela a tarefa não pode estar vazio.")
	public void responsibleNotBlankTest() {
		Task task = new Task();
        task.setTitle("Tarefa 1");
        task.setPriority(Priority.ALTA.getPriority());
        task.setResponsible(""); 
        
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        
        assertFalse(violations.isEmpty(), "Deve haver violação de validação para responsável vazio");
        
        ConstraintViolation<Task> violation = violations.iterator().next();
        assertEquals("Escolha o responsável pela a tarefa.", violation.getMessage());
        
	}

}
