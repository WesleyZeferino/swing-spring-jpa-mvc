package br.com.arq.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.ClienteDAO;
import br.com.arq.model.Cliente;
import br.com.arq.ui.CadastrarClienteUI;

@Component
@SuppressWarnings("unused")
public class CadastrarClienteController extends AppController<Cliente> {

	@Autowired
	private CadastrarClienteUI ui;

	@Autowired
	private ClienteDAO dao;

	@Autowired
	private ListarClienteController listaController;

	@PostConstruct
	private void init() {
		ui.show();
		registrarAcoes();
	}

	private void registrarAcoes() {
		ui.getBtnSalvar().addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				salvar(ui);
			}
		});

		ui.getBtnListar().addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				listaController.getUi().show();
			}
		});
	}

	@Override
	public ClienteDAO getDao() {
		return dao;
	}

	public CadastrarClienteUI getUi() {
		return ui;
	}
}
