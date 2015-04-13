package br.com.arq.ui;

import java.awt.Color;
import java.awt.Frame;

import javax.annotation.PostConstruct;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import br.com.arq.model.ContaBancaria;

@Component
public class PrincipalUI extends JFrame {

	private static final long serialVersionUID = 1L;
	public JDesktopPane desktop;
	private JMenuItem menuCadFolha;
	private JMenuItem menuCadConta;
	private JComboBox<ContaBancaria> cmbConta;
	private JMenuItem menuCadCategoria;

	@PostConstruct
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Gerenciador Financeiro");
		getContentPane().setLayout(new MigLayout());
		gerarBarraMenus();

		desktop = new JDesktopPane();
		desktop.setBackground(Color.WHITE);

		cmbConta = new JComboBox<ContaBancaria>();

		final JPanel pnlAtralho = new JPanel(new MigLayout());
		pnlAtralho.setBorder(new EtchedBorder());
		pnlAtralho.add(cmbConta);

		add(pnlAtralho, "wrap, growx");
		add(desktop, "push, grow");
		pack();

		setVisible(true);
	}

	private void gerarBarraMenus() {
		final JMenuBar menuBar = new JMenuBar();
		menuCadFolha = new JMenuItem("Folha");
		menuCadConta = new JMenuItem("Conta");
		menuCadCategoria = new JMenuItem("Categoria");

		final JMenu menuCad = new JMenu("Cadastros");
		menuCad.add(menuCadFolha);
		menuCad.add(menuCadConta);
		menuCad.add(menuCadCategoria);

		menuBar.add(menuCad);

		super.setJMenuBar(menuBar);
	}

	public JMenuItem getMenuCadConta() {
		return menuCadConta;
	}

	public JMenuItem getMenuCadFolha() {
		return menuCadFolha;
	}

	public JDesktopPane getDesktop() {
		return desktop;
	}

	public JComboBox<ContaBancaria> getCmbConta() {
		return cmbConta;
	}

	public JMenuItem getMenuCadCategoria() {
		return menuCadCategoria;
	}
}
