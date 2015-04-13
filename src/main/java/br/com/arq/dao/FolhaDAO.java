package br.com.arq.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import br.com.arq.model.Folha;
import br.com.arq.model.TipoFolha;

public interface FolhaDAO extends AppDAO<Folha> {

	public List<Folha> findByTipo(TipoFolha tipo);

	@Query("FROM Folha f WHERE f.conta.id like ?1")
	public Page<Folha> findAllByConta(Integer idConta, Pageable pageable);
}
