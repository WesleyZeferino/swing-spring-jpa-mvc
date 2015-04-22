package br.com.arq.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
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
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnExcluir;
	private JPanel pnlBtns;

	public AppTable() {
		init();
	}

	private void init() {
		dados = ObservableCollections.observableList(new ArrayList<T>());
		tabela = new JTable();
		scroll = new JScrollPane();
		pnlPaginacao = new JPanel(new MigLayout());
		pnlBtns = new JPanel(new MigLayout());

		btnPrimeiro = new JButton(new ImageIcon(getClass().getResource("/icon/Symbol_Rewind.png")));
		btnAnterior = new JButton(new ImageIcon(getClass().getResource("/icon/Symbol_Play_Reversed.png")));
		btnProximo = new JButton(new ImageIcon(getClass().getResource("/icon/Symbol_Play.png")));
		btnUltimo = new JButton(new ImageIcon(getClass().getResource("/icon/Symbol_FastForward.png")));
		btnAtualizar = new JButton("Atualizar", new ImageIcon(getClass().getResource("/icon/refresh.png")));
		btnEditar = new JButton("Editar", new ImageIcon(getClass().getResource("/icon/Edit-Document-icon.png")));
		btnCancelar = new JButton("Sair", new ImageIcon(getClass().getResource("/icon/Logout.png")));
		btnExcluir = new JButton("Excluir", new ImageIcon(getClass().getResource("/icon/Delete.png")));
		lbPaginacao = new JLabel("(0 de 0)");

		pnlPaginacao.setBorder(new EtchedBorder());
		pnlBtns.setBorder(new EtchedBorder());

		pnlPaginacao.add(btnPrimeiro);
		pnlPaginacao.add(btnAnterior);
		pnlPaginacao.add(lbPaginacao);
		pnlPaginacao.add(btnProximo);
		pnlPaginacao.add(btnUltimo, "pushx");
		
		pnlBtns.add(btnAtualizar);
		pnlBtns.add(btnEditar);
		pnlBtns.add(btnExcluir);
		pnlBtns.add(btnCancelar);

		scroll.setViewportView(tabela);
	}

	public JPanel getPainelBtns() {
		return pnlBtns;
	}

	public T getItemSelecionado() {
		return dados.get(tabela.getSelectedRow());
	}

	public List<T> getItensSelecionados() {
		final List<T> itens = new ArrayList<T>();
		final int[] rows = tabela.getSelectedRows();

		for (final int row : rows) {
			itens.add(dados.get(row));
		}

		return itens;
	}

	public ColumnBinding bind(final BindingUtil bind) {
		return bind.addJTableBinding(dados, tabela);
	}

	public JComponent getComponente() {
		final JPanel painel = new JPanel(new MigLayout());
		painel.add(pnlBtns, "wrap, growx, pushx");
		painel.add(scroll, "wrap, grow, push");
		painel.add(pnlPaginacao, "wrap, growx, pushx");
		return painel;
	}

	public void setDados(final List<T> dados) {
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

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnExcluir() {
		return btnExcluir;
	}
}
