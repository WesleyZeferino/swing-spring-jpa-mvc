package br.com.arq.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
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
	@DecimalMin("0.01")
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
	
	@Column
	private int parcela;

	@Column
	private int totalParcela;

	@Enumerated(EnumType.ORDINAL)
	private TipoPagamento tipoPagamento;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Folha pai;

	@Temporal(TemporalType.DATE)
	private Date dataQuitacao;

	@Temporal(TemporalType.DATE)
	private Date dataPrevistaQuitacao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataLancamento;

	public Folha() {
		super();
	}
	
	public Folha(TipoFolha tipo, StatusFolha statusFolha, BigDecimal valor, ContaBancaria conta, Categoria categoria, String descricao, TipoPagamento tipoPagamento, Date dataQuitacao, Date dataPrevistaQuitacao, Date dataLancamento, Integer id) {
		super();
		this.tipo = tipo;
		this.statusFolha = statusFolha;
		this.valor = valor;
		this.conta = conta;
		this.categoria = categoria;
		this.descricao = descricao;
		this.tipoPagamento = tipoPagamento;
		this.dataQuitacao = dataQuitacao;
		this.dataPrevistaQuitacao = dataPrevistaQuitacao;
		this.dataLancamento = dataLancamento;
		setId(id);
	}

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

	public void setValor(final BigDecimal valor) {
		this.valor = valor;
	}

	public ContaBancaria getConta() {
		return conta;
	}

	public void setConta(final ContaBancaria conta) {
		this.conta = conta;
	}

	public int getParcela() {
		return parcela;
	}

	public void setParcela(final int parcela) {
		this.parcela = parcela;
	}

	public int getTotalParcela() {
		return totalParcela;
	}

	public void setTotalParcela(final int totalParcela) {
		this.totalParcela = totalParcela;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(final TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public Date getDataQuitacao() {
		return dataQuitacao;
	}

	public void setDataQuitacao(final Date dataQuitacao) {
		this.dataQuitacao = dataQuitacao;
	}

	public Date getDataPrevistaQuitacao() {
		return dataPrevistaQuitacao;
	}

	public void setDataPrevistaQuitacao(final Date dataPrevistaQuitacao) {
		this.dataPrevistaQuitacao = dataPrevistaQuitacao;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(final Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(final Categoria categoria) {
		this.categoria = categoria;
	}

	public Folha getPai() {
		return pai;
	}

	public void setPai(final Folha pai) {
		this.pai = pai;
	}

}
