package br.com.arq.dao;

import java.util.List;

import br.com.arq.model.Folha;
import br.com.arq.model.TipoFolha;

public interface FolhaDAO extends AppDAO<Folha> {
	
	public List<Folha> findByTipo(TipoFolha tipo);
}
