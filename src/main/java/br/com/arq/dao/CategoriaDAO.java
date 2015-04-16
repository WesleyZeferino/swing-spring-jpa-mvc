package br.com.arq.dao;

import org.springframework.data.jpa.repository.Query;

import br.com.arq.model.Categoria;

public interface CategoriaDAO extends AppDAO<Categoria> {

	@Query("FROM Categoria c WHERE c.descricao LIKE ?1")
	public Categoria findAllByDescricao(String descricao);
}
