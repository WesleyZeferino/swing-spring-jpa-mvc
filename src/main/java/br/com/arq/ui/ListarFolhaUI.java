package br.com.arq.ui;

import javax.annotation.PostConstruct;
import javax.swing.JDialog;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import br.com.arq.converter.BigDecimalConverter;
import br.com.arq.converter.DateConverter;
import br.com.arq.model.Folha;
import br.com.arq.util.AppTable;
import br.com.arq.util.BindingUtil.ColumnBinding;

@Component
public class ListarFolhaUI extends AppUI<Folha> {

	private AppTable<Folha> tabela;
	private JDialog frame;

	@Override
	public void iniciarDados() {
		// TODO Auto-generated method stub
	}
	
	@PostConstruct
	private void init() {
		gerarTabela();
		
		frame = new JDialog();
		frame.getContentPane().setLayout(new MigLayout());
		frame.add(tabela.getComponente(), "wrap, grow, push");
		frame.setModal(true);
		frame.pack();
		
		bind();
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
		return null;
	}

	@Override
	public JDialog getFrame() {
		return frame;
	}
	
	public AppTable<Folha> getTabela() {
		return tabela;
	}

}
