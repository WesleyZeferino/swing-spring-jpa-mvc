package br.com.arq.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_conta_bancaria")
public class ContaBancaria extends Entidade {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 5, max = 255)
	private String descricao;
	
	@Override
	public String toString() {
		return this.descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(final String descricao) {
		this.descricao = descricao;
	}
}
