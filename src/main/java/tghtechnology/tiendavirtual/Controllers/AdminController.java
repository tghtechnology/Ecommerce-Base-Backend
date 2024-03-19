package tghtechnology.tiendavirtual.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Services.UsuarioService;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForList;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UsuarioService usService;

	// Registrar usuario por defecto
	@PostMapping
	public ResponseEntity<UsuarioDTOForList> crearAdmin(@RequestBody String us) throws Exception {

		try {
			UsuarioDTOForList u = usService.crearAdminDefault(us);
			return ResponseEntity.status(HttpStatus.CREATED).body(u);

		} catch (CustomValidationFailedException | DataMismatchException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new BadCredentialsException(null);
		}
	}
}
