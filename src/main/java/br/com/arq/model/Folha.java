package br.com.arq.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_folha")
public class Folha extends Entidade {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private TipoFolha tipo;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private StatusFolha statusFolha;

	@NotNull
	@Digits(fraction = 2, integer = 10)
	private BigDecimal valor;

	@NotNull
	@ManyToOne
	@JoinColumn
	private ContaBancaria conta;
	
	@NotNull
	@ManyToOne
	@JoinColumn
	private Categoria categoria;
	
	@Size(max = 255)
	private String descricao;
	
	private int parcela;
	
	private int totalParcela;
	
	private TipoPagamento tipoPagamento;
	
	private Folha filho;
	
	@Temporal(TemporalType.DATE)
	private Date dataQuitacao;
	
	@Temporal(TemporalType.DATE)
	private Date dataPrevistaQuitacao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataLancamento;
	
	@PrePersist
	private void preSalvar() {
		setDataLancamento(new Date());
	}

	public TipoFolha getTipo() {
		return tipo;
	}

	public void setTipo(final TipoFolha tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}

	public StatusFolha getStatusFolha() {
		return statusFolha;
	}

	public void setStatusFolha(final StatusFolha statusFolha) {
		this.statusFolha = statusFolha;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public ContaBancaria getConta() {
		return conta;
	}

	public void setConta(ContaBancaria conta) {
		this.conta = conta;
	}

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

	public int getTotalParcela() {
		return totalParcela;
	}

	public void setTotalParcela(int totalParcela) {
		this.totalParcela = totalParcela;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public Folha getFilho() {
		return filho;
	}

	public void setFilho(Folha filho) {
		this.filho = filho;
	}

	public Date getDataQuitacao() {
		return dataQuitacao;
	}

	public void setDataQuitacao(Date dataQuitacao) {
		this.dataQuitacao = dataQuitacao;
	}

	public Date getDataPrevistaQuitacao() {
		return dataPrevistaQuitacao;
	}

	public void setDataPrevistaQuitacao(Date dataPrevistaQuitacao) {
		this.dataPrevistaQuitacao = dataPrevistaQuitacao;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
