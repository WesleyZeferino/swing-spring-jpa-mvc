package br.com.arq.dto;

import java.math.BigDecimal;
import java.util.Date;

import br.com.arq.model.Categoria;
import br.com.arq.model.ContaBancaria;
import br.com.arq.model.TipoFolha;

public class TransacaoDTO {

	private String descricao;
	private Date quitacao;
	private BigDecimal valor;
	private ContaBancaria conta;
	private Categoria categoria;
	private TipoFolha tipo;

	public TransacaoDTO(final String descricao, final Date quitacao, final BigDecimal valor, final ContaBancaria conta, final TipoFolha tipo) {
		super();
		this.descricao = descricao;
		this.quitacao = quitacao;
		this.valor = valor;
		this.conta = conta;
		this.tipo = tipo;
	}

	public TransacaoDTO() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public Date getQuitacao() {
		return quitacao;
	}

	public void setQuitacao(final Date quitacao) {
		this.quitacao = quitacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(final BigDecimal valor) {
		this.valor = valor;
	}

	public ContaBancaria getConta() {
		return conta;
	}

	public void setConta(final ContaBancaria conta) {
		this.conta = conta;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(final Categoria categoria) {
		this.categoria = categoria;
	}

	public TipoFolha getTipo() {
		return tipo;
	}

	public void setTipo(final TipoFolha tipo) {
		this.tipo = tipo;
	}
}
