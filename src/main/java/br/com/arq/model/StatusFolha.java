package br.com.arq.model;

public enum StatusFolha {
	ABERTO("Aberto"), VENCIDO("Vencido"), QUITADO("Quitado");

	private String descricao;

	private StatusFolha(final String descricao) {
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
