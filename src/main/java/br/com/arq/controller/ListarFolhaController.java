package br.com.arq.controller;

import javax.annotation.PostConstruct;

import org.jdesktop.beansbinding.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.converter.BigDecimalConverter;
import br.com.arq.converter.DateConverter;
import br.com.arq.dao.FolhaDAO;
import br.com.arq.model.Folha;
import br.com.arq.ui.ListarFolhaUI;
import br.com.arq.util.AppTable;
import br.com.arq.util.BindingUtil;

@Component
public class ListarFolhaController extends PaginacaoController<Folha> {

	@Autowired
	private FolhaDAO dao;

	@Autowired
	private ListarFolhaUI ui;
	
	@Autowired
	private CadastrarFolhaController cadController;
	
	@PostConstruct
	private void init() {
		AppTable<Folha> tabela = ui.getTabela();
		
		BindingUtil.create(new BindingGroup())
		.add(tabela.getTabela(), "${selectedElement.tipo}", cadController.getUi().getCmbTipoFolha(), "selectedItem")
		.add(tabela.getTabela(), "${selectedElement.categoria}", cadController.getUi().getCmbCategoria(), "selectedItem")
		.add(tabela.getTabela(), "${selectedElement.statusFolha}", cadController.getUi().getCmbStatus(), "selectedItem")
		.add(tabela.getTabela(), "${selectedElement.tipoPagamento}", cadController.getUi().getCmbTipoPagamento(), "selectedItem")
		.add(tabela.getTabela(), "${selectedElement.descricao}", cadController.getUi().getTxtDescricao())
		.add(tabela.getTabela(), "${selectedElement.dataPrevistaQuitacao}", cadController.getUi().getTxtPrevQuitacao(), new DateConverter())
		.add(tabela.getTabela(), "${selectedElement.dataQuitacao}", cadController.getUi().getTxtQuitacao(), new DateConverter())
		.add(tabela.getTabela(), "${selectedElement.valor}", cadController.getUi().getTxtValor(), new BigDecimalConverter())
//		.add(tabela.getTabela(), "${selectedElement.tipo}", cadController.getUi().get)
		.getBindingGroup().bind();
		
		tabela.getBtnEditar().addActionListener(e -> {
			cadController.getUi().setFolha(tabela.getItemSelecionado());
			ui.getFrame().dispose();
		});
	}
	
	@Override
	public AppTable<Folha> getTabela() {
		return ui.getTabela();
	}
	
	public ListarFolhaUI getUi() {
		return ui;
	}
	
	public FolhaDAO getDao() {
		return dao;
	}
	
	public CadastrarFolhaController getCadController() {
		return cadController;
	}

}
