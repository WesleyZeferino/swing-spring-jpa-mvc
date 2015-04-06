package br.com.arq.ui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrincipalUI extends JFrame {

	private static final long serialVersionUID = 1L;
	public static JDesktopPane desktop;

	@Autowired
	private CadastrarFolhaUI cadastroFolha;

	public PrincipalUI() {
		iniciarComponentes();
	}

	private void iniciarComponentes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Gerenciador Financeiro");
		getContentPane().setLayout(new MigLayout());

		desktop = new JDesktopPane();
		desktop.setBackground(Color.WHITE);

		final JButton btn = new JButton("BTN");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				cadastroFolha.show();
			}
		});

		final JPanel pnlAtralho = new JPanel(new MigLayout());
		pnlAtralho.add(btn);

		add(pnlAtralho, "wrap, growx");
		add(desktop, "push, grow");
		pack();

		setVisible(true);
	}
}
