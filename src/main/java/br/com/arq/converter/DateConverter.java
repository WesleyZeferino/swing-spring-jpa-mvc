package br.com.arq.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author Wesley Luiz
 */
public class DateConverter extends Converter<Date, String> {

	private static final String MASK_DATE = "dd/MM/yyyy";
	private static final String REGEX = "\\d{2}/\\d{2}/\\d{4}";
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat(MASK_DATE);

	@Override
	public String convertForward(final Date data) {
		return Optional.ofNullable(data).map(FORMAT::format).orElse("");
	}

	@Override
	public Date convertReverse(final String data) {
		try {
			if (data.matches(REGEX)) {
				return new SimpleDateFormat(MASK_DATE).parse(data);
			}
		} catch (final ParseException ex) {
			Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
