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
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnExcluir;

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
		btnEditar = new JButton("Editar");
		btnCancelar = new JButton("Cancelar");
		btnExcluir = new JButton("Excluir");
		lbPaginacao = new JLabel("(0 de 0)");

		pnlPaginacao.setBorder(new EtchedBorder());

		pnlPaginacao.add(btnPrimeiro);
		pnlPaginacao.add(btnAnterior);
		pnlPaginacao.add(lbPaginacao);
		pnlPaginacao.add(btnProximo);
		pnlPaginacao.add(btnUltimo, "pushx");
		pnlPaginacao.add(btnAtualizar);
		pnlPaginacao.add(btnEditar);
		pnlPaginacao.add(btnExcluir);
		pnlPaginacao.add(btnCancelar);

		scroll.setViewportView(tabela);
	}

	public JPanel getPainelBtns() {
		return pnlPaginacao;
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
