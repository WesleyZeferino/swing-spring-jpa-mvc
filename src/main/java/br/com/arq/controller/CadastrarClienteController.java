package br.com.arq.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.ClienteDAO;
import br.com.arq.model.Cliente;
import br.com.arq.ui.CadastrarClienteUI;

@Component
public class CadastrarClienteController extends AppController<Cliente> {

	@Autowired
	private CadastrarClienteUI ui;

	@Autowired
	private ClienteDAO dao;

	@Autowired
	private ListarClienteController listaController;

	@PostConstruct
	private void init() {
		registrarAcoes();
	}

	private void registrarAcoes() {
		ui.getBtnSalvar().addActionListener(e -> salvar(ui));
	}

	@Override
	public ClienteDAO getDao() {
		return dao;
	}

	@Override
	public CadastrarClienteUI getUi() {
		return ui;
	}
}
