package br.com.arq.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.CategoriaDAO;
import br.com.arq.model.Categoria;
import br.com.arq.ui.CadastrarCategoriaUI;
import br.com.arq.ui.ListarCategoriaUI;
import br.com.arq.util.AppTable;

@Component
public class ListarCategoriaController extends PaginacaoController<Categoria> {

	@Autowired
	private CategoriaDAO dao;
	
	@Autowired
	private ListarCategoriaUI ui;
	
	@Autowired
	private CadastrarCategoriaUI cadUi;
	
	@PostConstruct
	private void init() {
		ui.getTabela().getBtnCancelar().addActionListener(e -> fecharModal());
		
		ui.getTabela().getBtnEditar().addActionListener(e -> {
			Categoria cat = ui.getTabela().getItemSelecionado();
			cadUi.setEntidade(cat);
			cadUi.getTxtDescricao().setText(cat.getDescricao());
			fecharModal();
		});
		
		ui.getTabela().getBtnExcluir().addActionListener(e -> {
			excluir(ui.getTabela().getItensSelecionados());
			atualizar();
		});
	}

	private void fecharModal() {
		ui.getFrame().dispose();
	}
	
	public void exibirModal() {
		atualizar();
		ui.getFrame().setVisible(true);
	}
	
	public ListarCategoriaUI getUi() {
		return ui;
	}
	
	public CategoriaDAO getDao() {
		return dao;
	}

	@Override
	public AppTable<Categoria> getTabela() {
		return ui.getTabela();
	}
}
