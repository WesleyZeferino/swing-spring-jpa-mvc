package br.com.arq.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.arq.converter.BigDecimalConverter;

/**
 * Classe utilitária responsável por fornecer funções de formatação de objetos
 * numérios.<br/>
 * Arquivo: NumberUtil.java <br/>
 * Criado em: 10/09/2014
 *
 * @author Wesley Luiz
 * @version 1.0.0
 */
public final class NumberUtil {

	private static final int VALOR_INICIAL = 0;
	private static final String VALOR_DEFAULT = "0,00";
	private static final NumberFormat FORMATO = NumberFormat.getInstance(new Locale("pt", "BR"));
	public static final int DECIMAL_DIGITS = 2;

	private NumberUtil() {
		super();
	}

	private static void configurar() {
		FORMATO.setMaximumFractionDigits(2);
		FORMATO.setMinimumFractionDigits(2);
	}

	/**
	 * Método responsável por transformar um objeto numérico em uma
	 * <code>String</code> formatada na moeda local.
	 *
	 * @author Wesley Luiz
	 * @param value
	 * @return
	 */
	public static String obterNumeroFormatado(final Number value) {
		configurar();
		return Optional.ofNullable(value).map(FORMATO::format).orElse(VALOR_DEFAULT);
	}

	/**
	 * Método responsável por transformar uma <code>String</code> em um
	 * <code>BigDecimal</code>.
	 *
	 * @author Wesley Luiz
	 * @param value
	 * @return
	 */
	public static BigDecimal obterNumeroFormatado(final String value) {
		configurar();

		Number parse = null;

		try {
			parse = FORMATO.parse(value);
		} catch (final ParseException ex) {
			Logger.getLogger(BigDecimalConverter.class.getName()).log(Level.WARNING, "Erro ao tentar converter String em BigDecimal");
		}

		final BigDecimal valor = Optional.ofNullable(parse).map(Number::doubleValue).map(BigDecimal::valueOf).orElse(new BigDecimal(VALOR_INICIAL));

		return valor.setScale(DECIMAL_DIGITS, RoundingMode.CEILING);
	}
}
