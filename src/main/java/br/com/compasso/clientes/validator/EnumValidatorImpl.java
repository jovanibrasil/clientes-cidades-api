package br.com.compasso.clientes.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

	private List<String> values; 

    @Override
    public void initialize(EnumValidator constraintAnnotation){
    	values = new ArrayList<>();
	    Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
	    for(Enum<?> enumVal : enumClass.getEnumConstants()) {
	    	values.add(enumVal.toString().toUpperCase());
	    }
    }
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return values.contains(value);
	}

}