package br.com.compasso.clientes.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ErroValidacao> fieldErrors = ex.getBindingResult().getFieldErrors()
				.stream().map(e -> new ErroValidacao(e.getDefaultMessage(),
				e.getField(), e.getRejectedValue())).collect(Collectors.toList());

		DetalheErro<List<ErroValidacao>> errorDetail = new DetalheErro.Builder<List<ErroValidacao>>()
				.message("Valores inválidos de entrada")
				.code(status.value())
				.status(status.getReasonPhrase())
				.timestamp(LocalDateTime.now())
				.objectName(ex.getBindingResult().getObjectName())
				.errors(fieldErrors).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
	}
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler({ NotFoundException.class })
	public DetalheErro<String> handleNotFound(Exception exception) {
		
		String message = exception.getMessage();
		log.info("Uma NotFoundException foi capturada {}", message);
		
		return new DetalheErro.Builder<String>()
				.message(message)
				.timestamp(LocalDateTime.now())
				.code(HttpStatus.NOT_FOUND.value())
				.build();
	}
	
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler({ InvalidParameterException.class })
	public DetalheErro<String> handleInvalidParameter(Exception exception) {

		String message = exception.getMessage();
		log.info("Uma InvalidParameterException foi capturada {}", message);
		
		return new DetalheErro.Builder<String>()
				.message(message)
				.code(HttpStatus.UNPROCESSABLE_ENTITY.value())
				.build();
	}
	
}
