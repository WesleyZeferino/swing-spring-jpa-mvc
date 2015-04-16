package br.com.arq.dao;

import org.springframework.data.jpa.repository.Query;

import br.com.arq.model.ContaBancaria;

public interface ContaBancariaDAO extends AppDAO<ContaBancaria> {

	@Query("FROM ContaBancaria c WHERE c.descricao LIKE ?1")
	public ContaBancaria findAllByDescricao(String descricao);
}
