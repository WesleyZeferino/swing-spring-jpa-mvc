package br.com.arq.controller;

import java.awt.Component;

import javax.swing.JOptionPane;

import br.com.arq.dao.AppDAO;
import br.com.arq.model.Entidade;
import br.com.arq.ui.AppUI;

public abstract class AppController<T extends Entidade> {

	private static final String TITULO_ERRO = "Erro";
	protected static final String MSG_SALVAR_SUCESSO = "Dados salvos com sucesso!";

	protected abstract AppDAO<T> getDao();

	public void salvar(final AppUI<T> ui) {
		final String msg = ui.validar();
		final Component comp = ui.getFrame();
		if (msg.isEmpty()) {
			getDao().save(ui.getEntidade());
			ui.iniciarDados();
			ui.limparComponentes(ui.getFrame());
			JOptionPane.showMessageDialog(comp, MSG_SALVAR_SUCESSO);
		} else {
			JOptionPane.showMessageDialog(comp, msg, TITULO_ERRO, JOptionPane.ERROR_MESSAGE);
		}
	}
}
