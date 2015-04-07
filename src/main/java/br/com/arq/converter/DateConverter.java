package br.com.arq.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	@Override
	public String convertForward(final Date s) {
		return new SimpleDateFormat(MASK_DATE).format(s);
	}

	@Override
	public Date convertReverse(final String t) {
		try {
			if (t.matches(REGEX)) {
				return new SimpleDateFormat(MASK_DATE).parse(t);
			}
		} catch (final ParseException ex) {
			Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
