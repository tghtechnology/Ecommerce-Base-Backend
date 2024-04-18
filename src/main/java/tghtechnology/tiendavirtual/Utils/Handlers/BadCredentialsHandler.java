package tghtechnology.tiendavirtual.Utils.Handlers;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BadCredentialsHandler extends ResponseEntityExceptionHandler{
	
	// Argumentos no validos para un campo con @Valid
	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<Map<String, String>> handleUnknownException(BadCredentialsException ex) {
		
		final String mensaje = (ex.getMessage() != null && ex.getMessage().equalsIgnoreCase("Bad Credentials")) ? "Usuario o contraseña incorrectos" : ex.getMessage();
		
		ResponseEntity<Map<String, String>> res = new ResponseEntity<Map<String, String>>(Map.of("error", mensaje), HttpStatus.UNAUTHORIZED);
		HttpHeaders.writableHttpHeaders(res.getHeaders()).add("WWW-Authenticate", "Bearer");
		//System.out.println(ex.getMessage());
		return res ;
	}
	
}
