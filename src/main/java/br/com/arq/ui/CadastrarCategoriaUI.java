package br.com.arq.ui;

import java.awt.Component;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import br.com.arq.model.Categoria;

@org.springframework.stereotype.Component
public class CadastrarCategoriaUI extends AppUI<Categoria> {

	private static final String TITULO_FRAME = "Cadastro de Categoria";
	
	private Categoria entidade;
	private JInternalFrame frame;
	private JButton btnSalvar;
	private JButton btnSair;

	@Override
	public void iniciarDados() {
		entidade = new Categoria();
	}
	
	@PostConstruct
	private void init() {
		frame = new JInternalFrame(TITULO_FRAME);
		frame.getContentPane().setLayout(new MigLayout());
		frame.setClosable(true);
		frame.add(getPanelCadastro(), "wrap, grow, push");
		frame.add(getPanelBtns(), ", grow, push");
		frame.pack();
		
		iniciarDados();
		bind();
	}

	private Component getPanelBtns() {
		btnSalvar = new JButton("Salvar");
		btnSair = new JButton("Sair");
		
		JPanel pnl = createJPanel();
		pnl.add(btnSalvar);
		pnl.add(btnSair);
		
		return pnl;
	}

	private Component getPanelCadastro() {
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
	
	@Override
	public Categoria getEntidade() {
		return entidade;
	}
	
	public void setEntidade(Categoria entidade) {
		this.entidade = entidade;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnSair() {
		return btnSair;
	}

}
