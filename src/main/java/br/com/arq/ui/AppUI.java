package br.com.arq.ui;

import java.awt.Container;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.beansbinding.BindingGroup;

import br.com.arq.model.Entidade;
import br.com.arq.util.BindingUtil;

public abstract class AppUI<T extends Entidade> {

	private final BindingUtil binding;

	protected static final Logger LOG = Logger.getLogger(AppUI.class.getName());

	public AppUI() {
		binding = BindingUtil.create(new BindingGroup());
	}

	public abstract T getEntidade();

	public abstract void iniciarDados();

	public abstract java.awt.Component getFrame();

	public void limparComponentes() {
		final java.awt.Component component = getFrame();
		if (component instanceof JInternalFrame) {
			limparComponentes(((JInternalFrame) component).getContentPane().getComponents());
		}
	}

	public void limparComponentes(final Container container) {
		limparComponentes(container.getComponents());
	}

	public void limparComponentes(final java.awt.Component[] components) {
		Arrays.asList(components).forEach(comp -> {
			if (comp instanceof JPanel) {
				limparComponentes((JPanel) comp);
			}
		});
	}

	public void limparComponentes(final JPanel container) {
		for (final java.awt.Component comp : container.getComponents()) {
			if (comp instanceof JPanel) {
				limparComponentes((JPanel) comp);
			}

			if (comp instanceof JTextComponent) {
				((JTextComponent) comp).setText(null);
			} else if (comp instanceof JComboBox) {
				((JComboBox<?>) comp).setSelectedItem(null);
			}
		}
	}

	protected void bind() {
		getBinding().getBindingGroup().bind();
	}

	protected BindingUtil getBinding() {
		return binding;
	}

	protected JPanel createJPanel() {
		final JPanel pnl = new JPanel(new MigLayout());
		pnl.setBorder(new EtchedBorder());
		return pnl;
	}
}
