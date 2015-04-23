package br.com.arq.controller;

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;

import org.jdesktop.beansbinding.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.model.ContaBancaria;
import br.com.arq.model.TipoFolha;
import br.com.arq.ui.PrincipalUI;
import br.com.arq.util.BindingUtil;
import br.com.arq.util.ListasUteis;
import br.com.arq.util.NumberUtil;

@Component
public class PrincipalController {

	@Autowired
	private PrincipalUI ui;

	@Autowired
	private CadastrarFolhaController folhaController;

	@Autowired
	private CadastrarContaController contaController;

	@Autowired
	private CadastrarCategoriaController categoriaController;

	@Autowired
	private ImportarArquivosController ofxController;

	@Autowired
	private EmitirRelatorioController relatorioExtratoController;

	@Autowired
	private ListasUteis listas;

	@PostConstruct
	private void init() {
		listas.setCategorias(categoriaController.getDao().findAll());
		listas.setContas(contaController.getDao().findAll());

		final JComponent frCadFolha = (JComponent) folhaController.getUi().getFrame();
		final JComponent frCadConta = (JComponent) contaController.getUi().getFrame();
		final JComponent frCadCategoria = (JComponent) categoriaController.getUi().getFrame();
		final JComponent frExtrato = relatorioExtratoController.getUi().getFrame();

		ui.getMenuCadFolha().addActionListener(e -> abrirFrame(frCadFolha));
		ui.getMenuCadConta().addActionListener(e -> abrirFrame(frCadConta));
		ui.getMenuCadCategoria().addActionListener(e -> abrirFrame(frCadCategoria));
		ui.getMenuExtrato().addActionListener(e -> abrirFrame(frExtrato));
		ui.getCmbConta().addActionListener(e -> atualizarSaldo());

		folhaController.getUi().getBtnSair().addActionListener(e -> fecharFrame(frCadFolha));
		contaController.getUi().getBtnSair().addActionListener(e -> fecharFrame(frCadConta));
		categoriaController.getUi().getBtnSair().addActionListener(e -> fecharFrame(frCadCategoria));

		BindingUtil.create(new BindingGroup()).addJComboBoxBinding(listas.getContas(), ui.getCmbConta()).add(this, "${folhaController.contaSelecionada}", ui.getCmbConta(), "selectedItem").add(this, "${folhaController.listController.contaSelecionada}", ui.getCmbConta(), "selectedItem").add(this, "${ofxController.contaSelecionada}", ui.getCmbConta(), "selectedItem")
				.add(this, "${relatorioExtratoController.contaSelecionada}", ui.getCmbConta(), "selectedItem").getBindingGroup().bind();

		if (!listas.getContas().isEmpty()) {
			ui.getCmbConta().setSelectedIndex(0);
		}

		atualizarSaldo();
	}

	private void atualizarSaldo() {
		final ContaBancaria conta = (ContaBancaria) ui.getCmbConta().getSelectedItem();
		BigDecimal saldo = new BigDecimal(0);
		if (conta != null && conta.getId() != null) {
			BigDecimal despesa = folhaController.getDao().countBy(TipoFolha.DESPESA, conta.getId());
			BigDecimal receita = folhaController.getDao().countBy(TipoFolha.RECEITA, conta.getId());
			
			despesa = Optional.ofNullable(despesa).orElse(new BigDecimal(0));
			receita = Optional.ofNullable(receita).orElse(new BigDecimal(0));
			
			saldo = receita.subtract(despesa);
		}

		ui.getLbSaldo().setText(String.format("Saldo: %s", NumberUtil.obterNumeroFormatado(saldo)));
	}

	private void abrirFrame(final JComponent frame) {
		if (frame instanceof JInternalFrame) {
			final JInternalFrame internal = (JInternalFrame) frame;
			ui.getDesktop().add(internal);
			internal.show();
		}
	}

	private void fecharFrame(final JComponent frame) {
		if (frame instanceof JInternalFrame) {
			final JInternalFrame internal = (JInternalFrame) frame;
			internal.hide();
			ui.getDesktop().remove(internal);
		}
	}

	public PrincipalUI getFrame() {
		return ui;
	}

	public CadastrarFolhaController getFolhaController() {
		return folhaController;
	}

	public ImportarArquivosController getOfxController() {
		return ofxController;
	}

	public EmitirRelatorioController getRelatorioExtratoController() {
		return relatorioExtratoController;
	}

}
