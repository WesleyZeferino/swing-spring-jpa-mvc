package br.com.arq.frame;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class AppFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panelFormulario;
	private JPanel panelBtns;

	@PostConstruct
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout());
	}

	public void addPanelFormulario(final JPanel panel) {
		panelFormulario = panel;
		add(panel, "wrap, push, grow");
	}

	public void addPanelBtns(final JPanel panel) {
		panelBtns = panel;
		add(panel, "growx");
	}

	public JPanel getPanelFormulario() {
		return panelFormulario;
	}

	public JPanel getPanelBtns() {
		return panelBtns;
	}

}
