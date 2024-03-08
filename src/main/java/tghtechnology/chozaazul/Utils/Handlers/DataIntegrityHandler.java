package tghtechnology.chozaazul.Utils.Handlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DataIntegrityHandler extends ResponseEntityExceptionHandler{
	
	// Error de integridad en la base de datos: Una id de texto ya existe | tamaño de input truncado por BD
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityException(DataIntegrityViolationException ex) {
		
		if(ex.getMessage().contains("Duplicate entry")) {
			return new ResponseEntity<Object>("Ya existe una entrada con ese nombre", HttpStatus.CONFLICT);
		} else
			return new ResponseEntity<Object>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
