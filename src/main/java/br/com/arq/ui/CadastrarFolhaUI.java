package br.com.arq.ui;

import java.awt.Component;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.MaskFormatter;

import net.miginfocom.swing.MigLayout;
import br.com.arq.component.JMoneyField;
import br.com.arq.model.Categoria;
import br.com.arq.model.Folha;
import br.com.arq.model.StatusFolha;
import br.com.arq.model.TipoFolha;
import br.com.arq.model.TipoPagamento;
import br.com.arq.util.BindingUtil;

@org.springframework.stereotype.Component
public class CadastrarFolhaUI extends AppUI<Folha> {

	private JInternalFrame frame;
	private JPanel panelCadastro;
	private Folha folha;
	private List<Categoria> categorias;
	private JButton btnSalvar;

	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
		gerarPainelCadastro();

		frame = new JInternalFrame("Cadastro de Folha");
		frame.setClosable(true);
		frame.setResizable(true);
		frame.getContentPane().setLayout(new MigLayout());
		frame.add(panelCadastro);
		frame.pack();

		iniciarDados();
		bind();
	}

	private void gerarPainelCadastro() {
		final JComboBox<TipoFolha> cmbTipoFolha = new JComboBox<TipoFolha>();
		final JComboBox<StatusFolha> cmbStatus = new JComboBox<StatusFolha>();
		final JComboBox<TipoPagamento> cmbTipoPagamento = new JComboBox<TipoPagamento>();
		final JComboBox<Categoria> cmbCategoria = new JComboBox<Categoria>();
		final JMoneyField txtValor = new JMoneyField();
		final JTextArea txtDescricao = new JTextArea(10, 20);
		JFormattedTextField txtQuitacao = null;
		JFormattedTextField txtPrevQuitacao = null;

		try {
			txtQuitacao = new JFormattedTextField(new MaskFormatter("##/##/####"));
			txtPrevQuitacao = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (final ParseException e) {
			e.printStackTrace();
		}

		btnSalvar = new JButton("Salvar");

		final BindingUtil bin = getBinding();
		bin.addJComboBoxBinding(Arrays.asList(TipoFolha.values()), cmbTipoFolha);
		bin.addJComboBoxBinding(Arrays.asList(StatusFolha.values()), cmbStatus);
		bin.addJComboBoxBinding(Arrays.asList(TipoPagamento.values()), cmbTipoPagamento);
		bin.addJComboBoxBinding(categorias, cmbCategoria);
		bin.add(this, "${folha.descricao}", txtDescricao);
		bin.add(this, "${folha.dataPrevistaQuitacao}", txtPrevQuitacao);
		bin.add(this, "${folha.dataQuitacao}", txtQuitacao);
		bin.add(this, "${folha.valor}", txtValor);
		bin.add(this, "${folha.categoria}", cmbCategoria, "selectedItem");
		bin.add(this, "${folha.statusFolha}", cmbStatus, "selectedItem");
		bin.add(this, "${folha.tipo}", cmbTipoFolha, "selectedItem");
		bin.add(this, "${folha.tipoPagamento}", cmbTipoPagamento, "selectedItem");
		// bin.add(this, "${folha.}", cmb);
		// bin.add(this, "${folha.}", target);
		// bin.add(this, "${folha.}", target);

		panelCadastro = new JPanel(new MigLayout());
		panelCadastro.add(new JLabel("Tipo Folha:"));
		panelCadastro.add(cmbTipoFolha, "wrap, growx");
		panelCadastro.add(new JLabel("Status:"));
		panelCadastro.add(cmbStatus, "wrap, growx");
		panelCadastro.add(new JLabel("Pagamento:"));
		panelCadastro.add(cmbTipoPagamento, "wrap, growx");
		panelCadastro.add(new JLabel("Categoria:"));
		panelCadastro.add(cmbCategoria, "wrap, growx");
		panelCadastro.add(new JLabel("Previsão de Quitação:"));
		panelCadastro.add(txtPrevQuitacao, "wrap, growx");
		panelCadastro.add(new JLabel("Data Quitação:"));
		panelCadastro.add(txtQuitacao, "wrap, growx");
		panelCadastro.add(new JLabel("Valor:"));
		panelCadastro.add(txtValor, "wrap, growx");
		panelCadastro.add(new JLabel("Descrição:"));
		panelCadastro.add(txtDescricao, "wrap, growx");
		panelCadastro.add(btnSalvar);
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public void setCategorias(final List<Categoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public Folha getEntidade() {
		return this.folha;
	}

	@Override
	public void show() {
		PrincipalUI.desktop.add(frame);
		frame.show();
	}

	@Override
	public void iniciarDados() {
		folha = new Folha();
	}

	@Override
	public Component getFramePrincipal() {
		return panelCadastro;
	}

	public Folha getFolha() {
		return folha;
	}

	public void setFolha(final Folha folha) {
		this.folha = folha;
	}

}
