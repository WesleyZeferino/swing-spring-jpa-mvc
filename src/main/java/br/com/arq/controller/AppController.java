package br.com.arq.controller;

import java.awt.Component;
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
	protected static final String MSG_EXCLUIR_SUCESSO = "O item foi excluído com sucesso!";
	protected static final String MSG_EXCLUIR = "Quer excluir este item?";
	protected static final String MSG_SALVAR_SUCESSO = "Dados salvos com sucesso!";
	protected static final Logger LOG = Logger.getLogger(AppController.class.getName());

	protected abstract AppDAO<T> getDao();
	protected abstract AppUI<T> getUi();
	
	public void salvar() {
		salvar(getUi());
	}
	
	public void excluir() {
		excluir(getUi());
	}
	
	public void excluir(final AppUI<T> ui) {
		Component frame = ui.getFrame();
		
		int result = JOptionPane.showConfirmDialog(frame, MSG_EXCLUIR, TITULO_EXCLUSAO, JOptionPane.YES_NO_OPTION);
		
		if (result == JOptionPane.YES_OPTION) {
			try {
				getDao().delete(ui.getEntidade().getId());
				JOptionPane.showMessageDialog(frame, MSG_EXCLUIR_SUCESSO);
			} catch (DataIntegrityViolationException ex) {
				exibirMensagemErro(MSG_DEPENDENCIA, frame);
			}
		}
	}

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
