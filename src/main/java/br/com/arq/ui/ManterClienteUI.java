package br.com.arq.ui;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.frame.AppFrame;
import br.com.arq.model.Cliente;

@Component
@SuppressWarnings("unused")
public class ManterClienteUI extends AppUI<Cliente> {

	@Autowired
	private AppFrame frame;

	private JButton btnSalvar;
	private Cliente cliente;

	@Override
	public void iniciarDados() {
		cliente = new Cliente();
	}

	@PostConstruct
	private void init() {
		frame.addPanelFormulario(getPanelCadastro());
		frame.addPanelBtns(getPanelBtns());
		frame.pack();

		iniciarDados();
		bind();
	}

	@Override
	public Cliente getEntidade() {
		return cliente;
	}

	@Override
	public void show() {
		frame.setVisible(true);
	}

	@Override
	public java.awt.Component getFramePrincipal() {
		return frame;
	}

	private JPanel getPanelBtns() {
		btnSalvar = new JButton("Salvar");
		final JPanel panel = new JPanel(new MigLayout());
		panel.add(btnSalvar);
		return panel;
	}

	private JPanel getPanelCadastro() {
		final JTextField txtNome = new JTextField(10);
		final JTextField txtCPF = new JTextField(10);

		getBinding().add(this, "${cliente.nome}", txtNome);
		getBinding().add(this, "${cliente.cpf}", txtCPF);

		final JPanel panel = new JPanel(new MigLayout());
		panel.add(new JLabel("Nome:"));
		panel.add(txtNome, "wrap");
		panel.add(new JLabel("CPF:"));
		panel.add(txtCPF);
		return panel;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JFrame getFrame() {
		return frame;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(final Cliente cliente) {
		this.cliente = cliente;
	}
}