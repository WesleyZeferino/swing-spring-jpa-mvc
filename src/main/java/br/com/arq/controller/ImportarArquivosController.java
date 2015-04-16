package br.com.arq.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import net.sf.ofx4j.domain.data.common.TransactionType;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.CategoriaDAO;
import br.com.arq.dao.ConfiguracaoDAO;
import br.com.arq.dao.ContaBancariaDAO;
import br.com.arq.dao.FolhaDAO;
import br.com.arq.dto.TransacaoDTO;
import br.com.arq.model.Categoria;
import br.com.arq.model.Configuracao;
import br.com.arq.model.ContaBancaria;
import br.com.arq.model.Folha;
import br.com.arq.model.StatusFolha;
import br.com.arq.model.TipoFolha;
import br.com.arq.model.TipoPagamento;
import br.com.arq.ui.ImportarArquivosUI;
import br.com.arq.util.BankingUtil;
import br.com.arq.util.DateUtil;
import br.com.arq.util.ListasUteis;
import br.com.arq.util.NumberUtil;
import br.com.arq.util.ObjetoUtil;
import br.com.arq.validation.ValidacaoException;

@Component
public class ImportarArquivosController extends AppController<Folha> {

	private static final String MSG_ERRO_SALVAR = "Alguns itens não poderam ser salvos!";

	@Autowired
	private FolhaDAO dao;

	@Autowired
	private CategoriaDAO categoriaDao;

	@Autowired
	private ContaBancariaDAO contaDao;

	@Autowired
	private ImportarArquivosUI ui;

	@Autowired
	private PrincipalController pric;

	@Autowired
	private ListasUteis listas;

	@Autowired
	private ConfiguracaoDAO confDao;

	private ContaBancaria contaSelecionada;
	private boolean isSalvo;
	private String erros = "";

	public void abrirFormularioEdicaoOfx(final java.awt.Component parent) {
		categorias = listas.getCategorias();

		try {
			ui.showChooser(obterDiretorioPadrao(), parent);

			final InputStream in = ui.getArquivo();
			final List<TransacaoDTO> dados = importarDadosArquivoOfx(in);

			ui.setTransacoes(dados);
			ui.setCategorias(categorias);
			ui.getFrame().setVisible(true);

			definirDiretorioPadrao();

		} catch (final ValidacaoException ex) {
			exibirMensagemErro(ex.getMessage(), ui.getFrame());
		} catch (final Exception e) {
			LOG.log(Level.SEVERE, null, e);
		}
	}

	public void abrirFormularioEdicaoXls(final java.awt.Component parent) {
		ui.showChooser(obterDiretorioPadrao(), parent);
		final List<Folha> entidades = new ArrayList<Folha>();

		importarArquivoXls(it -> {
			try {
				final Categoria categoria = categoriaDao.findAllByDescricao((String) it[0]);
				final ContaBancaria conta = contaDao.findAllByDescricao((String) it[1]);
				final Date dataLancamento = DateUtil.obterDataFormatada((String) it[2]);
				final Date dataPrevistaQuitacao = DateUtil.obterDataFormatada((String) it[3]);
				final Date dataQuitacao = DateUtil.obterDataFormatada((String) it[4]);
				final BigDecimal valor = NumberUtil.obterNumeroFormatado((String) it[9]);
				final Integer id = Integer.valueOf((Integer) it[10]);
				final Folha folha = new Folha(null, null, valor, conta, categoria, (String) it[5], null, dataQuitacao, dataPrevistaQuitacao, dataLancamento, id);

				entidades.add(folha);
			} catch (final Exception e) {
				LOG.log(Level.SEVERE, null, e);
			}
		}, ui.getArquivo());

		definirDiretorioPadrao();
	}

