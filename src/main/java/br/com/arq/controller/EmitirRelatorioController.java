package br.com.arq.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.PostConstruct;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import org.jdesktop.beansbinding.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.converter.DateConverter;
import br.com.arq.dao.FolhaDAO;
import br.com.arq.model.ContaBancaria;
import br.com.arq.model.Folha;
import br.com.arq.model.StatusFolha;
import br.com.arq.model.TipoFolha;
import br.com.arq.ui.EmitirRelatorioUI;
import br.com.arq.util.BindingUtil;
import br.com.arq.util.ObjetoUtil;

@Component
public class EmitirRelatorioController extends AppController<Folha> {

	private static final String RELATORIO_FOLHA_JASPER = "/relatorios/relatorio_folha.jasper";

	@Autowired
	private FolhaDAO dao;

	@Autowired
	private EmitirRelatorioUI ui;

	private ContaBancaria contaSelecionada;
	private Date dataInicio;
	private Date dataFim;

	@PostConstruct
	private void init() {
		ui.getBtnGerar().addActionListener(e -> gerarRelatorio());

		final DateConverter dtConverter = new DateConverter();
		final BindingUtil binding = BindingUtil.create(new BindingGroup());
		List<StatusFolha> listaStatus = new ArrayList<StatusFolha>();
		listaStatus.add(null);
		listaStatus.addAll(Arrays.asList(StatusFolha.values()));
		
		binding.addJComboBoxBinding(listaStatus, ui.getCmbStatus());
		binding.add(this, "${dataInicio}", ui.getTxtDataInicio(), dtConverter);
		binding.add(this, "${dataFim}", ui.getTxtDataFim(), dtConverter);
		binding.getBindingGroup().bind();
	}

	private void gerarRelatorio() {
		final Integer idConta = contaSelecionada.getId();
		List<Folha> dados = null;
		final StatusFolha status = (StatusFolha) ui.getCmbStatus().getSelectedItem();

		if (ObjetoUtil.isReferencia(dataInicio, dataFim, status)) {
			dados = dao.findAll(idConta, dataInicio, dataFim, status);
		} else if (ObjetoUtil.isReferencia(dataInicio, dataFim)) {
			dados = dao.findAll(idConta, dataInicio, dataFim);
		} else {
			dados = dao.findAll(idConta);
		}

		final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dados);
		final InputStream in = getClass().getResourceAsStream(RELATORIO_FOLHA_JASPER);
		final HashMap<String, Object> parametros = new HashMap<String, Object>();
		JasperPrint print = null;

		BigDecimal despesa = new BigDecimal(0);
		BigDecimal receita = new BigDecimal(0);

		for (final Folha f : dados) {
			if (f.getTipo().equals(TipoFolha.DESPESA)) {
				despesa = despesa.add(f.getValor());
			} else {
				receita = receita.add(f.getValor());
			}
		}

		final BigDecimal saldo = receita.subtract(despesa);

		parametros.put("DESPESA", despesa);
		parametros.put("RECEITA", receita);
		parametros.put("SALDO", saldo);
		parametros.put("CONTA", contaSelecionada.getDescricao());

		try {
			print = JasperFillManager.fillReport(in, parametros, dataSource);
		} catch (final JRException e) {
			LOG.log(Level.SEVERE, "Erro ao tentar emitir relatório", e);
		}

		final JasperViewer viewer = new JasperViewer(print, false);
		viewer.setVisible(true);
	}

	@Override
	public FolhaDAO getDao() {
		return dao;
	}

	@Override
	public EmitirRelatorioUI getUi() {
		return ui;
	}

	public ContaBancaria getContaSelecionada() {
		return contaSelecionada;
	}

	public void setContaSelecionada(final ContaBancaria contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(final Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(final Date dataFim) {
		this.dataFim = dataFim;
	}

}
