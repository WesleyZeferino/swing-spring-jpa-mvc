package br.com.arq.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.jdesktop.beansbinding.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.converter.BigDecimalConverter;
import br.com.arq.converter.DateConverter;
import br.com.arq.dao.FolhaDAO;
import br.com.arq.model.ContaBancaria;
import br.com.arq.model.Folha;
import br.com.arq.model.StatusFolha;
import br.com.arq.model.TipoPagamento;
import br.com.arq.ui.CadastrarFolhaUI;
import br.com.arq.ui.ListarFolhaUI;
import br.com.arq.util.AppTable;
import br.com.arq.util.BindingUtil;
import br.com.arq.util.NumberUtil;
import br.com.arq.util.XlsBuilder;
import br.com.arq.util.XlsBuilder.CellBuilder;
import br.com.arq.validation.ValidacaoException;

@Component
public class ListarFolhaController extends PaginacaoController<Folha> {

	private static final String SEPARATOR = File.separator;

	private static final String MSG_QUITAR_ITENS = "Selecione itens que ainda não estejam quitados!";

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

		ui.getBtnQuitar().addActionListener(e -> {
			ui.getModalQuitacao().setVisible(true);
		});

		ui.getBtnCancelarQuitacao().addActionListener(e -> ui.getModalQuitacao().dispose());

		ui.getBtnSalvarQuitacao().addActionListener(e -> quitarFolhas());

		ui.getBtnExportar().addActionListener(e -> {
			final JFileChooser chooser = ui.getChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			final int result = chooser.showSaveDialog(ui.getFrame());

			if (result == JFileChooser.APPROVE_OPTION) {
				final String nomeArquivo = new SimpleDateFormat("yyyyMMdd").format(new Date()).concat(".xls");
				final File file = new File(chooser.getSelectedFile().getPath().concat(SEPARATOR).concat(nomeArquivo));
				gerarArquivoXls(file);
			}
		});

		bind();
	}

	private void gerarArquivoXls(final File diretorio) {
		final List<Folha> dados = dao.findAll();
		final DateConverter dateConverter = new DateConverter();
		final XlsBuilder build = XlsBuilder.create();
		final CellBuilder row = build.addRow(0);

		final HSSFWorkbook workbook = build.getWorkbook();

		final HSSFFont font = workbook.createFont();
		font.setBold(true);

		final HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);

		row.addCell(0, "Categoria", style);
		row.addCell(1, "Conta", style);
		row.addCell(2, "Lançamento", style);
		row.addCell(3, "Data Prev. Quitação", style);
		row.addCell(4, "Data Quitação", style);
		row.addCell(5, "Descrição", style);
		row.addCell(6, "Status", style);
		row.addCell(7, "Tipo", style);
		row.addCell(8, "Pagamento", style);
		row.addCell(9, "Valor", style);
		row.addCell(10, "ID", style);

		dados.forEach(it -> {
			final CellBuilder cell = build.addRow(dados.indexOf(it) + 1);

			cell.addCell(0, it.getCategoria().getDescricao());
			cell.addCell(1, it.getConta().getDescricao());
			cell.addCell(2, dateConverter.convertForward(it.getDataLancamento()));
			cell.addCell(3, dateConverter.convertForward(it.getDataPrevistaQuitacao()));
			cell.addCell(4, dateConverter.convertForward(it.getDataQuitacao()));
			cell.addCell(5, it.getDescricao());
			cell.addCell(6, it.getStatusFolha().getDescricao());
			cell.addCell(7, it.getTipo().getDescricao());
			cell.addCell(8, it.getTipoPagamento().getDescricao());
			cell.addCell(9, it.getValor().doubleValue());
			cell.addCell(10, it.getId());
		});

		try {
			final OutputStream out = new FileOutputStream(diretorio);
			workbook.write(out);
			out.flush();
			out.close();
		} catch (final Exception ex) {
			LOG.log(Level.SEVERE, "Erro ao gerar xls", ex);
		}

	}

	private void quitarFolhas() {
		final AppTable<Folha> tabela = ui.getTabela();
		boolean isSalvo = false;

		for (final Folha it : tabela.getItensSelecionados()) {
			if (!it.getStatusFolha().equals(StatusFolha.QUITADO)) {
				it.setDataQuitacao(new Date());
				it.setStatusFolha(StatusFolha.QUITADO);
				it.setTipoPagamento((TipoPagamento) ui.getCmbPagamento().getSelectedItem());

				dao.save(it);

				isSalvo = true;
			}
		}

		final JDialog modal = ui.getModalQuitacao();

		if (isSalvo) {
			exibirMensagemSalvarSucesso(modal);
			modal.dispose();
			atualizar();
		} else {
			exibirMensagemErro(MSG_QUITAR_ITENS, modal);
			modal.dispose();
		}
	}

	private void bind() {
		final AppTable<Folha> tabela = ui.getTabela();
		final BindingUtil binding = BindingUtil.create(new BindingGroup());
		binding.add(tabela.getTabela(), "${selectedElement != null}", tabela.getBtnEditar(), "enabled");
		binding.add(tabela.getTabela(), "${selectedElement != null}", tabela.getBtnExcluir(), "enabled");
		binding.add(tabela.getTabela(), "${selectedElement != null}", ui.getBtnDetalhar(), "enabled");
		binding.add(tabela.getTabela(), "${selectedElement != null}", ui.getBtnQuitar(), "enabled");
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
