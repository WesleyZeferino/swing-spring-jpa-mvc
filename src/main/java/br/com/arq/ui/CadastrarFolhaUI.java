package br.com.arq.ui;

import java.text.ParseException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.text.MaskFormatter;

import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arq.component.JMoneyField;
import br.com.arq.converter.BigDecimalConverter;
import br.com.arq.converter.DateConverter;
import br.com.arq.converter.IntegerConverter;
import br.com.arq.model.Categoria;
import br.com.arq.model.Folha;
import br.com.arq.model.Frequencia;
import br.com.arq.model.StatusFolha;
import br.com.arq.model.TipoFolha;
import br.com.arq.model.TipoPagamento;
import br.com.arq.util.BindingUtil;
import br.com.arq.util.ListasUteis;

@Component
public class CadastrarFolhaUI extends CadastroUI<Folha> {

	private static final String TITULO_FRAME_RECORRENCIA = "Recorrência";
	private static final String TITULO_FRAME = "Cadastro de Folha";
	
	@Autowired
	private ListasUteis listas;
	
	private JInternalFrame frame;
	private JDialog dialogRecorrencia;
	private JPanel panelCadastro;
	private Folha folha;
	private Frequencia frequencia;
	private Integer repeticaoFreq;
	private JButton btnSalvarRec;
	private JButton btnCancelarRec;

	@Override
	public void iniciarDados() {
		folha = new Folha();
		frequencia = Frequencia.DIARIO;
		repeticaoFreq = 1;
	}
	
	@PostConstruct
	private void init() {
		gerarPainelCadastro();
		
		dialogRecorrencia = new JDialog();
		dialogRecorrencia.setTitle(TITULO_FRAME_RECORRENCIA);
		dialogRecorrencia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialogRecorrencia.setModal(true);
		dialogRecorrencia.getContentPane().setLayout(new MigLayout());
		dialogRecorrencia.add(getPainelRecorrencia(), "wrap");
		dialogRecorrencia.add(getBtnsRecorrencia());
		dialogRecorrencia.pack();

		frame = new JInternalFrame(TITULO_FRAME);
		frame.setClosable(true);
		frame.setResizable(true);
		frame.getContentPane().setLayout(new MigLayout());
		frame.add(panelCadastro, "growx, wrap, push");
		frame.add(getPanelBtns(), "growx, pushx");
		frame.pack();

		iniciarDados();
		bind();
	}

	private JPanel getBtnsRecorrencia() {
		btnSalvarRec = new JButton("Salvar");
		btnCancelarRec = new JButton("Cancelar");
		
		JPanel panel = createJPanel();
		panel.add(btnSalvarRec);
		panel.add(btnCancelarRec);
		
		return panel;
	}

	private JPanel getPainelRecorrencia() {
		JComboBox<Frequencia> cmbFreq = new JComboBox<Frequencia>();
		JTextField txtMaximo = new JTextField(20);
		
		getBinding().addJComboBoxBinding(Arrays.asList(Frequencia.values()), cmbFreq);
		getBinding().add(this, "${frequencia}", cmbFreq, "selectedItem");
		getBinding().add(this, "${repeticaoFreq}", txtMaximo, new IntegerConverter());
		
		JPanel panel = createJPanel();
		panel.add(new JLabel("Insira a frequência com que essa folha será cadastrada."), "wrap, spanx2");
		panel.add(new JLabel("Recorrência:"));
		panel.add(cmbFreq, "wrap, grow");
		panel.add(new JLabel("Limite:"));
		panel.add(txtMaximo);
		
		return panel;
	}

	private void gerarPainelCadastro() {
		final JComboBox<TipoFolha> cmbTipoFolha = new JComboBox<TipoFolha>();
		final JComboBox<StatusFolha> cmbStatus = new JComboBox<StatusFolha>();
		final JComboBox<TipoPagamento> cmbTipoPagamento = new JComboBox<TipoPagamento>();
		final JComboBox<Categoria> cmbCategoria = new JComboBox<Categoria>();
		final JMoneyField txtValor = new JMoneyField();
		final JTextArea txtDescricao = new JTextArea(5, 20);
		JFormattedTextField txtQuitacao = null;
		JFormattedTextField txtPrevQuitacao = null;

		try {
			txtQuitacao = new JFormattedTextField(new MaskFormatter("##/##/####"));
			txtPrevQuitacao = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (final ParseException e) {
			e.printStackTrace();
		}

		final BindingUtil bin = getBinding();
		bin.addJComboBoxBinding(Arrays.asList(TipoFolha.values()), cmbTipoFolha);
		bin.addJComboBoxBinding(Arrays.asList(StatusFolha.values()), cmbStatus);
		bin.addJComboBoxBinding(Arrays.asList(TipoPagamento.values()), cmbTipoPagamento);
		bin.addJComboBoxBinding(listas.getCategorias(), cmbCategoria);
		bin.add(this, "${folha.descricao}", txtDescricao);
		bin.add(this, "${folha.dataPrevistaQuitacao}", txtPrevQuitacao, new DateConverter());
		bin.add(this, "${folha.dataQuitacao}", txtQuitacao, new DateConverter());
		bin.add(this, "${folha.valor}", txtValor, new BigDecimalConverter());
		bin.add(this, "${folha.categoria}", cmbCategoria, "selectedItem");
		bin.add(this, "${folha.statusFolha}", cmbStatus, "selectedItem");
		bin.add(this, "${folha.tipo}", cmbTipoFolha, "selectedItem");
		bin.add(this, "${folha.tipoPagamento}", cmbTipoPagamento, "selectedItem");

		panelCadastro = new JPanel(new MigLayout());
		panelCadastro.setBorder(new EtchedBorder());
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
	}

	@Override
	public Folha getEntidade() {
		return this.folha;
	}
	
	@Override
	public java.awt.Component getFrame() {
		return frame;
	}
	
	public Folha getFolha() {
		return folha;
	}

	public void setFolha(final Folha folha) {
		this.folha = folha;
	}

	public JDialog getDialogRecorrencia() {
		return dialogRecorrencia;
	}

	public void setFrequencia(Frequencia frequencia) {
		this.frequencia = frequencia;
	}

	public void setRepeticaoFreq(Integer repeticaoFreq) {
		this.repeticaoFreq = repeticaoFreq;
	}

	public Frequencia getFrequencia() {
		return frequencia;
	}

	public Integer getRepeticaoFreq() {
		return repeticaoFreq;
	}

	public JButton getBtnSalvarRec() {
		return btnSalvarRec;
	}

	public JButton getBtnCancelarRec() {
		return btnCancelarRec;
	}
}
