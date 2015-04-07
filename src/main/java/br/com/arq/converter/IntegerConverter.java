package br.com.arq.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.beansbinding.Converter;

/**
 * 
 * @author Wesley Luiz
 */
public class IntegerConverter extends Converter<Integer, String> {

	@Override
	public String convertForward(final Integer numero) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Integer convertReverse(final String numero) {
		Integer res = null;
		try {
			res = Integer.valueOf(numero);
		} catch (NumberFormatException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
		}
		return res;
	}

}
