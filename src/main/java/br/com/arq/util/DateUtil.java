package br.com.arq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DateUtil {

	private static final String PATTERN = "dd/MM/yyyy";
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat(PATTERN);
	private static final Logger LOG = Logger.getLogger(DateUtil.class.getName());

	private DateUtil() {
		super();
	}

	public static Date obterDataFormatada(final String strData) {
		Date data = null;
		if (strData != null && !strData.isEmpty()) {
			try {
				data = FORMAT.parse(strData);
			} catch (final ParseException e) {
				LOG.log(Level.SEVERE, null, e);
			}
		}

		return data;
	}
}
