package br.com.arq.model;

/**
 * Arquivo: Frequencia.java <br/>
 *
 * @since 28/10/2014
 * @author Wesley Luiz
 * @version 1.0.0
 */
public enum Frequencia {

	DIARIO("Diário"), SEMANAL("Semanal"), MENSAL("Mensal"), BIMESTRAL("Bimestral"), TRIMESTRAL("Trimestral"), SEMESTRAL("Semestral"), ANUAL("Anual");

	private final String descricao;

	@Override
	public String toString() {
		return getDescricao();
	}

	private Frequencia(final String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
