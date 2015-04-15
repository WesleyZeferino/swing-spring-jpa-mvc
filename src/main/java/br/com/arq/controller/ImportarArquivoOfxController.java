package br.com.arq.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import net.sf.ofx4j.domain.data.common.TransactionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.ConfiguracaoDAO;
import br.com.arq.dao.FolhaDAO;
import br.com.arq.dto.TransacaoDTO;
import br.com.arq.model.Categoria;
import br.com.arq.model.Configuracao;
import br.com.arq.model.ContaBancaria;
import br.com.arq.model.Folha;
import br.com.arq.model.StatusFolha;
import br.com.arq.model.TipoFolha;
import br.com.arq.model.TipoPagamento;
import br.com.arq.ui.ImportarArquivoOfxUI;
import br.com.arq.util.BankingUtil;
import br.com.arq.util.ListasUteis;
import br.com.arq.util.ObjetoUtil;
import br.com.arq.validation.ValidacaoException;

@Component
public class ImportarArquivoOfxController extends AppController<Folha> {

	private static final String MSG_ERRO_SALVAR = "Alguns itens não poderam ser salvos!";

	@Autowired
	private FolhaDAO dao;

	@Autowired
	private ImportarArquivoOfxUI ui;

	@Autowired
	private PrincipalController pric;

	@Autowired
	private ListasUteis listas;

	@Autowired
	private ConfiguracaoDAO confDao;

	private ContaBancaria contaSelecionada;
	private boolean isSalvo;
	private String erros = "";

	@PostConstruct
	private void init() {
		ui.getBtnSalvar().addActionListener(e -> salvar());
		ui.getBtnCancelar().addActionListener(e -> ui.getModal().dispose());
	}

	public void importarDadosArquivoOfx(final java.awt.Component parent) {
		try {
			final Configuracao conf = confDao.obterConfiguracao();

			final String dir = Optional.ofNullable(conf).map(Configuracao::getDiretorio).orElse("/");
			ui.showChooser(dir, parent);
			final InputStream in = ui.getArquivo();

			if (ObjetoUtil.isReferencia(in)) {

				final List<TransacaoDTO> transacoes = new ArrayList<TransacaoDTO>();
				final List<Categoria> categorias = listas.getCategorias();

				BankingUtil.obterTransacoesArquivoOfx(in).forEach(it -> {
					final BigDecimal valor = new BigDecimal(it.getAmount()).setScale(2, RoundingMode.CEILING);
					final TipoFolha tipo = it.getTransactionType().equals(TransactionType.CREDIT) ? TipoFolha.RECEITA : TipoFolha.DESPESA;
					final TransacaoDTO dto = new TransacaoDTO(it.getMemo(), it.getDatePosted(), valor, contaSelecionada, tipo);

					if (!categorias.isEmpty()) {
						dto.setCategoria(categorias.get(0));
					}

					transacoes.add(dto);
				});

				ui.setTransacoes(transacoes);
				ui.setCategorias(categorias);
				ui.getFrame().setVisible(true);

				final String novoDir = ui.getChooser().getSelectedFile().getAbsolutePath();

				if (ObjetoUtil.isReferencia(conf)) {
					conf.setDiretorio(novoDir);
					confDao.save(conf);
				} else {
					confDao.save(new Configuracao(novoDir));
				}
			}
		} catch (final ValidacaoException ex) {
			exibirMensagemErro(ex.getMessage(), ui.getFrame());
		} catch (final Exception e) {
			LOG.log(Level.SEVERE, null, e);
		}
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

	@Override
	public FolhaDAO getDao() {
		return dao;
	}

	@Override
	public ImportarArquivoOfxUI getUi() {
		return ui;
	}

	public ContaBancaria getContaSelecionada() {
		return contaSelecionada;
	}

	public void setContaSelecionada(final ContaBancaria contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

}
