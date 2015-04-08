package br.com.arq.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.arq.dao.AppDAO;
import br.com.arq.model.Entidade;
import br.com.arq.util.AppTable;


public class PaginacaoController<T extends Entidade, D extends AppDAO<T>> {
	
	private static final int MAX_RESULTADOS = 5;
	private static final int PAGINA_INICIAL = 0;
	
	private D dao;
	private AppTable<T> tabela;
	private Page<T> pagina;
	
	public PaginacaoController(D dao, AppTable<T> tabela) {
		super();
		this.dao = dao;
		this.tabela = tabela;
		
		init();
	}

	private void init() {
		tabela.getBtnPrimeiro().addActionListener(e -> irPrimeiroRegistro());
		tabela.getBtnAnterior().addActionListener(e -> irRegistroAnterior());
		tabela.getBtnProximo().addActionListener(e -> irProximoRegistro());
		tabela.getBtnUltimo().addActionListener(e -> irUltimoRegistro());
		tabela.getBtnAtualizar().addActionListener(e -> atualizar());
	}
	
	private void atualizar() {
		Integer paginaAtual = Optional.ofNullable(pagina).map(Page::getNumber).orElse(0);
		tabela.setDados(buscarDados(paginaAtual));
	}

	private void irUltimoRegistro() {
		tabela.setDados(buscarDados(pagina.getTotalPages()));
	}

	private void irProximoRegistro() {
		if (pagina.hasNextPage()) {
			int prox = pagina.getNumber() + 1;
			tabela.setDados(buscarDados(prox));
		}
	}

	private void irRegistroAnterior() {
		if (pagina.hasPreviousPage()) {
			int ant = pagina.getNumber() - 1;
			tabela.setDados(buscarDados(ant));
		}
	}

	private void irPrimeiroRegistro() {
		tabela.setDados(buscarDados(PAGINA_INICIAL));
	}

	private List<T> buscarDados(int page) {
		pagina = dao.findAll(new PageRequest(page, MAX_RESULTADOS));
		validarBtns();
		tabela.getLbPaginacao().setText(String.format("(%s de %s)", pagina.getNumber() + 1, pagina.getTotalPages()));
		return pagina.getContent();
	}

	private void validarBtns() {
		tabela.getBtnPrimeiro().setEnabled(pagina.hasPreviousPage());
		tabela.getBtnAnterior().setEnabled(pagina.hasPreviousPage());
		tabela.getBtnProximo().setEnabled(pagina.hasNextPage());
		tabela.getBtnUltimo().setEnabled(pagina.hasNextPage());
	}
	
}
