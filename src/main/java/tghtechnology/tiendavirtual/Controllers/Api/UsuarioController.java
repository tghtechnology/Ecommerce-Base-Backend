package tghtechnology.tiendavirtual.Controllers.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Security.Interfaces.Cliente;
import tghtechnology.tiendavirtual.Services.UsuarioService;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForInsert;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForList;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usService;

	// Leer datos de usuario (solo propios)
	@Cliente
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTOForList> listarUno(@PathVariable Integer id,
															Authentication auth){
		UsuarioDTOForList u = usService.listarUsuario(id,auth);
		return ResponseEntity.status(HttpStatus.OK).body(u);
	}
	
	// Registrar usuario básico
	@PostMapping
	public ResponseEntity<UsuarioDTOForList> crear(@RequestBody UsuarioDTOForInsert usuario) throws Exception {
		UsuarioDTOForList u = new UsuarioDTOForList().from(usService.crearUsuario(usuario));
		return ResponseEntity.status(HttpStatus.CREATED).body(u);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> actualizar(@PathVariable Integer id,
													@RequestBody UsuarioDTOForInsert usuario,
													Authentication auth){
		
		usService.actualizarUsuario(id, usuario, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	
}
