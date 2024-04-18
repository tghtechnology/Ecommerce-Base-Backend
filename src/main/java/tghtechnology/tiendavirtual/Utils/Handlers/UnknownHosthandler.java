package tghtechnology.tiendavirtual.Utils.Handlers;

import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UnknownHosthandler extends ResponseEntityExceptionHandler{
	
	// Error de conexion
	@ExceptionHandler(UnknownHostException.class)
	protected ResponseEntity<Object> handleIOException(UnknownHostException ex) {
		ex.printStackTrace();
		return new ResponseEntity<Object>(Map.of("error", "Error de conexion"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}