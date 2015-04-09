package br.com.arq.model;

public enum StatusFolha {
	ABERTO("Aberto"),
	VENCIDO("Vencido"),
	QUITADO("Quitado");
	
	private String descricao;
	
	private StatusFolha(final String descricao) {
		this.descricao = descricao;
	}
	
	public String toString() {
		return this.descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
