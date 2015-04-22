package br.com.arq.ui;

import java.text.ParseException;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import net.miginfocom.swing.MigLayout;

import org.springframework.stereotype.Component;

import br.com.arq.model.Folha;
import br.com.arq.model.StatusFolha;

@Component
public class EmitirRelatorioUI extends AppUI<Folha> {
	
	private JInternalFrame frame;
	private JButton btnGerar;
	private JFormattedTextField txtDataInicio;
	private JFormattedTextField txtDataFim;
	private JComboBox<StatusFolha> cmbStatus;
	
	@PostConstruct
	private void init() {
		frame = new JInternalFrame("Relatório");
		btnGerar = new JButton("Gerar");
		cmbStatus = new JComboBox<StatusFolha>();
		
		try {
			txtDataInicio = new JFormattedTextField(new MaskFormatter("##/##/####"));
			txtDataFim = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			LOG.log(Level.SEVERE, null, e);
		}
		
		txtDataInicio.setColumns(10);
		txtDataFim.setColumns(10);
		
		JPanel panel = createJPanel();
		panel.add(new JLabel("Prev. Quitação entre:"));
		panel.add(txtDataInicio);
		panel.add(new JLabel(" e "));
		panel.add(txtDataFim, "wrap");
		panel.add(new JLabel("Status:"));
		panel.add(cmbStatus, "growx");
		
		frame.getContentPane().setLayout(new MigLayout());
		frame.setClosable(true);
		frame.add(panel, "wrap, growx");
		frame.add(btnGerar);
		frame.pack();
		
		bind();
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
	public JInternalFrame getFrame() {
		return frame;
	}

	public JButton getBtnGerar() {
		return btnGerar;
	}

	public JFormattedTextField getTxtDataInicio() {
		return txtDataInicio;
	}

	public JFormattedTextField getTxtDataFim() {
		return txtDataFim;
	}

	public JComboBox<StatusFolha> getCmbStatus() {
		return cmbStatus;
	}
}
