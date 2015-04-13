package br.com.arq.converter;

import java.math.BigDecimal;

import org.jdesktop.beansbinding.Converter;

import br.com.arq.util.NumberUtil;

/**
 * 
 * @author Wesley Luiz
 */
public class BigDecimalConverter extends Converter<BigDecimal, String> {

	@Override
	public String convertForward(final BigDecimal numero) {
		return NumberUtil.obterNumeroFormatado(numero);
	}

	@Override
	public BigDecimal convertReverse(final String numero) {
		return NumberUtil.obterNumeroFormatado(numero);
	}
}
