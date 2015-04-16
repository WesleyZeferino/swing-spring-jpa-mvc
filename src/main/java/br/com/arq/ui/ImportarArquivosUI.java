package br.com.arq.ui;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.observablecollections.ObservableCollections;

import br.com.arq.converter.BigDecimalConverter;
import br.com.arq.converter.DateConverter;
import br.com.arq.dto.TransacaoDTO;
import br.com.arq.model.Categoria;
import br.com.arq.model.Folha;
import br.com.arq.util.BindingUtil;
import br.com.arq.util.ObjetoUtil;
import br.com.arq.validation.ValidacaoException;

@org.springframework.stereotype.Component
public class ImportarArquivosUI extends AppUI<Folha> {

	private static final String EXTENSAO_XLS = "xls";
	private static final String MSG_ARQUIVO_INVALIDO = "Arquivo inválido!";
	private static final String EXTENSAO_OFX = "ofx";
	private static final String RAIZ = "/";
	private JFileChooser chooser;
	private JDialog modal;
	private JTable tabela;
	private List<TransacaoDTO> transacoes;
	private List<Categoria> categorias;
	private JButton btnSalvar;
	private JButton btnCancelar;

	@PostConstruct
	private void init() {
		chooser = new JFileChooser();
		chooser.setApproveButtonText("Selecionar");

		transacoes = ObservableCollections.observableList(new ArrayList<TransacaoDTO>());
		categorias = ObservableCollections.observableList(new ArrayList<Categoria>());

		modal = new JDialog();
		modal.setTitle(CadastrarFolhaUI.TITULO_FRAME);
		modal.setModal(true);
		modal.getContentPane().setLayout(new MigLayout());
		modal.add(getScroll(), "wrap, grow, push");
		modal.add(getBtnsAcoes(), "growx");

		final BindingUtil binding = getBinding();

		binding.addJTableBinding(transacoes, tabela).addColumnBinding(0, "${descricao}", "Descrição").addColumnBinding(1, "${quitacao}", "Quitação", new DateConverter()).addColumnBinding(2, "${categoria}", "Categoria").addColumnBinding(3, "${valor}", "Valor", new BigDecimalConverter());

		final JComboBox<Categoria> cmbCategoria = new JComboBox<Categoria>();

		binding.addJComboBoxBinding(categorias, cmbCategoria);
		binding.getBindingGroup().bind();

		tabela.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cmbCategoria));

		modal.pack();
	}

	private JPanel getBtnsAcoes() {
		btnSalvar = new JButton("Salvar", new ImageIcon(getClass().getResource("/icon/Save.png")));
		btnCancelar = new JButton("Sair", new ImageIcon(getClass().getResource("/icon/Logout.png")));

		final JPanel panel = createJPanel();
		panel.add(btnSalvar);
		panel.add(btnCancelar);

		return panel;
	}

	private JScrollPane getScroll() {
		final JScrollPane scroll = new JScrollPane();
		tabela = new JTable();

		scroll.setViewportView(tabela);
		return scroll;
	}

	public InputStream getArquivo() {
		final File file = chooser.getSelectedFile();

		if (ObjetoUtil.isReferencia(file)) {
			if (isArquivoOfx() || isArquivoXls()) {
				try {
					return new FileInputStream(file);
				} catch (final FileNotFoundException e) {
					LOG.log(Level.WARNING, null, e);
				}
			} else {
				throw new ValidacaoException(MSG_ARQUIVO_INVALIDO);
			}
		}
		
		return null;
	}

	public boolean isArquivoXls() {
		return chooser.getSelectedFile().getName().endsWith(EXTENSAO_XLS);
	}

	public boolean isArquivoOfx() {
		return chooser.getSelectedFile().getName().endsWith(EXTENSAO_OFX);
	}

	public void showChooser(final String diretorio, final Component parent) {
		final File dir = new File(Optional.ofNullable(diretorio).orElse(RAIZ));
		Optional.ofNullable(dir).ifPresent(chooser::setCurrentDirectory);

		chooser.showOpenDialog(parent);
	}

	@Override
	public Folha getEntidade() {
		return null;
	}

	@Override
	public void iniciarDados() {
		// TODO Auto-generated method stub
	}

	@Override
	public JDialog getFrame() {
		return modal;
	}

	public JFileChooser getChooser() {
		return chooser;
	}

	public JDialog getModal() {
		return modal;
	}

	public JTable getTabela() {
		return tabela;
	}

	public void setTransacoes(final List<TransacaoDTO> transacoes) {
		this.transacoes.clear();
		this.transacoes.addAll(transacoes);
	}

	public List<TransacaoDTO> getTransacoes() {
		return transacoes;
	}

	public void setCategorias(final List<Categoria> categorias) {
		this.categorias.clear();
		this.categorias.addAll(categorias);
	}

	public JButton getBtnSalvar() {
		return btnSalvar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

}
