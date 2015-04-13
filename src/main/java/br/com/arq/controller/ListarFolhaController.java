package br.com.arq.controller;

import java.math.BigDecimal;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdesktop.beansbinding.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.converter.BigDecimalConverter;
import br.com.arq.converter.DateConverter;
import br.com.arq.dao.FolhaDAO;
import br.com.arq.model.ContaBancaria;
import br.com.arq.model.Folha;
import br.com.arq.ui.CadastrarFolhaUI;
import br.com.arq.ui.ListarFolhaUI;
import br.com.arq.util.AppTable;
import br.com.arq.util.BindingUtil;
import br.com.arq.util.NumberUtil;
import br.com.arq.validation.ValidacaoException;

@Component
public class ListarFolhaController extends PaginacaoController<Folha> {

	@Autowired
	private FolhaDAO dao;

	@Autowired
	private ListarFolhaUI ui;

	@Autowired
	private CadastrarFolhaController cadController;

	private ContaBancaria contaSelecionada;

	@PostConstruct
	private void init() {
		final AppTable<Folha> tabela = ui.getTabela();

		tabela.getBtnEditar().addActionListener(e -> {
			getDadosTabela();
			ui.getFrame().dispose();
		});

		tabela.getBtnCancelar().addActionListener(e -> {
			cancelar();
		});

		tabela.getBtnExcluir().addActionListener(e -> {
			excluir(tabela.getItensSelecionados());
			atualizar();
		});

		ui.getBtnDetalhar().addActionListener(e -> ui.getModalDetalhe().setVisible(true));

		ui.getBtnCancelarDetalhe().addActionListener(e -> {
			ui.getModalDetalhe().dispose();
		});

		ui.getBtnSalvarDetalhe().addActionListener(e -> {
			try {
				salvarDetalhe();
				ui.limparComponentes(ui.getModalDetalhe().getContentPane());
				ui.getModalDetalhe().dispose();
				exibirMensagemSalvarSucesso(ui.getFrame());
				atualizar();
			} catch (final ValidacaoException ex) {
				exibirMensagemErro(ex.getMessage(), ui.getFrame());
			}
		});

		bind();
	}

	private void bind() {
		final AppTable<Folha> tabela = ui.getTabela();
		final BindingUtil binding = BindingUtil.create(new BindingGroup());
		binding.add(tabela.getTabela(), "${selectedElement != null}", tabela.getBtnEditar(), "enabled");
		binding.add(tabela.getTabela(), "${selectedElement != null}", tabela.getBtnExcluir(), "enabled");
		binding.add(tabela.getTabela(), "${selectedElement != null}", ui.getBtnDetalhar(), "enabled");
		binding.getBindingGroup().bind();
	}

	private void salvarDetalhe() {
		final AppTable<Folha> tabela = ui.getTabela();
		final Folha folhaPai = tabela.getItemSelecionado();
		final Folha folhaFilho = new Folha();
		final String valorDetalhe = ui.getTxtDetalhe().getText();

		try {
			PropertyUtils.copyProperties(folhaFilho, folhaPai);
			folhaFilho.setId(null);
		} catch (final Exception ex) {
			LOG.log(Level.SEVERE, null, ex);
		}

		final BigDecimal valorFilho = NumberUtil.obterNumeroFormatado(valorDetalhe);
		final BigDecimal valorPai = folhaPai.getValor();

		validarDetalhamento(valorPai, valorFilho);

		folhaPai.setValor(valorPai.subtract(valorFilho));
		folhaFilho.setValor(valorFilho);
		folhaFilho.setPai(folhaPai);

		dao.save(folhaPai);
		dao.save(folhaFilho);
	}

	private void validarDetalhamento(final BigDecimal valorPai, final BigDecimal valorFilho) {
		final int compare = valorFilho.compareTo(valorPai);

		if (compare >= 0) {
			throw new ValidacaoException("O valor da segunta folha não pode ser igual ou superior a da primeira.");
		}
	}

	private void getDadosTabela() {
		final Folha folha = ui.getTabela().getItemSelecionado();
		final CadastrarFolhaUI uiCad = cadController.getUi();
		final DateConverter dateConverter = new DateConverter();

		uiCad.setFolha(folha);
		uiCad.getCmbTipoFolha().setSelectedItem(folha.getTipo());
		uiCad.getCmbCategoria().setSelectedItem(folha.getCategoria());
		uiCad.getCmbStatus().setSelectedItem(folha.getStatusFolha());
		uiCad.getCmbTipoPagamento().setSelectedItem(folha.getTipoPagamento());
		uiCad.getTxtDescricao().setText(folha.getDescricao());
		uiCad.getTxtPrevQuitacao().setText(dateConverter.convertForward(folha.getDataPrevistaQuitacao()));
		uiCad.getTxtQuitacao().setText(dateConverter.convertForward(folha.getDataQuitacao()));
		uiCad.getTxtValor().setText(new BigDecimalConverter().convertForward(folha.getValor()));

	}

	private void cancelar() {
		ui.getFrame().dispose();
		cadController.getUi().limparComponentes();
		cadController.getUi().resetarCombos();
	}

	@Override
	public AppTable<Folha> getTabela() {
		return ui.getTabela();
	}

	@Override
	public ListarFolhaUI getUi() {
		return ui;
	}

	@Override
	public FolhaDAO getDao() {
		return dao;
	}

	public ContaBancaria getContaSelecionada() {
		return contaSelecionada;
	}

	public void setContaSelecionada(final ContaBancaria contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

}
