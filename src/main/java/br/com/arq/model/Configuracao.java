package br.com.arq.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_configuracao")
public class Configuracao extends Entidade {

	public Configuracao() {
		super();
	}

	public Configuracao(final String diretorio) {
		super();
		this.diretorio = diretorio;
	}

	private static final long serialVersionUID = 1L;

	private String diretorio;

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(final String diretorio) {
		this.diretorio = diretorio;
	}

}
