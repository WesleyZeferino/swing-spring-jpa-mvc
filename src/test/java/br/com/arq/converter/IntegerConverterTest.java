package br.com.arq.converter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IntegerConverterTest {

	private IntegerConverter converter;

	@Before
	public void init() {
		converter = new IntegerConverter();
	}
	
	@Test
	public void string_to_integer_eh_1() {
		assertEquals(Integer.valueOf(1), converter.convertReverse("1"));
	}

	@Test
	public void string_to_integer_letras() {
		assertEquals(Integer.valueOf(0), converter.convertReverse("ab"));
	}
	
	@Test
	public void string_to_integer_double() {
		assertEquals(Integer.valueOf(0), converter.convertReverse("12.2"));
	}
	
	@Test
	public void integer_to_string_eh_1() {
		assertEquals("1", converter.convertForward(1));
	}
	
	@Test
	public void integer_to_string_null() {
		assertEquals("0", converter.convertForward(null));
	}
}
