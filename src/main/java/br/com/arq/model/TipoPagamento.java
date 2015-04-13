package br.com.arq.model;

public enum TipoPagamento {
	DINHEIRO("Dinheiro"), CARTAO_DEBITO("Cartão de Débito"), CARTAO_CREDITO("Cartão de Crédito"), CHEQUE("Cheque"), DEBITO("Débito automático"), INDEFINIDO("");

	private final String descricao;

	private TipoPagamento(final String descricao) {
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
