package tghtechnology.chozaazul.Controllers.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tghtechnology.chozaazul.Services.UsuarioService;
import tghtechnology.chozaazul.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.chozaazul.Utils.Exceptions.DataMismatchException;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForList;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usService;
	

	// Registrar usuario por defecto
		@PostMapping
		public ResponseEntity<UsuarioDTOForList> crearusuario(@RequestBody String us) throws Exception {

			try {
				UsuarioDTOForList u = usService.crearUsuarioDefault(us);
				return ResponseEntity.status(HttpStatus.CREATED).body(u);
				
			} catch(CustomValidationFailedException | DataMismatchException ex) {
				throw ex;
			}
			catch(Exception ex) {
				throw new BadCredentialsException(null);
			}

		}

		@GetMapping
		public ResponseEntity<List<UsuarioDTOForList>> listarCategorias() {
			List<UsuarioDTOForList> uss = usService.listarUsuarios();
			return ResponseEntity.status(HttpStatus.OK).body(uss);
		}
}
