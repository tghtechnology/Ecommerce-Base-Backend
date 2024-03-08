package tghtechnology.tiendavirtual.Utils.Handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;

@ControllerAdvice
public class CustomValidationHandler{
	// Argumentos no validos para un campo con @Valid (Validacion manual)
	@ExceptionHandler(CustomValidationFailedException.class)
	protected ResponseEntity<Object> handleCustomValidationFailed(CustomValidationFailedException ex) {
		
		Map<String, String> errors = new HashMap<>();
		ex.getResult().getAllErrors().forEach((error) ->{
			
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
}
