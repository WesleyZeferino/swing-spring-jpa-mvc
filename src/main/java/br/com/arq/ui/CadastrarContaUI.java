package br.com.arq.ui;

import java.awt.Component;

import javax.annotation.PostConstruct;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import br.com.arq.model.ContaBancaria;

@org.springframework.stereotype.Component
public class CadastrarContaUI extends CadastroUI<ContaBancaria> {

	private static final String TITULO_FRAME = "Cadastro de Conta";

	private ContaBancaria entidade;
	private JInternalFrame frame;

	private JTextField txtDescricao;

	@Override
	public void iniciarDados() {
		entidade = new ContaBancaria();
	}

	@PostConstruct
	private void init() {
		frame = new JInternalFrame(TITULO_FRAME);
		frame.getContentPane().setLayout(new MigLayout());
		frame.setClosable(true);
		frame.add(getPanelCadastro(), "wrap, growx, push");
		frame.add(getPanelBtns(), "growx, push");
		frame.pack();

		iniciarDados();
		bind();
	}

	private JPanel getPanelCadastro() {
		txtDescricao = new JTextField(20);

		getBinding().add(this, "${entidade.descricao}", txtDescricao);

		final JPanel pnl = createJPanel();
		pnl.add(new JLabel("Descrição:"));
		pnl.add(txtDescricao);

		return pnl;
	}

	@Override
	public Component getFrame() {
		return frame;
	}

	@Override
	public ContaBancaria getEntidade() {
		return entidade;
	}

	public void setEntidade(final ContaBancaria entidade) {
		this.entidade = entidade;
	}

	public JTextField getTxtDescricao() {
		return txtDescricao;
	}
}
