package br.com.arq.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arq.model.Entidade;

public interface AppDAO<T extends Entidade> extends JpaRepository<T, Integer> {
}
