package br.com.arq.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.observablecollections.ObservableCollections;

import br.com.arq.util.BindingUtil.ColumnBinding;

public class AppTable<T> {

	private List<T> dados;
	private JTable tabela;
	private JScrollPane scroll;
	private JButton btnPrimeiro;
	private JButton btnAnterior;
	private JButton btnProximo;
	private JButton btnUltimo;
	private JPanel pnlPaginacao;
	private JLabel lbPaginacao;
	private JButton btnAtualizar;
	
	public AppTable() {
		init();
	}
	
	private void init() {
		dados = ObservableCollections.observableList(new ArrayList<T>());
		tabela = new JTable();
		scroll = new JScrollPane();
		pnlPaginacao = new JPanel(new MigLayout());
		
		btnPrimeiro = new JButton("<<");
		btnAnterior = new JButton("<");
		btnProximo = new JButton(">");
		btnUltimo = new JButton(">>");
		btnAtualizar = new JButton("Atualizar");
		lbPaginacao = new JLabel("(0 de 0)");
		
		pnlPaginacao.setBorder(new EtchedBorder());
		
		pnlPaginacao.add(btnPrimeiro);
		pnlPaginacao.add(btnAnterior);
		pnlPaginacao.add(lbPaginacao);
		pnlPaginacao.add(btnProximo);
		pnlPaginacao.add(btnUltimo, "pushx");
		pnlPaginacao.add(btnAtualizar);
		
		scroll.setViewportView(tabela);
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
	
	public JButton getBtnAtualizar() {
		return btnAtualizar;
	}

	public JButton getBtnPrimeiro() {
		return btnPrimeiro;
	}

	public JButton getBtnAnterior() {
		return btnAnterior;
	}

	public JButton getBtnProximo() {
		return btnProximo;
	}

	public JButton getBtnUltimo() {
		return btnUltimo;
	}
	
	public JLabel getLbPaginacao() {
		return lbPaginacao;
	}
}
