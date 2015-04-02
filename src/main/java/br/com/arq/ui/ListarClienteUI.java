package br.com.arq.ui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.observablecollections.ObservableCollections;

import br.com.arq.model.Cliente;
import br.com.arq.util.BindingUtil.ColumnBinding;

@org.springframework.stereotype.Component
public class ListarClienteUI extends AppUI<Cliente> {

	private JFrame frame;
	private List<Cliente> dados;
	private JButton btnAtualizar;

	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
		frame = new JFrame();
		frame.getContentPane().setLayout(new MigLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(getTabela(), "wrap");

		btnAtualizar = new JButton("Atualizar");

		frame.add(btnAtualizar);
		frame.pack();

		bind();
	}

	private JScrollPane getTabela() {
		final JTable tabela = new JTable();
		final JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(tabela);

		dados = ObservableCollections.observableList(new ArrayList<Cliente>());

		final ColumnBinding clBinding = getBinding().addJTableBinding(dados, tabela);
		clBinding.addColumnBinding(0, "${nome}", "Nome");
		clBinding.addColumnBinding(1, "${cpf}", "CPF");

		return scroll;
	}

	public JButton getBtnAtualizar() {
		return btnAtualizar;
	}

	public void setDados(final List<Cliente> dados) {
		this.dados.clear();
		this.dados.addAll(dados);
	}

	public List<Cliente> getDados() {
		return dados;
	}

	@Override
	public void show() {
		frame.setVisible(true);
	}

	@Override
	public Component getFramePrincipal() {
		return frame;
	}

	@Override
	public void iniciarDados() {

	}

	@Override
	public Cliente getEntidade() {
		return null;
	}
}
