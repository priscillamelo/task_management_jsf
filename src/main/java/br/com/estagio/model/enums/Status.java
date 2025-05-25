package br.com.estagio.model.enums;

public enum Status {
	EM_ANDAMENTO("Em andamento"),
	CONCLUIDO("Concluído");
	
	
	private final String status;
	Status(String status) {
		System.out.println("Criando enum " + status);
		this.status = status;
		System.out.println("Status " + this.status);
	}
	public String getStatus() {
		System.out.println("Get Status " + status);
		return status;
	}
	
}
