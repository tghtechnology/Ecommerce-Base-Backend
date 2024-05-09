package tghtechnology.tiendavirtual.Utils.Handlers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import tghtechnology.tiendavirtual.Services.SettingsService;

@ControllerAdvice
public class UnknownExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private SettingsService settingsService;

	// Error inesperado
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleUnknownException(Exception ex) {
		String mensaje= "";

		if(settingsService.getBoolean("config.send_error")){
			mensaje= ex.getMessage();
		};
		ex.printStackTrace();
		return new ResponseEntity<Object>(Map.of("error", mensaje), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
