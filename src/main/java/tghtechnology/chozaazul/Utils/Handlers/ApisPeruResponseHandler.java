package tghtechnology.chozaazul.Utils.Handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tghtechnology.chozaazul.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.chozaazul.Utils.ApisPeru.Objects.Response.ApisPeruError;

@ControllerAdvice
public class ApisPeruResponseHandler extends ResponseEntityExceptionHandler{
	
	// Argumentos no validos para un campo con @Valid
	@ExceptionHandler(ApisPeruResponseException.class)
	protected ResponseEntity<ApisPeruError> handleDataMismatchException(ApisPeruResponseException ex) {
		
		return new ResponseEntity<ApisPeruError>(ex.getError(), ex.getStatusCode());
	}
	
}
