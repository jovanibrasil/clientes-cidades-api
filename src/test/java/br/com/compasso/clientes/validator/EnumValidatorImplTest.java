package br.com.compasso.clientes.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;

import javax.validation.Payload;

import org.junit.jupiter.api.Test;

import br.com.compasso.clientes.dominio.enumeration.Sexo;

public class EnumValidatorImplTest {

	@Test
	void isValid_QuandoStringEnumValido_EsperaRetornoTrue() {
		EnumValidatorImpl enumValidator = new EnumValidatorImpl();
		enumValidator.initialize(criaValidador(Sexo.class));
		assertTrue(enumValidator.isValid("M", null));
	}

	@Test
	void isValid_QuandoStringEnumInvalido_EsperaRetornoFalse() {
		EnumValidatorImpl enumValidator = new EnumValidatorImpl();
		enumValidator.initialize(criaValidador(Sexo.class));
		assertFalse(enumValidator.isValid("V", null));
	}

	private EnumValidator criaValidador(Class<? extends Enum<?>> classe) {
		return new EnumValidator() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return null;
			}
			
			@Override
			public Class<? extends Payload>[] payload() {
				return null;
			}
			
			@Override
			public String message() {
				return null;
			}
			
			@Override
			public Class<?>[] groups() {
				return null;
			}
			
			@Override
			public Class<? extends Enum<?>> enumClass() {
				return classe;
			}
		};
	}

}
