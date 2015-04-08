package br.com.arq.controller;

import java.awt.Component;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.arq.dao.AppDAO;
import br.com.arq.model.Entidade;
import br.com.arq.ui.AppUI;
import br.com.arq.validation.ValidacaoException;

public abstract class AppController<T extends Entidade> {

	private static final String TITULO_ERRO = "Erro";
	protected static final String MSG_SALVAR_SUCESSO = "Dados salvos com sucesso!";
	protected static final Logger LOG = Logger.getLogger(AppController.class.getName());

	protected abstract AppDAO<T> getDao();

	public void salvar(final AppUI<T> ui) {
		final Component comp = ui.getFrame();
		try {
			ui.validar();
			getDao().save(ui.getEntidade());
			ui.iniciarDados();
			ui.limparComponentes();
			exibirMensagemSalvarSucesso(comp);
		} catch (ValidacaoException ex) {
			exibirMensagemErro(ex.getMessage(), comp);
		}
	}

	protected void exibirMensagemErro(final String msg, final Component comp) {
		JOptionPane.showMessageDialog(comp, msg, TITULO_ERRO, JOptionPane.ERROR_MESSAGE);
	}

	protected void exibirMensagemSalvarSucesso(final Component comp) {
		JOptionPane.showMessageDialog(comp, MSG_SALVAR_SUCESSO);
	}
}
