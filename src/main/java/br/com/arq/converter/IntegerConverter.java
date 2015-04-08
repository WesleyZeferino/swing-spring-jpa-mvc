package br.com.arq.converter;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.beansbinding.Converter;

/**
 * 
 * @author Wesley Luiz
 */
public class IntegerConverter extends Converter<Integer, String> {

	private static final int VALOR_INT_DEFAULT = 0;
	private static final String VALOR_STR_DEFAULT = "0";

	@Override
	public String convertForward(final Integer numero) {
		return Optional.ofNullable(numero).map(String::valueOf).orElse(VALOR_STR_DEFAULT);
	}

	@Override
	public Integer convertReverse(final String numero) {
		Integer valor = null;
		
		try {
			valor = Integer.valueOf(numero);
		} catch (NumberFormatException e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Erro ao tentar converter String em Integer");
		}
		
		return Optional.ofNullable(valor).orElse(VALOR_INT_DEFAULT);
	}

}
