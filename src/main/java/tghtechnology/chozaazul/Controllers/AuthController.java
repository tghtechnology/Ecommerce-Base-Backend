package tghtechnology.chozaazul.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tghtechnology.chozaazul.Services.AuthService;
import tghtechnology.chozaazul.Services.UsuarioService;
import tghtechnology.chozaazul.dto.UserLogin;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForLoginResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AuthController {
	
	@Autowired
	private final AuthService authService;
	private final UsuarioService userService;
	//private final AuthenticationManager authManager;
	
	@PostMapping("/token")
	public UsuarioDTOForLoginResponse token(@RequestBody UserLogin login,
											@RequestHeader Map<String, String> headers)
											throws AuthenticationException {
		//Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		//String token = authService.generateToken(auth);
		String token = authService.autenticar(login, headers);
		
		return userService.devolverLogin(login.getUsername(), token);
	}
	
	@GetMapping("/logout-success")
	public ResponseEntity<Void> logoutFinalizado(){
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	
}
