package tghtechnology.tiendavirtual.Utils.Handlers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UnknownExceptionHandler extends ResponseEntityExceptionHandler{
	
	// Error inesperado
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleUnknownException(Exception ex) {
		ex.printStackTrace();
		return new ResponseEntity<Object>(Map.of("error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
