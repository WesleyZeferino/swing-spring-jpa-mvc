package br.com.arq.model;

public enum TipoPagamento {
	INDEFINIDO(""),
	DINHEIRO("Dinheiro"), 
	CARTAO_DEBITO("Cartão de Débito"), 
	CARTAO_CREDITO("Cartão de Crédito"), 
	CHEQUE("Cheque"), 
	DEBITO("Débito automático");

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
