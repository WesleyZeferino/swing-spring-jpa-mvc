package br.com.arq.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.arq.dao.ClienteDAO;
import br.com.arq.model.Cliente;
import br.com.arq.ui.ListarClienteUI;

@Component
public class ListarClienteController extends AppController<Cliente> {

	@Autowired
	private ClienteDAO dao;

	@Autowired
	private ListarClienteUI ui;

	@PostConstruct
	private void init() {
		ui.getBtnAtualizar().addActionListener(e -> {
			Page<Cliente> page = dao.findAll(new PageRequest(0, 15));
			ui.setDados(page.getContent());
		});
	}
	
	@Override
	public ClienteDAO getDao() {
		return dao;
	}

	public ListarClienteUI getUi() {
		return ui;
	}
}
