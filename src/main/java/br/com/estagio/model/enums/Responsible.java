package br.com.estagio.model.enums;

public enum Responsible {
	JOAO("João"),
    ANTONIO("Antônio");

    private final String responsible;

    Responsible(String responsible) {
        this.responsible = responsible;
    }

    public String getResponsible() {
        return responsible;
    }
}
