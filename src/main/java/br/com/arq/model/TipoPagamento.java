package br.com.arq.model;

public enum TipoPagamento {
	DINHEIRO("Dinheiro"), 
	CARTAO_DEBITO("Cartão de Débito"), 
	CARTAO_CREDITO("Cartão de Crédito"), 
	CHEQUE("Cheque");
	
	private final String descricao;
	
	private TipoPagamento(final String descricao) {
		this.descricao = descricao;
	}
	
	public String toString() {
		return this.descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
