package br.com.arq.ui;

import java.awt.Color;
import java.awt.Frame;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JLabel lbSaldo;
	private JMenuItem menuExtrato;

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
		lbSaldo = new JLabel();

		final JPanel pnlAtalho = new JPanel(new MigLayout());
		pnlAtalho.setBorder(new EtchedBorder());
		pnlAtalho.add(cmbConta);
		pnlAtalho.add(lbSaldo);

		add(pnlAtalho, "wrap, growx");
		add(desktop, "push, grow");
		pack();

		setVisible(true);
	}

	private void gerarBarraMenus() {
		final JMenuBar menuBar = new JMenuBar();
		menuCadFolha = new JMenuItem("Folha", new ImageIcon(getClass().getResource("/icon/Status-wallet-open-icon.png")));
		menuCadConta = new JMenuItem("Conta", new ImageIcon(getClass().getResource("/icon/User.png")));
		menuCadCategoria = new JMenuItem("Categoria", new ImageIcon(getClass().getResource("/icon/Paste.png")));
		menuExtrato = new JMenuItem("Extrato");

		final JMenu menuCad = new JMenu("Cadastros");
		menuCad.add(menuCadFolha);
		menuCad.add(menuCadConta);
		menuCad.add(menuCadCategoria);

		final JMenu menuRel = new JMenu("Relatórios");
		menuRel.add(menuExtrato);

		menuBar.add(menuCad);
		menuBar.add(menuRel);

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

	public JLabel getLbSaldo() {
		return lbSaldo;
	}

	public JMenuItem getMenuExtrato() {
		return menuExtrato;
	}
}
