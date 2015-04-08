package br.com.arq.controller;

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
import br.com.arq.ui.ListarFolhaUI;
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
	private ListarFolhaUI listUi;
	
	@Autowired
	private CalcularRecorrencia calcRecorrencia;
	
	private ContaBancaria contaSelecionada;
	
	@PostConstruct
	private void init() {
		ui.getBtnSalvar().addActionListener(e -> {
			ui.getFolha().setConta(contaSelecionada);
			
			if (ObjetoUtil.isReferencia(ui.getEntidade().getId())) {
				salvar(ui);
			} else {
				try {
					ui.validar();
					ui.getDialogRecorrencia().setVisible(true);
				} catch (ValidacaoException ex) {
					exibirMensagemErro(ex.getMessage(), ui.getFrame());
				}
			}
			
		});
		
		ui.getBtnListar().addActionListener(e -> {
//			listUi.getTabela().setDados(dao.findAll());
			listUi.show();
		});
		
		ui.getBtnSalvarRec().addActionListener(e -> salvarRec());
		
		ui.getBtnCancelarRec().addActionListener(e -> ui.getDialogRecorrencia().dispose());
	}
	
	private void salvarRec() {
		Date dataPrevista = ui.getEntidade().getDataPrevistaQuitacao();
		Date dataBase = Optional.ofNullable(dataPrevista).orElse(ui.getEntidade().getDataQuitacao());
		List<Date> datas = calcRecorrencia.gerarDatasRecorrentes(ui.getFrequencia(), dataBase, ui.getRepeticaoFreq());
		
		datas.forEach(data -> {
			Folha folha = new Folha();
			
			try {
				PropertyUtils.copyProperties(folha, ui.getEntidade());
			} catch (Exception e) {
				LOG.log(Level.SEVERE, null, e);
			}
			
			folha.setDataPrevistaQuitacao(data);
			folha.setTotalParcela(datas.size());
			
			dao.save(folha);
		});
		
		ui.iniciarDados();
		ui.limparComponentes();
		ui.getDialogRecorrencia().setVisible(false);
		
		exibirMensagemSalvarSucesso(ui.getFrame());
			
	}
	
	public CadastrarFolhaUI getUi() {
		return ui;
	}

	public void setContaSelecionada(ContaBancaria contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}
	
	public ContaBancaria getContaSelecionada() {
		return contaSelecionada;
	}
	
	public FolhaDAO getDao() {
		return dao;
	}

}