package br.com.arq.ui;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import br.com.arq.model.Entidade;

@Component
public abstract class CadastroUI<T extends Entidade> extends AppUI<T> {

	private JButton btnSalvar;
	private JButton btnSair;
	private JButton btnListar;
	private JButton btnLimpar;
	
	@PostConstruct
	private void init() {
		btnSalvar = new JButton("Salvar");
		btnSair = new JButton("Sair");
		btnLimpar = new JButton("Limpar");
		btnListar = new JButton("Listar");
	}

	protected JPanel getPanelBtns() {
		JPanel panel = new JPanel(new MigLayout());
		panel.setBorder(new EtchedBorder());
		panel.add(btnSalvar);
		panel.add(btnLimpar);
		panel.add(btnListar, "pushx");
		panel.add(btnSair);
		
		return panel;
	}
	
	public JButton getBtnLimpar() {
		return btnLimpar;
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnSair() {
		return btnSair;
	}

	public JButton getBtnListar() {
		return btnListar;
	}

}