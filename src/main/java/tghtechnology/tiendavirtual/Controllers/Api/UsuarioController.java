package tghtechnology.tiendavirtual.Controllers.Api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/usuario")
public class UsuarioController {

//	@Autowired
//	private UsuarioService usService;
//
//	// Leer datos de usuario (solo propios)
//	@Cliente
//	@GetMapping("/{id}")
//	public ResponseEntity<UsuarioDTOForList> listarUno(@PathVariable Integer id,
//															Authentication auth){
//		UsuarioDTOForList u = usService.listarUsuario(id,auth);
//		return ResponseEntity.status(HttpStatus.OK).body(u);
//	}
//	
//	// Registrar usuario básico
//	@PostMapping
//	public ResponseEntity<UsuarioDTOForList> crear(@RequestBody UsuarioDTOForInsert usuario) throws Exception {
//		UsuarioDTOForList u = new UsuarioDTOForList().from(usService.crearUsuario(usuario));
//		return ResponseEntity.status(HttpStatus.CREATED).body(u);
//	}
//	
//	@PutMapping("/{id}")
//	public ResponseEntity<Void> actualizar(@PathVariable Integer id,
//													@RequestBody UsuarioDTOForModify usuario,
//													Authentication auth){
//		
//		usService.actualizarUsuarioCliente(id, usuario, auth);
//		return ResponseEntity.status(HttpStatus.OK).build();
//	}
}
