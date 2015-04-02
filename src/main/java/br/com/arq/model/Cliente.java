package br.com.arq.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_cliente")
public class Cliente extends Entidade {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 3, max = 255)
	private String nome;

	@NotNull
	private String cpf;

	public Cliente() {
		super();
	}

	public Cliente(final String nome, final String cpf) {
		super();
		setNome(nome);
		setCpf(cpf);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(final String cpf) {
		this.cpf = cpf;
	}
}
