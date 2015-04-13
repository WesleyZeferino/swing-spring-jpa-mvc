package br.com.arq.model;

public enum TipoFolha {
	RECEITA("Receita"), DESPESA("Despesa");

	private final String descricao;

	private TipoFolha(final String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
