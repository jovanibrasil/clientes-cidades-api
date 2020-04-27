package br.com.compasso.clientes.exceptions;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ NotFoundException.class })
	public List<String> handleNotFound(Exception exception) {
		String message = exception.getMessage();
		log.info(message);
		return Arrays.asList(message);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ InvalidParameterException.class })
	public List<String> handleInvalidParameter(Exception exception) {
		String message = exception.getMessage();
		log.info(message);
		return Arrays.asList(message);
	}
	
}
