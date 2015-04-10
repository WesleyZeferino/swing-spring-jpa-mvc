package br.com.arq.ui;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import br.com.arq.component.JMoneyField;
import br.com.arq.converter.BigDecimalConverter;
import br.com.arq.converter.DateConverter;
import br.com.arq.model.Folha;
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

	@Override
	public void iniciarDados() {
		// TODO Auto-generated method stub
	}
	
	@PostConstruct
	private void init() {
		gerarTabela();
		gerarModalDetalhe();
		
		btnDetalhar = new JButton("Detalhar");
		
		tabela.getPainelBtns().add(btnDetalhar);
		
		frame = new JDialog();
		frame.getContentPane().setLayout(new MigLayout());
		frame.add(tabela.getComponente(), "wrap, grow, push");
		frame.setModal(true);
		frame.pack();
		
		bind();
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
		
		JPanel panel = createJPanel();
		panel.add(new JLabel("Valor:"));
		panel.add(txtDetalhe, "wrap");
		return panel;
	}

	private JPanel gerarPanelAcaoDetalhe() {
		btnSalvarDetalhe = new JButton("Salvar");
		btnCancelarDetalhe = new JButton("Cancelar");
		
		JPanel panel = createJPanel();
		panel.add(btnSalvarDetalhe);
		panel.add(btnCancelarDetalhe);
		return panel;
	}

	private void gerarTabela() {
		tabela = new AppTable<Folha>();
		
		ColumnBinding bind = tabela.bind(getBinding());
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
	
	public void setEntidade(Folha entidade) {
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

}
