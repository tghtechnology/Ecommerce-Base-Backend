package tghtechnology.tiendavirtual.Utils.Handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;

@ControllerAdvice
public class DataMismatchHandler extends ResponseEntityExceptionHandler{
	
	// Argumentos no validos para un campo con @Valid
	@ExceptionHandler(DataMismatchException.class)
	protected ResponseEntity<Object> handleDataMismatchException(DataMismatchException ex) {
		
		Map<String, String> errors = new HashMap<>();
		errors.put(ex.getVar(), ex.getMsg());
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
}
