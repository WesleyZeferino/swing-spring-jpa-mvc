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
	private JButton btnGerarRelatorio;
	private JFormattedTextField txtDataInicio;
	private JFormattedTextField txtDataFim;
	private JComboBox<StatusFolha> cmbStatus;
	private JButton btnGerarGrafico;

	@PostConstruct
	private void init() {
		frame = new JInternalFrame("Relatório");
		btnGerarRelatorio = new JButton("Gerar Relatório");
		btnGerarGrafico = new JButton("Gerar Gráfico");
		cmbStatus = new JComboBox<StatusFolha>();

		try {
			txtDataInicio = new JFormattedTextField(new MaskFormatter("##/##/####"));
			txtDataFim = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (final ParseException e) {
			LOG.log(Level.SEVERE, null, e);
		}

		txtDataInicio.setColumns(10);
		txtDataFim.setColumns(10);

		final JPanel panel = createJPanel();
		panel.add(new JLabel("Prev. Quitação entre:"));
		panel.add(txtDataInicio);
		panel.add(new JLabel(" e "));
		panel.add(txtDataFim, "wrap");
		panel.add(new JLabel("Status:"));
		panel.add(cmbStatus, "growx");

		frame.getContentPane().setLayout(new MigLayout());
		frame.setClosable(true);
		frame.add(panel, "wrap, growx, spanx2");
		frame.add(btnGerarRelatorio);
		frame.add(btnGerarGrafico);
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

	public JFormattedTextField getTxtDataInicio() {
		return txtDataInicio;
	}

	public JFormattedTextField getTxtDataFim() {
		return txtDataFim;
	}

	public JComboBox<StatusFolha> getCmbStatus() {
		return cmbStatus;
	}

	public JButton getBtnGerarRelatorio() {
		return btnGerarRelatorio;
	}

	public JButton getBtnGerarGrafico() {
		return btnGerarGrafico;
	}
}
