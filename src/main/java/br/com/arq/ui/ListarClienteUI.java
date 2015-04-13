package br.com.arq.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;
import br.com.arq.model.Cliente;
import br.com.arq.util.AppTable;
import br.com.arq.util.BindingUtil.ColumnBinding;

@org.springframework.stereotype.Component
public class ListarClienteUI extends AppUI<Cliente> {

	private JFrame frame;
	private JButton btnAtualizar;
	private AppTable<Cliente> tabela;

	@PostConstruct
	private void init() {
		btnAtualizar = new JButton("Atualizar");

		frame = new JFrame();
		frame.getContentPane().setLayout(new MigLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(getTabela(), "wrap");
		frame.add(btnAtualizar);
		frame.pack();

		bind();
	}

	private JComponent getTabela() {
		tabela = new AppTable<Cliente>();

		final ColumnBinding clBinding = tabela.bind(getBinding());
		clBinding.addColumnBinding(0, "${nome}", "Nome");
		clBinding.addColumnBinding(1, "${cpf}", "CPF");

		return tabela.getComponente();
	}

	public JButton getBtnAtualizar() {
		return btnAtualizar;
	}

	public void setDados(final List<Cliente> dados) {
		tabela.setDados(dados);
	}

	@Override
	public java.awt.Component getFrame() {
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
