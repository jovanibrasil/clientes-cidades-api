package br.com.compasso.clientes.validator;

import java.time.LocalDate;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.compasso.clientes.util.DateUtils;

public class DateValidAndBeforeValidatorImpl implements ConstraintValidator<DateValidAndBeforeValidator, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if(value == null) return false;
		
		String regexDate = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";
		Pattern pattern = Pattern.compile(regexDate);
		if(!pattern.matcher(value).matches()) return false;
		
		LocalDate date = DateUtils.converteStringToLocalDate(value);
		return LocalDate.now().isAfter(date);
	}
	
}
