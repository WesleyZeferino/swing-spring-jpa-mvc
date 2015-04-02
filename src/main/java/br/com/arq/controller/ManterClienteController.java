package br.com.arq.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.ClienteDAO;
import br.com.arq.model.Cliente;
import br.com.arq.ui.ManterClienteUI;

@Component
@SuppressWarnings("unused")
public class ManterClienteController extends AppController<Cliente> {

	@Autowired
	private ManterClienteUI ui;

	@Autowired
	private ClienteDAO dao;

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
	}

	@Override
	public ClienteDAO getDao() {
		return dao;
	}

	public ManterClienteUI getUi() {
		return ui;
	}
}
