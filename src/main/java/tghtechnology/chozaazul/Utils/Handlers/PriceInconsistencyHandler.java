package tghtechnology.chozaazul.Utils.Handlers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tghtechnology.chozaazul.Utils.Exceptions.PriceInconsistencyException;

@ControllerAdvice
public class PriceInconsistencyHandler extends ResponseEntityExceptionHandler{
	
	// Argumentos no validos para un campo con @Valid
	@ExceptionHandler(PriceInconsistencyException.class)
	protected ResponseEntity<Object> handlePriceInconsistencyException(PriceInconsistencyException ex) {
		
		Map<String, String> errors = new LinkedHashMap<>();
		errors.put("message", ex.getLocalizedMessage());
		errors.put("Esperado", ex.getExpected().toString());
		errors.put("Recibido", ex.getReceived().toString());
		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
}
