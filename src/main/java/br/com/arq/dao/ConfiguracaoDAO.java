package br.com.arq.dao;

import org.springframework.data.jpa.repository.Query;

import br.com.arq.model.Configuracao;

public interface ConfiguracaoDAO extends AppDAO<Configuracao> {

	@Query("FROM Configuracao")
	public Configuracao obterConfiguracao();
}
