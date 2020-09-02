package br.com.compasso.clientes.util;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class DateUtilsTest {
	
	private final LocalDate dateObject = LocalDate.of(1992, Month.JUNE, 13);
	private final String dateString = "13/06/1992";
	
	@Test
	void converteLocalDateToString_QuandoLocalDateOk_EsperaRetornoStringOk() {
		assertEquals(dateString, DateUtils.converteLocalDateToString(dateObject));
	}
	
	@Test
	void converteStringToLocalDate_QuandoStringOk_EsperaRetornoLocalDateOk() {
		assertEquals(dateObject, DateUtils.converteStringToLocalDate(dateString));
	}

}
