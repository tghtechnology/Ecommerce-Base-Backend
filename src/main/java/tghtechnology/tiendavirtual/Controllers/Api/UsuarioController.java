package tghtechnology.tiendavirtual.Controllers.Api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Security.CustomJwtAuthToken;
import tghtechnology.tiendavirtual.Security.Interfaces.ClienteNoVerificacion;
import tghtechnology.tiendavirtual.Services.UsuarioService;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForLoginResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {


private UsuarioService usService;

	@ClienteNoVerificacion
	@PostMapping("/validar")
	public ResponseEntity<Void> validar(CustomJwtAuthToken auth) throws MessagingException{
		usService.solicitar_validacion(auth);
		return ResponseEntity.status(HttpStatus.OK).build(); 
	}
	
	@PostMapping("/validar/success")
	public ResponseEntity<UsuarioDTOForLoginResponse> validar_success(@RequestParam(required = true, name = "token") String token
																		,CustomJwtAuthToken oldToken) throws MessagingException{
		UsuarioDTOForLoginResponse resp = usService.verificar_usuario(oldToken, token);
		return ResponseEntity.status(HttpStatus.OK).body(resp); 
	}

}
