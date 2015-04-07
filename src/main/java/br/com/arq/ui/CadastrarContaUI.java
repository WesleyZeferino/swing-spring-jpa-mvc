package br.com.arq.ui;

import java.awt.Component;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import br.com.arq.model.ContaBancaria;

@org.springframework.stereotype.Component
public class CadastrarContaUI extends AppUI<ContaBancaria> {

	private static final String TITULO_FRAME = "Cadastro de Conta";
	
	private ContaBancaria entidade;
	private JInternalFrame frame;
	private JButton btnSalvar;
	private JButton btnListar;
	private JButton btnSair;
	
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

	private Component getPanelBtns() {
		btnSalvar = new JButton("Salvar");
		btnListar = new JButton("Listar");
		btnSair = new JButton("Sair");
		
		JPanel pnl = createJPanel();
		pnl.add(btnSalvar);
		pnl.add(btnListar);
		pnl.add(btnSair);
		
		return pnl;
	}

	private JPanel getPanelCadastro() {
		JTextField txtDescricao = new JTextField(20);
		
		getBinding().add(this, "${entidade.descricao}", txtDescricao);
		
		JPanel pnl = createJPanel();
		pnl.add(new JLabel("Descrição:"));
		pnl.add(txtDescricao);
		
		return pnl;
	}
	
	@Override
	public Component getFrame() {
		return frame;
	}

	public ContaBancaria getEntidade() {
		return entidade;
	}

	public void setEntidade(ContaBancaria entidade) {
		this.entidade = entidade;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnListar() {
		return btnListar;
	}

	public JButton getBtnSair() {
		return btnSair;
	}
	
}
