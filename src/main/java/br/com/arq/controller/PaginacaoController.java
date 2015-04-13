package br.com.arq.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.arq.model.Entidade;
import br.com.arq.util.AppTable;

@Component
public abstract class PaginacaoController<T extends Entidade> extends AppController<T> {

	protected static final int MAX_RESULTADOS = 15;
	private static final int PAGINA_INICIAL = 0;

	private Page<T> pagina;

	@PostConstruct
	private void init() {
		getTabela().getBtnPrimeiro().addActionListener(e -> irPrimeiroRegistro());
		getTabela().getBtnAnterior().addActionListener(e -> irRegistroAnterior());
		getTabela().getBtnProximo().addActionListener(e -> irProximoRegistro());
		getTabela().getBtnUltimo().addActionListener(e -> irUltimoRegistro());
		getTabela().getBtnAtualizar().addActionListener(e -> atualizar());
	}

	protected void atualizar() {
		final Integer paginaAtual = Optional.ofNullable(pagina).map(Page::getNumber).orElse(0);
		getTabela().setDados(buscarDados(paginaAtual));
	}

	private void irUltimoRegistro() {
		getTabela().setDados(buscarDados(pagina.getTotalPages() - 1));
	}

	private void irProximoRegistro() {
		if (pagina.hasNext()) {
			final int prox = pagina.getNumber() + 1;
			getTabela().setDados(buscarDados(prox));
		}
	}

	private void irRegistroAnterior() {
		if (pagina.hasPrevious()) {
			final int ant = pagina.getNumber() - 1;
			getTabela().setDados(buscarDados(ant));
		}
	}

	private void irPrimeiroRegistro() {
		getTabela().setDados(buscarDados(PAGINA_INICIAL));
	}

	private List<T> buscarDados(final int page) {
		pagina = obterDados(page);
		validarBtns();
		getTabela().getLbPaginacao().setText(String.format("(%s de %s)", pagina.getNumber() + 1, pagina.getTotalPages()));
		return pagina.getContent();
	}

	protected Page<T> obterDados(final int page) {
		return getDao().findAll(new PageRequest(page, MAX_RESULTADOS));
	}

	private void validarBtns() {
		getTabela().getBtnPrimeiro().setEnabled(pagina.hasPrevious());
		getTabela().getBtnAnterior().setEnabled(pagina.hasPrevious());
		getTabela().getBtnProximo().setEnabled(pagina.hasNext());
		getTabela().getBtnUltimo().setEnabled(pagina.hasNext());
	}

	public abstract AppTable<T> getTabela();

}
