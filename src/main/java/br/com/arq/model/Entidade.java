package br.com.arq.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.AbstractPersistable;

@SuppressWarnings("unused")
public class Entidade extends AbstractPersistable<Integer> {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.ORDINAL)
	private StatusEntidade status;

	@Transient
	private transient final PropertyChangeSupport property = new PropertyChangeSupport(this);

	@PrePersist
	private void prePersist() {
		status = StatusEntidade.ATIVO;
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		property.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		property.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		property.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		property.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
		property.firePropertyChange(propertyName, oldValue, newValue);
	}

	public StatusEntidade getStatus() {
		return status;
	}

	public void setStatus(final StatusEntidade status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getId() == null ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Entidade other = (Entidade) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
}
