package br.com.arq.controller;

import java.awt.Component;

import javax.swing.JOptionPane;

import br.com.arq.dao.AppDAO;
import br.com.arq.model.Entidade;
import br.com.arq.ui.AppUI;

public abstract class AppController<T extends Entidade> {

	protected abstract AppDAO<T> getDao();

	public void salvar(final AppUI<T> ui) {
		final String msg = ui.validar();
		final Component comp = ui.getFramePrincipal();
		if (msg.isEmpty()) {
			final T entidade = ui.getEntidade();
			getDao().save(entidade);
			ui.iniciarDados();
			ui.limparComponentes(ui.getFramePrincipal());
			JOptionPane.showMessageDialog(comp, "Dados salvos com sucesso!");
		} else {
			JOptionPane.showMessageDialog(comp, msg, "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}
