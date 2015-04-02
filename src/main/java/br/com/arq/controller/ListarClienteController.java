package br.com.arq.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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
	@SuppressWarnings("unused")
	private void init() {
		ui.getBtnAtualizar().addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final List<Cliente> clientes = dao.findAll();
				ui.setDados(clientes);
			}
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
