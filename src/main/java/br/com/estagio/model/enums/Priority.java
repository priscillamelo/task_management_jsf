package br.com.estagio.model.enums;

public enum Priority {
	BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta");

    private final String priority;

    Priority(String priority) {
        this.priority = priority;
    }

	public String getPriority() {
		return priority;
	}

}
