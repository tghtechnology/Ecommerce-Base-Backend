package tghtechnology.tiendavirtual.Utils.Handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruError;

@Slf4j
@ControllerAdvice
public class ApisPeruResponseHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ApisPeruResponseException.class)
	protected ResponseEntity<ApisPeruError> handleApisPeruException(ApisPeruResponseException ex) {
		log.error("ApisPeru Responded with Status code: " + ex.getStatusCode());
		return new ResponseEntity<ApisPeruError>(ex.getError(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
