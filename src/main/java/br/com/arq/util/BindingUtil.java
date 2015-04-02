package br.com.arq.util;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Converter;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.beansbinding.Validator;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

@SuppressWarnings({ "unchecked", "rawtypes"})
public final class BindingUtil {

	private final BindingGroup bindingGroup;

	private BindingUtil(final BindingGroup bindingGroup) {
		this.bindingGroup = bindingGroup;
	}

	public static BindingUtil create(final BindingGroup bindingGroup) {
		return new BindingUtil(bindingGroup);
	}

	public BindingUtil addJComboBoxBinding(final List list, final JComboBox combobox) {
		bindingGroup.addBinding(SwingBindings.createJComboBoxBinding(AutoBinding.UpdateStrategy.READ, list, combobox));
		return this;
	}

	public ColumnBinding addJTableBinding(final List<?> list, final JTable jtable) {
		final JTableBinding tableBinding = SwingBindings.createJTableBinding(UpdateStrategy.READ_WRITE, list, jtable);
		final ColumnBinding columnBinding = new ColumnBinding(this, tableBinding);

		bindingGroup.addBinding(tableBinding);

		return columnBinding;
	}

	public class ColumnBinding {

		private final JTableBinding tableBinding;
		private final BindingUtil bindingUtil;

		public ColumnBinding(final BindingUtil bindingUtil, final JTableBinding tableBinding) {
			this.tableBinding = tableBinding;
			this.bindingUtil = bindingUtil;
		}

		public ColumnBinding addColumnBinding(final int index, final String expression, final String nameColumn) {
			addColumnBinding(index, expression, nameColumn, null, null);
			return this;
		}

		public ColumnBinding addColumnBinding(final int index, final String expression, final String nameColumn, final Converter converter) {
			addColumnBinding(index, expression, nameColumn, converter, null);
			return this;
		}

		public ColumnBinding addColumnBinding(final int index, final String expression, final String nameColumn, final Class<?> columnClass) {
			addColumnBinding(index, expression, nameColumn, null, columnClass);
			return this;
		}

		public ColumnBinding addColumnBinding(final int index, final String expression, final String nameColumn, final Converter converter, final Class<?> columnClass) {
			final JTableBinding.ColumnBinding columnBinding = tableBinding.addColumnBinding(index, ELProperty.create(expression)).setColumnName(nameColumn);

			if (ObjetoUtil.isReferencia(converter)) {
				columnBinding.setConverter(converter);
			}

			if (ObjetoUtil.isReferencia(columnClass)) {
				columnBinding.setColumnClass(columnClass);
			}
			return this;
		}

		public BindingUtil close() {
			return bindingUtil;
		}
	}

	public BindingUtil add(final Object source, final String sourceEl, final Object target) {
		return add(source, sourceEl, target, "text", null, null);
	}

	public BindingUtil add(final Object source, final String sourceEl, final Object target, final String targetEl) {
		return add(source, sourceEl, target, targetEl, null, null);
	}

	public BindingUtil add(final Object source, final String sourceEl, final Object target, final Converter converter) {
		return add(source, sourceEl, target, "text", converter, null);
	}

	public BindingUtil add(final Object source, final String sourceEl, final Object target, final Validator validator) {
		return add(source, sourceEl, target, "text", null, validator);
	}

	public BindingUtil add(final Object source, final String sourceEl, final Object target, final String targetEl, final Converter converter, final Validator validator) {
		final Binding b = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, source, ELProperty.create(sourceEl), target, BeanProperty.create(targetEl));

		if (ObjetoUtil.isReferencia(converter)) {
			b.setConverter(converter);
		}

		if (ObjetoUtil.isReferencia(validator)) {
			b.setValidator(validator);
		}

		bindingGroup.addBinding(b);
		return this;
	}

	public BindingGroup getBindingGroup() {
		return bindingGroup;
	}
}
