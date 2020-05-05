package br.com.compasso.clientes.exceptions;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ NotFoundException.class })
	public List<String> handleNotFound(Exception exception) {
		String message = exception.getMessage();
		log.info("Uma exception foi capturada {}", message);
		return Arrays.asList(message);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ InvalidParameterException.class })
	public List<String> handleInvalidParameter(Exception exception) {
		String message = exception.getMessage();
		log.info("Uma exception foi capturada {}", message);
		return Arrays.asList(message);
	}
	
}
