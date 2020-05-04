package br.com.compasso.clientes.exceptions;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

	private static final Logger log = Logger.getLogger(RestExceptionHandler.class.getName());
	
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