	@Override
	public void salvar() {
		final List<TransacaoDTO> transacoes = ui.getTransacoes();
		final List<TransacaoDTO> transacoesProblematicas = new ArrayList<TransacaoDTO>();

		transacoes.forEach(it -> {
			final Folha folha = new Folha();
			folha.setCategoria(it.getCategoria());
			folha.setConta(it.getConta());
			folha.setDataPrevistaQuitacao(it.getQuitacao());
			folha.setDataQuitacao(it.getQuitacao());
			folha.setValor(it.getValor());
			folha.setDescricao(it.getDescricao());
			folha.setTipo(it.getTipo());
			folha.setTipoPagamento(TipoPagamento.INDEFINIDO);
			folha.setStatusFolha(StatusFolha.QUITADO);

			try {
				validar(folha);
				dao.save(folha);
				isSalvo = true;
			} catch (final ValidacaoException ex) {
				erros = ex.getMessage();
				transacoesProblematicas.add(it);
			}
		});

		if (!erros.isEmpty() && isSalvo) {
			exibirMensagemAlerta(MSG_ERRO_SALVAR, ui.getFrame());
			transacoes.clear();
			transacoes.addAll(transacoesProblematicas);
		} else if (!erros.isEmpty()) {
			exibirMensagemErro(erros, ui.getFrame());
		} else {
			exibirMensagemSalvarSucesso(ui.getFrame());
			transacoes.clear();
			ui.getFrame().dispose();
		}

		isSalvo = false;
		erros = "";
	}

	private List<Categoria> categorias;

	@PostConstruct
	private void init() {
		ui.getBtnSalvar().addActionListener(e -> salvar());
		ui.getBtnCancelar().addActionListener(e -> ui.getModal().dispose());
	}

	private void importarArquivoXls(final Consumer<Object[]> action, final InputStream in) {
		try {
			final HSSFWorkbook workbook = new HSSFWorkbook(in);
			final HSSFSheet sheet = workbook.getSheetAt(0);
			final int lastRowNum = sheet.getLastRowNum();
			int indRow = 1;

			while (indRow < lastRowNum) {
				final HSSFRow row = sheet.getRow(indRow);
				final int lastCellNum = row.getLastCellNum();
				int indCell = 0;

				final Object[] elements = new Object[lastCellNum];

				while (indCell < lastCellNum) {
					final HSSFCell cell = row.getCell(indCell);
					final int type = cell.getCellType();

					if (type == Cell.CELL_TYPE_STRING) {
						elements[indCell] = cell.getStringCellValue();
					} else if (type == Cell.CELL_TYPE_NUMERIC) {
						elements[indCell] = cell.getNumericCellValue();
					}

					action.accept(elements);

					indCell++;
				}
				indRow++;
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private List<TransacaoDTO> importarDadosArquivoOfx(final InputStream in) {
		final List<TransacaoDTO> transacoes = new ArrayList<TransacaoDTO>();

		if (ObjetoUtil.isReferencia(in)) {
			BankingUtil.obterTransacoesArquivoOfx(in).forEach(it -> {
				final BigDecimal valor = new BigDecimal(it.getAmount()).setScale(2, RoundingMode.CEILING);
				final TipoFolha tipo = it.getTransactionType().equals(TransactionType.CREDIT) ? TipoFolha.RECEITA : TipoFolha.DESPESA;
				final TransacaoDTO dto = new TransacaoDTO(it.getMemo(), it.getDatePosted(), valor, contaSelecionada, tipo);

				if (!categorias.isEmpty()) {
					dto.setCategoria(categorias.get(0));
				}

				transacoes.add(dto);
			});
		}

		return transacoes;
	}

	private void definirDiretorioPadrao() {
		final String novoDir = ui.getChooser().getSelectedFile().getAbsolutePath();
		final Configuracao conf = confDao.obterConfiguracao();

		if (ObjetoUtil.isReferencia(conf)) {
			conf.setDiretorio(novoDir);
			confDao.save(conf);
		} else {
			confDao.save(new Configuracao(novoDir));
		}
	}

	private String obterDiretorioPadrao() {
		final Configuracao conf = confDao.obterConfiguracao();
		return Optional.ofNullable(conf).map(Configuracao::getDiretorio).orElse("/");
	}

	@Override
	public FolhaDAO getDao() {
		return dao;
	}

	@Override
	public ImportarArquivosUI getUi() {
		return ui;
	}

	public ContaBancaria getContaSelecionada() {
		return contaSelecionada;
	}

	public void setContaSelecionada(final ContaBancaria contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

}
