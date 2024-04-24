package tghtechnology.tiendavirtual.Controllers.Api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Security.CustomJwtAuthToken;
import tghtechnology.tiendavirtual.Security.Interfaces.ClienteNoVerificacion;
import tghtechnology.tiendavirtual.Services.UsuarioService;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForLoginResponse;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForPassChange;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForPassRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {


private UsuarioService usService;

	@ClienteNoVerificacion
	@PostMapping("/validar")
	public ResponseEntity<Void> validar(CustomJwtAuthToken auth) throws MessagingException{
		usService.solicitar_verificacion(auth);
		return ResponseEntity.status(HttpStatus.OK).build(); 
	}
	
	@PostMapping("/validar/success")
	public ResponseEntity<UsuarioDTOForLoginResponse> validar_success(@RequestParam(required = true, name = "token") String token
																		,CustomJwtAuthToken oldToken){
		UsuarioDTOForLoginResponse resp = usService.verificar_usuario(oldToken, token);
		return ResponseEntity.status(HttpStatus.OK).body(resp); 
	}
	
	@PostMapping("/changepass")
	public ResponseEntity<Void> cambiar_pass(@RequestBody @Valid UsuarioDTOForPassRequest req) throws MessagingException{
		usService.solicitar_cambio_pass(req.getEmail());
		return ResponseEntity.status(HttpStatus.OK).build(); 
	}

	@PostMapping("/changepass/success")
	public ResponseEntity<Void> cambiar_pass(@RequestParam(required = true, name = "token") String token
											,@RequestBody @Valid UsuarioDTOForPassChange req) throws MessagingException {
		usService.cambiar_pass(token, req.getPassword());
		return ResponseEntity.status(HttpStatus.OK).build(); 
	}
	
}
