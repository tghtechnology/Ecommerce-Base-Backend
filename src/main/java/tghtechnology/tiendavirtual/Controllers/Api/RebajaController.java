package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.Services.RebajaService;
import tghtechnology.tiendavirtual.dto.Rebaja.RebajaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Rebaja.RebajaDTOForList;
import tghtechnology.tiendavirtual.dto.Rebaja.RebajaDTOForListFull;

@RequestMapping("/api/rebaja")
@RestController
public class RebajaController {

	@Autowired
    private RebajaService rebService;
	
	@GetMapping
    public ResponseEntity<List<RebajaDTOForList>> listarRebajas(Authentication auth){
        List<RebajaDTOForList> rebs = rebService.listarRebajas(auth);
        return ResponseEntity.status(HttpStatus.OK).body(rebs);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<RebajaDTOForListFull> listarUno(@PathVariable Integer id, Authentication auth) {
		RebajaDTOForListFull reb = rebService.listarUno(id, auth);
        return ResponseEntity.status(HttpStatus.OK).body(reb);
	}
	
	@Empleado
	@PostMapping
	public ResponseEntity<RebajaDTOForList> crear(@RequestBody @Valid RebajaDTOForInsert iReb){
		RebajaDTOForList reb = rebService.crearRebaja(iReb);
		return ResponseEntity.status(HttpStatus.CREATED).body(reb); 
	}
	
//	@Empleado
//	@PutMapping("/{id}")
//	public ResponseEntity<Void> modificar(@PathVariable Integer id,
//											@RequestBody @Valid CategoriaDTOForInsert mCat){
//		catService.actualizarCategoria(id, mCat);
//		return ResponseEntity.status(HttpStatus.OK).build();
//	}
//	
//	@Gerente
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> eliminar(@PathVariable Integer id){
//		catService.eliminarCategoria(id);
//		return ResponseEntity.status(HttpStatus.OK).build();
//	} 

}