package br.com.arq.ui;

import java.awt.Dimension;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import br.com.arq.component.JMoneyField;
import br.com.arq.converter.BigDecimalConverter;
import br.com.arq.converter.DateConverter;
import br.com.arq.model.Folha;
import br.com.arq.model.TipoPagamento;
import br.com.arq.util.AppTable;
import br.com.arq.util.BindingUtil.ColumnBinding;

@Component
public class ListarFolhaUI extends AppUI<Folha> {

	private AppTable<Folha> tabela;
	private JDialog frame;
	private JDialog modalDetalhe;
	private JButton btnDetalhar;
	private Folha entidade;
	private JMoneyField txtDetalhe;
	private JButton btnSalvarDetalhe;
	private JButton btnCancelarDetalhe;
	private JButton btnQuitar;
	private JDialog modalQuitacao;
	private JButton btnSalvarQuitacao;
	private JComboBox<TipoPagamento> cmbPagamento;
	private JButton btnCancelarQuitacao;
	private JButton btnExportar;
	private JFileChooser chooser;

	@Override
	public void iniciarDados() {
		// TODO Auto-generated method stub
	}
	
	@PostConstruct
	private void init() {
		gerarTabela();
		gerarModalDetalhe();
		gerarModalQuitacao();
		
		chooser = new JFileChooser();

		btnDetalhar = new JButton("Detalhar", new ImageIcon(getClass().getResource("/icon/Add.png")));
		btnQuitar = new JButton("Quitar", new ImageIcon(getClass().getResource("/icon/Cash-icon.png")));
		btnExportar = new JButton("Exportar", new ImageIcon(getClass().getResource("/icon/Arrow_Up.png")));

		tabela.getPainelBtns().add(btnDetalhar);
		tabela.getPainelBtns().add(btnQuitar);
		tabela.getPainelBtns().add(btnExportar);

		frame = new JDialog();
		frame.setTitle(CadastrarFolhaUI.TITULO_FRAME);
		frame.getContentPane().setLayout(new MigLayout());
		frame.add(tabela.getComponente(), "wrap, grow, push");
		frame.setModal(true);
		frame.pack();

		bind();
	}

	private void gerarModalQuitacao() {
		modalQuitacao = new JDialog(frame);
		modalQuitacao.setTitle("Quitar Folhas");
		modalQuitacao.setModal(true);
		modalQuitacao.getContentPane().setLayout(new MigLayout());
		modalQuitacao.add(gerarPanelQuitar(), "wrap, growx");
		modalQuitacao.add(gerarPanelBtnsQuitar(), "growx");
		modalQuitacao.pack();
	}

	private JPanel gerarPanelBtnsQuitar() {
		btnSalvarQuitacao = new JButton("Salvar", new ImageIcon(getClass().getResource("/icon/Save.png")));
		btnCancelarQuitacao = new JButton("Sair", new ImageIcon(getClass().getResource("/icon/Logout.png")));
		
		JPanel pnl = createJPanel();
		pnl.add(btnSalvarQuitacao);
		pnl.add(btnCancelarQuitacao);
		
		return pnl;
	}

	private JPanel gerarPanelQuitar() {
		cmbPagamento = new JComboBox<TipoPagamento>();
		cmbPagamento.setPreferredSize(new Dimension(200, 0));
		getBinding().addJComboBoxBinding(Arrays.asList(TipoPagamento.values()), cmbPagamento);

		JPanel pnl = createJPanel();
		pnl.add(new JLabel("Pagamento:"));
		pnl.add(cmbPagamento);
		
		return pnl;
	}

	private void gerarModalDetalhe() {
		modalDetalhe = new JDialog(frame);
		modalDetalhe.setTitle("Detalhes");
		modalDetalhe.setModal(true);
		modalDetalhe.getContentPane().setLayout(new MigLayout());
		modalDetalhe.add(gerarPanelValorDetalhe(), "growx, wrap");
		modalDetalhe.add(gerarPanelAcaoDetalhe(), "growx");
		modalDetalhe.pack();
	}

	private JPanel gerarPanelValorDetalhe() {
		txtDetalhe = new JMoneyField();
		txtDetalhe.setColumns(20);

		final JPanel panel = createJPanel();
		panel.add(new JLabel("Valor:"));
		panel.add(txtDetalhe, "wrap");
		return panel;
	}

	private JPanel gerarPanelAcaoDetalhe() {
		btnSalvarDetalhe = new JButton("Salvar", new ImageIcon(getClass().getResource("/icon/Save.png")));
		btnCancelarDetalhe = new JButton("Sair", new ImageIcon(getClass().getResource("/icon/Logout.png")));

		final JPanel panel = createJPanel();
		panel.add(btnSalvarDetalhe);
		panel.add(btnCancelarDetalhe);
		return panel;
	}

	private void gerarTabela() {
		tabela = new AppTable<Folha>();

		final ColumnBinding bind = tabela.bind(getBinding());
		bind.addColumnBinding(0, "${categoria} ${totalParcela > 1 ? parcela : ''} ${totalParcela > 1 ? ' de ' : ''} ${totalParcela > 1 ? totalParcela : ''}", "Categoria");
		bind.addColumnBinding(1, "${tipo}", "Tipo");
		bind.addColumnBinding(2, "${statusFolha}", "Status");
		bind.addColumnBinding(3, "${dataPrevistaQuitacao}", "Prev. Quitação", new DateConverter());
		bind.addColumnBinding(4, "${dataQuitacao}", "Quitação", new DateConverter());
		bind.addColumnBinding(5, "${valor}", "Valor", new BigDecimalConverter());
	}

	public void show() {
		frame.setVisible(true);
	}

	@Override
	public Folha getEntidade() {
		return entidade;
	}

	public void setEntidade(final Folha entidade) {
		this.entidade = entidade;
	}

	@Override
	public JDialog getFrame() {
		return frame;
	}

	public AppTable<Folha> getTabela() {
		return tabela;
	}

	public JButton getBtnDetalhar() {
		return btnDetalhar;
	}

	public JDialog getModalDetalhe() {
		return modalDetalhe;
	}

	public JMoneyField getTxtDetalhe() {
		return txtDetalhe;
	}

	public JButton getBtnSalvarDetalhe() {
		return btnSalvarDetalhe;
	}

	public JButton getBtnCancelarDetalhe() {
		return btnCancelarDetalhe;
	}

	public JButton getBtnQuitar() {
		return btnQuitar;
	}

	public JDialog getModalQuitacao() {
		return modalQuitacao;
	}

	public JButton getBtnSalvarQuitacao() {
		return btnSalvarQuitacao;
	}

	public JComboBox<TipoPagamento> getCmbPagamento() {
		return cmbPagamento;
	}

	public JButton getBtnCancelarQuitacao() {
		return btnCancelarQuitacao;
	}

	public JButton getBtnExportar() {
		return btnExportar;
	}

	public JFileChooser getChooser() {
		return chooser;
	}

}
