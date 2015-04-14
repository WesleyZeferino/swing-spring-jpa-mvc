package br.com.arq.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.ContaBancariaDAO;
import br.com.arq.model.ContaBancaria;
import br.com.arq.ui.CadastrarContaUI;
import br.com.arq.util.ListasUteis;

@Component
public class CadastrarContaController extends AppController<ContaBancaria> {

	@Autowired
	private ContaBancariaDAO dao;

	@Autowired
	private CadastrarContaUI ui;

	@Autowired
	private ListasUteis listas;
	
	@Autowired
	private ListarContaController lstController;

	@PostConstruct
	private void init() {
		ui.getBtnSalvar().addActionListener(e -> {
			salvar(ui);
			listas.setContas(dao.findAll());
		});
		
		ui.getBtnListar().addActionListener(e -> {
			lstController.atualizar();
			lstController.getUi().getFrame().setVisible(true);
		});
	}

	@Override
	public CadastrarContaUI getUi() {
		return ui;
	}

	@Override
	public ContaBancariaDAO getDao() {
		return dao;
	}
}
