package br.com.arq.controller;

import java.awt.Component;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.arq.dao.AppDAO;
import br.com.arq.model.Entidade;
import br.com.arq.ui.AppUI;
import br.com.arq.validation.ValidacaoException;

public abstract class AppController<T extends Entidade> {

	private static final String TITULO_EXCLUSAO = "Exclusão";
	private static final String TITULO_ERRO = "Erro";

	protected static final String MSG_DEPENDENCIA = "O item não pode ser excluído (Existe dependência)!";
	protected static final String MSG_EXCLUIR_SUCESSO = "O(s) item(ns) foi(ram) excluído(s) com sucesso!";
	protected static final String MSG_EXCLUIR = "Quer excluir este(s) item(ns)?";
	protected static final String MSG_SALVAR_SUCESSO = "Dados salvos com sucesso!";
	protected static final Logger LOG = Logger.getLogger(AppController.class.getName());

	protected abstract AppDAO<T> getDao();

	protected abstract AppUI<T> getUi();

	public void excluir() {
		excluir(getUi().getFrame(), getUi().getEntidade());
	}

	public void excluir(final T entidade) {
		excluir(getUi().getFrame(), entidade);
	}

	public void excluir(final List<T> entidades) {
		final Component frame = getUi().getFrame();
		final int result = JOptionPane.showConfirmDialog(frame, MSG_EXCLUIR, TITULO_EXCLUSAO, JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			entidades.forEach(it -> {
				try {
					getDao().delete(it);
				} catch (final DataIntegrityViolationException ex) {
				}
			});

			JOptionPane.showMessageDialog(frame, MSG_EXCLUIR_SUCESSO);
		}
	}

	public void excluir(final Component frame, final T entidade) {

		final int result = JOptionPane.showConfirmDialog(frame, MSG_EXCLUIR, TITULO_EXCLUSAO, JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION) {
			try {
				getDao().delete(entidade);
				JOptionPane.showMessageDialog(frame, MSG_EXCLUIR_SUCESSO);
			} catch (final DataIntegrityViolationException ex) {
				exibirMensagemErro(MSG_DEPENDENCIA, frame);
			}
		}
	}

	public void salvar() {
		salvar(getUi());
	}

	public void salvar(final AppUI<T> ui) {
		final Component comp = ui.getFrame();
		try {
			ui.validar();
			getDao().save(ui.getEntidade());
			ui.iniciarDados();
			ui.limparComponentes();
			exibirMensagemSalvarSucesso(comp);
		} catch (final ValidacaoException ex) {
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
