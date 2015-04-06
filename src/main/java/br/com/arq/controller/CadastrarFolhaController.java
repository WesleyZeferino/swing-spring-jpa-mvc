package br.com.arq.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.CategoriaDAO;
import br.com.arq.dao.FolhaDAO;
import br.com.arq.model.Categoria;
import br.com.arq.model.Folha;
import br.com.arq.ui.CadastrarFolhaUI;

@Component
public class CadastrarFolhaController extends AppController<Folha> {

	@Autowired
	private FolhaDAO dao;
	
	@Autowired
	private CategoriaDAO categoriaDAO;
	
	@Autowired
	private CadastrarFolhaUI ui;
	
	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
		List<Categoria> categorias = categoriaDAO.findAll();
		ui.setCategorias(categorias);
		
		ui.getBtnSalvar().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ui.getEntidade();
			}
		});
	}
	
	public CadastrarFolhaUI getUi() {
		return ui;
	}
	
	public FolhaDAO getDao() {
		return dao;
	}

}
