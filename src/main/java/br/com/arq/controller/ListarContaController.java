package br.com.arq.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.ContaBancariaDAO;
import br.com.arq.model.ContaBancaria;
import br.com.arq.ui.CadastrarContaUI;
import br.com.arq.ui.ListarContaUI;
import br.com.arq.util.AppTable;

@Component
public class ListarContaController extends PaginacaoController<ContaBancaria> {

	@Autowired
	private ContaBancariaDAO dao;
	
	@Autowired
	private ListarContaUI ui;
	
	@Autowired
	private CadastrarContaUI cadUi;
	
	@PostConstruct
	private void init() {
		ui.getTabela().getBtnCancelar().addActionListener(e -> fecharModal());
		
		ui.getTabela().getBtnEditar().addActionListener(e -> {
			ContaBancaria conta = ui.getTabela().getItemSelecionado();
			cadUi.setEntidade(conta);
			cadUi.getTxtDescricao().setText(conta.getDescricao());
			fecharModal();
		});
		
		ui.getTabela().getBtnExcluir().addActionListener(e -> {
			excluir(ui.getTabela().getItensSelecionados());
			atualizar();
		});
	}
	
	private void fecharModal() {
		ui.getFrame().setVisible(false);
	}
	
	@Override
	public AppTable<ContaBancaria> getTabela() {
		return ui.getTabela();
	}
	
	public ContaBancariaDAO getDao() {
		return dao;
	}
	
	public ListarContaUI getUi() {
		return ui;
	}

}
