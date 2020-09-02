package br.com.compasso.clientes.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class DateValidAndBeforeValidatorImplTest {
	
	private final DateValidAndBeforeValidatorImpl validator = new DateValidAndBeforeValidatorImpl(); 
	
	@Test
	void isValid_QuandoDataNull_EsperaRetornoFalso() {
		assertFalse(validator.isValid(null, null));
	}
	
	@Test
	void isValid_QuandoDataForaDoFormatoInvalido_EsperaRetornoFalso() {
		assertFalse(validator.isValid("0/0/0", null));
	}
	
	@Test
	void isValid_QuandoDataNoFuturo_EsperaRetornoFalso() {
		assertFalse(validator.isValid("13/06/3000", null));
	}
	
	@Test
	void isValid_QuandoDataValida_EsperaRetornoTrue() {
		assertTrue(validator.isValid("13/06/1992", null));
	}
	
}
