package br.com.arq.model;

public enum TipoFolha {
	RECEITA("Receita"), 
	DESPESA("Despesa");
	
	private final String descricao;
	
	private TipoFolha(final String descricao) {
		this.descricao = descricao;
	}
	
	public String toString() {
		return this.descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
