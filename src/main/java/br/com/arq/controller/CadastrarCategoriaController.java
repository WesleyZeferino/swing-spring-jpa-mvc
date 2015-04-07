package br.com.arq.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.CategoriaDAO;
import br.com.arq.model.Categoria;
import br.com.arq.ui.CadastrarCategoriaUI;
import br.com.arq.util.ListasUteis;

@Component
public class CadastrarCategoriaController extends AppController<Categoria> {

	@Autowired
	private CategoriaDAO dao;
	
	@Autowired
	private CadastrarCategoriaUI ui;
	
	@Autowired
	private ListasUteis listas;
	
	@PostConstruct
	private void init() {
		ui.getBtnSalvar().addActionListener(e -> {
			salvar(ui);
			listas.setCategorias(dao.findAll());
		});
	}
	
	public CadastrarCategoriaUI getUi() {
		return ui;
	}
	
	public CategoriaDAO getDao() {
		return dao;
	}
}
