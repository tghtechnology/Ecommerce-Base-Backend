package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Security.Interfaces.Cliente;
import tghtechnology.tiendavirtual.Services.DireccionService;
import tghtechnology.tiendavirtual.dto.Direccion.DireccionDTOForInsert;
import tghtechnology.tiendavirtual.dto.Direccion.DireccionDTOForList;

@RequestMapping("/api/direccion")
@RestController
public class DireccionController {

	@Autowired
    private DireccionService dirService;
	
	@Cliente
	@GetMapping
    public ResponseEntity<List<DireccionDTOForList>> listar(@RequestParam(name = "cliente") Integer id_cliente,
    														Authentication auth){
        List<DireccionDTOForList> dirs = dirService.listarDirecciones(id_cliente, auth);
        return ResponseEntity.status(HttpStatus.OK).body(dirs);
    }
	
	@Cliente
	@GetMapping("/{id}")
	public ResponseEntity<DireccionDTOForList> listarUno(@PathVariable Integer id,
															Authentication auth) {
        DireccionDTOForList dir = dirService.listarUno(id, auth);
        return ResponseEntity.status(HttpStatus.OK).body(dir);
	}
	
	@Cliente
	@PostMapping
	public ResponseEntity<DireccionDTOForList> crear(@RequestBody @Valid DireccionDTOForInsert iDir,
														Authentication auth){
		DireccionDTOForList dir = dirService.crearDireccion(iDir, auth);
		return ResponseEntity.status(HttpStatus.CREATED).body(dir); 
	}
	
	@Cliente
	@PutMapping("/{id}")
	public ResponseEntity<Void> modificar(@PathVariable Integer id,
											@RequestBody @Valid DireccionDTOForInsert mDir,
											Authentication auth){
		dirService.actualizarDireccion(id, mDir, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Cliente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id,
											Authentication auth){
		dirService.eliminarDireccion(id, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}