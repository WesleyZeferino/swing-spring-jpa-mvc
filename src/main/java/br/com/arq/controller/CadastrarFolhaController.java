package br.com.arq.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.CategoriaDAO;
import br.com.arq.dao.FolhaDAO;
import br.com.arq.model.ContaBancaria;
import br.com.arq.model.Folha;
import br.com.arq.ui.CadastrarFolhaUI;
import br.com.arq.ui.ListarFolhaUI;

@Component
public class CadastrarFolhaController extends AppController<Folha> {

	@Autowired
	private FolhaDAO dao;
	
	@Autowired
	private CategoriaDAO categoriaDAO;
	
	@Autowired
	private CadastrarFolhaUI ui;
	
	@Autowired
	private ListarFolhaUI listUi;
	
	private ContaBancaria contaSelecionada;
	
	@PostConstruct
	private void init() {
		ui.getBtnSalvar().addActionListener(e -> {
			getUi().getFolha().setConta(contaSelecionada);
			salvar(getUi());
		});
		
		ui.getBtnListar().addActionListener(e -> {
			listUi.getTabela().setDados(dao.findAll());
			listUi.show();
		});
	}
	
	public void setContaSelecionada(ContaBancaria contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}
	
	public ContaBancaria getContaSelecionada() {
		return contaSelecionada;
	}
	
	public CadastrarFolhaUI getUi() {
		return ui;
	}
	
	public FolhaDAO getDao() {
		return dao;
	}

}
