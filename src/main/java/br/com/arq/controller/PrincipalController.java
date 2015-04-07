package br.com.arq.controller;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;

import org.jdesktop.beansbinding.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.ui.PrincipalUI;
import br.com.arq.util.BindingUtil;
import br.com.arq.util.ListasUteis;

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
	private ListasUteis listas;
	
	@PostConstruct
	private void init() {
		listas.setCategorias(categoriaController.getDao().findAll());
		listas.setContas(contaController.getDao().findAll());
		
		JComponent frCadFolha = (JComponent) folhaController.getUi().getFrame();
		JComponent frCadConta = (JComponent) contaController.getUi().getFrame();
		JComponent frCadCategoria = (JComponent) categoriaController.getUi().getFrame();
		
		ui.getMenuCadFolha().addActionListener(e -> abrirFrame(frCadFolha));
		ui.getMenuCadConta().addActionListener(e -> abrirFrame(frCadConta));
		ui.getMenuCadCategoria().addActionListener(e -> abrirFrame(frCadCategoria));
		
		folhaController.getUi().getBtnSair().addActionListener(e -> fecharFrame(frCadFolha));
		contaController.getUi().getBtnSair().addActionListener(e -> fecharFrame(frCadConta));
		categoriaController.getUi().getBtnSair().addActionListener(e -> fecharFrame(frCadCategoria));
	
		BindingUtil.create(new BindingGroup())
		.addJComboBoxBinding(listas.getContas(), ui.getCmbConta())
		.add(this, "${folhaController.contaSelecionada}", ui.getCmbConta(), "selectedItem")
		.getBindingGroup().bind();
	}
	
	private void abrirFrame(JComponent frame) {
		if (frame instanceof JInternalFrame) {
			JInternalFrame internal = (JInternalFrame) frame;
			ui.getDesktop().add(internal);
			internal.show();
		}
	}
	
	private void fecharFrame(JComponent frame) {
		if (frame instanceof JInternalFrame) {
			JInternalFrame internal = (JInternalFrame) frame;
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
	
}
