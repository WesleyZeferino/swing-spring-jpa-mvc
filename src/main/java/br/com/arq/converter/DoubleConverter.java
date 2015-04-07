package br.com.arq.converter;

import java.math.BigDecimal;
import java.util.Optional;

import org.jdesktop.beansbinding.Converter;

import br.com.arq.util.NumberUtil;

public class DoubleConverter extends Converter<Double, String> {

	private static final double VALOR_DEFAULT = 0d;

	@Override
	public String convertForward(final Double numero) {
		return NumberUtil.obterNumeroFormatado(numero);
	}

	@Override
	public Double convertReverse(final String numero) {
		BigDecimal valor = NumberUtil.obterNumeroFormatado(numero);
		return Optional.ofNullable(valor).map(BigDecimal::doubleValue).orElse(VALOR_DEFAULT);
	}

}
