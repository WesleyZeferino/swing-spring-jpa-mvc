package br.com.arq.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public interface Bindable {

	public static final PropertyChangeSupport PROPERTY = new PropertyChangeSupport(Bindable.class);

	public default void addPropertyChangeListener(final PropertyChangeListener listener) {
		PROPERTY.addPropertyChangeListener(listener);
	}

	public default void removePropertyChangeListener(final PropertyChangeListener listener) {
		PROPERTY.removePropertyChangeListener(listener);
	}
}
