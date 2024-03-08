package tghtechnology.tiendavirtual.Utils.Handlers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class IOExceptionHandler extends ResponseEntityExceptionHandler{
	
	// Error de lectura en un JSON, usualmente porque esta malformado
	@ExceptionHandler(IOException.class)
	protected ResponseEntity<Object> handleIOException(IOException ex) {
		ex.printStackTrace();
		return new ResponseEntity<Object>("Datos de entrada incorrectos", HttpStatus.BAD_REQUEST);
	}
	
}