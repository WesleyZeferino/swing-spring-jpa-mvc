package br.com.arq.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.dao.CategoriaDAO;
import br.com.arq.dao.FolhaDAO;
import br.com.arq.model.ContaBancaria;
import br.com.arq.model.Folha;
import br.com.arq.ui.CadastrarFolhaUI;
import br.com.arq.util.CalcularRecorrencia;
import br.com.arq.util.ObjetoUtil;
import br.com.arq.validation.ValidacaoException;

@Component
public class CadastrarFolhaController extends AppController<Folha> {

	@Autowired
	private FolhaDAO dao;

	@Autowired
	private CategoriaDAO categoriaDAO;

	@Autowired
	private CadastrarFolhaUI ui;

	@Autowired
	private ListarFolhaController listController;

	@Autowired
	private CalcularRecorrencia calcRecorrencia;

	private ContaBancaria contaSelecionada;

	@PostConstruct
	private void init() {
		ui.getBtnSalvar().addActionListener(e -> {
			ui.getFolha().setConta(contaSelecionada);
			
			if (ObjetoUtil.isReferencia(ui.getFolhaPai())) {
				salvarDetalhamento();
			} else if (ObjetoUtil.isReferencia(ui.getEntidade().getId())) {
				salvar(ui);
				ui.resetarCombos();
			} else {
				try {
					ui.validar();
					ui.getDialogRecorrencia().setVisible(true);
				} catch (final ValidacaoException ex) {
					exibirMensagemErro(ex.getMessage(), ui.getFrame());
				}
			}
		});

		ui.getBtnListar().addActionListener(e -> {
			listController.atualizar();
			listController.getUi().show();
		});
		
		ui.getBtnLimpar().addActionListener(e -> {
			ui.iniciarDados();
			ui.limparComponentes();
		});

		ui.getBtnSalvarRec().addActionListener(e -> salvarRec());

		ui.getBtnCancelarRec().addActionListener(e -> ui.getDialogRecorrencia().dispose());
		
		ui.resetarCombos();
	}

	private void salvarDetalhamento() {
		try {
			Folha pai = ui.getFolhaPai();
			BigDecimal valorPai = pai.getValor();
			BigDecimal valorFilho = ui.getEntidade().getValor();
			
			ui.validar();
			validarDetalhamento(valorPai, valorFilho);
			
			pai.setValor(valorPai.subtract(valorFilho));
			ui.getEntidade().setPai(pai);
			
			dao.save(ui.getEntidade());
			dao.save(pai);
			
			ui.iniciarDados();
			ui.limparComponentes();
			ui.resetarCombos();
			
			exibirMensagemSalvarSucesso(ui.getFrame());
		} catch (ValidacaoException ex) {
			exibirMensagemErro(ex.getMessage(), ui.getFrame());
		}
	}

	private void validarDetalhamento(BigDecimal valorPai, BigDecimal valorFilho) {
		int compare = valorFilho.compareTo(valorPai);
		
		if (compare <= 1) {
			throw new ValidacaoException("O valor da segunta folha não pode ser igual ou superior a da primeira.");
		}
	}

	private void salvarRec() {
		final Date dataPrevista = ui.getEntidade().getDataPrevistaQuitacao();
		final Date dataBase = Optional.ofNullable(dataPrevista).orElse(ui.getEntidade().getDataQuitacao());
		final List<Date> datas = calcRecorrencia.gerarDatasRecorrentes(ui.getFrequencia(), dataBase, ui.getRepeticaoFreq());

		datas.forEach(data -> {
			final Folha folha = new Folha();

			try {
				PropertyUtils.copyProperties(folha, ui.getEntidade());
			} catch (final Exception e) {
				LOG.log(Level.SEVERE, null, e);
			}

			folha.setDataPrevistaQuitacao(data);
			folha.setTotalParcela(datas.size());
			folha.setParcela(datas.indexOf(data) + 1);

			dao.save(folha);
		});

		ui.iniciarDados();
		ui.limparComponentes();
		ui.resetarCombos();
		ui.getDialogRecorrencia().setVisible(false);

		exibirMensagemSalvarSucesso(ui.getFrame());

	}

	public CadastrarFolhaUI getUi() {
		return ui;
	}

	public void setContaSelecionada(final ContaBancaria contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

	public ContaBancaria getContaSelecionada() {
		return contaSelecionada;
	}

	@Override
	public FolhaDAO getDao() {
		return dao;
	}

}
