package br.com.arq.listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public interface windowGainedFocusListener extends WindowFocusListener {

	@Override
	public void windowGainedFocus(WindowEvent e);
}
