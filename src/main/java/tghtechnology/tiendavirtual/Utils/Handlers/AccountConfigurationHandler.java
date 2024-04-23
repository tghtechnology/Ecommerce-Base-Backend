package tghtechnology.tiendavirtual.Utils.Handlers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import tghtechnology.tiendavirtual.Utils.Exceptions.AccountConfigurationException;

@ControllerAdvice
public class AccountConfigurationHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountConfigurationException.class)
    public final ResponseEntity<Map<String, String>> handleAccessDeniedException(AccountConfigurationException ex, WebRequest request) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()),HttpStatus.FORBIDDEN);
    }

}
