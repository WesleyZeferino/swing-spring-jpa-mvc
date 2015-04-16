package br.com.arq.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void string_to_date_null() {
		assertNull(DateUtil.obterDataFormatada(""));
	}

	@Test
	public void string_to_date_now() {
		Date data = new Date();
		assertEquals(data, DateUtil.obterDataFormatada(new SimpleDateFormat("dd/MM/yyyy").format(data)));
	}

}
