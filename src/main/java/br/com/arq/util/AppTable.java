package br.com.arq.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.observablecollections.ObservableCollections;
import org.springframework.stereotype.Component;

import br.com.arq.util.BindingUtil.ColumnBinding;

@Component
public class AppTable<T> {

	private List<T> dados;
	private JTable tabela;
	private JScrollPane scroll;
	private JButton btnPrimeiro;
	private JButton btnAnterior;
	private JButton btnProximo;
	private JButton btnUltimo;
	private JPanel pnlPaginacao;
	private JLabel lbPag;
	
	@PostConstruct
	private void init() {
		dados = ObservableCollections.observableList(new ArrayList<T>());
		tabela = new JTable();
		scroll = new JScrollPane();
		pnlPaginacao = new JPanel(new MigLayout());
		
		btnPrimeiro = new JButton("<<");
		btnAnterior = new JButton("<");
		btnProximo = new JButton(">");
		btnUltimo = new JButton(">>");
		lbPag = new JLabel("(0 de 0)");
		
		pnlPaginacao.setBorder(new EtchedBorder());
		
		btnPrimeiro.addActionListener(e -> irPrimeiroRegistro());
		btnAnterior.addActionListener(e -> irRegistroAnterior());
		btnProximo.addActionListener(e -> irProximoRegistro());
		btnUltimo.addActionListener(e -> irUltimoRegistro());
		
		pnlPaginacao.add(btnPrimeiro);
		pnlPaginacao.add(btnAnterior);
		pnlPaginacao.add(lbPag);
		pnlPaginacao.add(btnProximo);
		pnlPaginacao.add(btnUltimo);
		
		scroll.setViewportView(tabela);
	}
	
	private void irUltimoRegistro() {
		// TODO Auto-generated method stub
	}

	private void irProximoRegistro() {
		// TODO Auto-generated method stub
	}

	private void irRegistroAnterior() {
		// TODO Auto-generated method stub
	}

	private void irPrimeiroRegistro() {
		// TODO Auto-generated method stub
	}

	public ColumnBinding bind(final BindingUtil bind) {
		return bind.addJTableBinding(dados, tabela);
	}
	
	public JComponent getComponente() {
		JPanel painel = new JPanel(new MigLayout());
		painel.add(scroll, "wrap");
		painel.add(pnlPaginacao, "wrap");
		return painel;
	}
	
	public void setDados(List<T> dados) {
		this.dados.clear();
		this.dados.addAll(dados);
	}
	
	public List<T> getDados() {
		return dados;
	}
	
	public JTable getTabela() {
		return tabela;
	}
}
