package br.com.compasso.clientes.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidatorImpl.class)
public @interface EnumValidator {
	String message() default "Sexo deve ser um enum válido";
	Class<?>[] groups() default {};
	Class<? extends Enum<?>> enumClass();
	Class<? extends Payload>[] payload() default {};
}