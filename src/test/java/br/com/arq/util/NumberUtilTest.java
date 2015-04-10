package br.com.arq.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class NumberUtilTest {

	@Test
	public void testObterNumeroFormatadoNumber() {
		assertEquals(new BigDecimal(15).setScale(2), NumberUtil.obterNumeroFormatado("15,00"));
	}

	@Test
	public void testObterNumeroFormatadoString() {
		assertEquals("15,00", NumberUtil.obterNumeroFormatado(15));
	}

}
