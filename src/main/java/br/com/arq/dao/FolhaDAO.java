package br.com.arq.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import br.com.arq.model.Folha;
import br.com.arq.model.StatusFolha;
import br.com.arq.model.TipoFolha;

public interface FolhaDAO extends AppDAO<Folha> {

	@Query("FROM Folha f WHERE f.conta.id = ?1")
	public List<Folha> findAll(Integer idConta);

	@Query("FROM Folha f WHERE f.conta.id = ?1 AND f.statusFolha = ?2")
	public List<Folha> findAll(Integer idConta, StatusFolha status);
	
	@Query("FROM Folha f WHERE f.conta.id = ?1 AND f.dataPrevistaQuitacao BETWEEN ?2 AND ?3")
	public List<Folha> findAll(Integer idConta, Date d0, Date d1);
	
	@Query("FROM Folha f WHERE f.conta.id = ?1 AND f.dataPrevistaQuitacao BETWEEN ?2 AND ?3 AND f.statusFolha = ?4")
	public List<Folha> findAll(Integer idConta, Date d0, Date d1, StatusFolha status);
	
	@Query("SELECT SUM(f.valor) FROM Folha f WHERE f.tipo = ?1 AND f.conta.id = ?2")
	public BigDecimal countBy(TipoFolha tipo, Integer conta);
	
	@Query("SELECT SUM(f.valor) FROM Folha f WHERE f.tipo = ?1 AND f.conta.id = ?2 AND f.dataPrevistaQuitacao BETWEEN ?3 AND ?4")
	public BigDecimal countBy(TipoFolha tipo, Integer conta, Date d0, Date d1);
	
	@Query("SELECT SUM(f.valor) FROM Folha f WHERE f.tipo = ?1 AND f.conta.id = ?2 AND f.statusFolha = ?3")
	public BigDecimal countBy(TipoFolha tipo, Integer conta, StatusFolha status);
	
	@Query("SELECT SUM(f.valor) FROM Folha f WHERE f.tipo = ?1 AND f.conta.id = ?2 AND f.dataPrevistaQuitacao BETWEEN ?3 AND ?4 AND f.statusFolha = ?4")
	public BigDecimal countBy(TipoFolha tipo, Integer conta, Date d0, Date d1, StatusFolha status);
}
