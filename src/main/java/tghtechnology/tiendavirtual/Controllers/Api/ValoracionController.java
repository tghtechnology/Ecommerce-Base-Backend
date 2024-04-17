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
import tghtechnology.tiendavirtual.Enums.OrdenValoracion;
import tghtechnology.tiendavirtual.Security.Interfaces.Cliente;
import tghtechnology.tiendavirtual.Services.ValoracionService;
import tghtechnology.tiendavirtual.dto.Valoracion.ValoracionDTOForInsert;
import tghtechnology.tiendavirtual.dto.Valoracion.ValoracionDTOForList;

@RequestMapping("/api/valoracion")
@RestController
public class ValoracionController {

	@Autowired
    private ValoracionService valService;
	
	@GetMapping("/item/{id_item}")
    public ResponseEntity<List<ValoracionDTOForList>> listarPorItem(@PathVariable String id_item,
    								@RequestParam(defaultValue = "-1", name = "stars") Short stars,
    								@RequestParam(defaultValue = "RECENT", name = "order") OrdenValoracion orden,
    								@RequestParam(defaultValue = "1", name = "page") Integer page){
        List<ValoracionDTOForList> vals = valService.listarPorItem(id_item, stars, orden, page);
        return ResponseEntity.status(HttpStatus.OK).body(vals);
    }
	
	@Cliente
	@GetMapping("/user/{id_user}")
    public ResponseEntity<List<ValoracionDTOForList>> listarPorUsuario(@PathVariable Integer id_user,
    								@RequestParam(defaultValue = "RECENT", name = "order") OrdenValoracion orden,
    								@RequestParam(defaultValue = "1", name = "page") Integer page,
    								Authentication auth){
        List<ValoracionDTOForList> vals = valService.listarPorCliente(id_user, orden, page, auth);
        return ResponseEntity.status(HttpStatus.OK).body(vals);
    }
	
	@Cliente
	@PostMapping
	public ResponseEntity<ValoracionDTOForList> crear(@RequestBody @Valid ValoracionDTOForInsert iVal, Authentication auth){
		ValoracionDTOForList val = valService.crearValoracion(iVal, auth);
		return ResponseEntity.status(HttpStatus.CREATED).body(val);
	}
	
	@Cliente
	@PutMapping("/{id}")
	public ResponseEntity<Void> editar(@PathVariable Integer id,
									@RequestBody @Valid ValoracionDTOForInsert mVal,
									Authentication auth){
		valService.modificarValoracion(id, mVal, auth);
		return ResponseEntity.status(HttpStatus.OK).build(); 
	}
	
	@Cliente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id, Authentication auth){
		valService.eliminarValoracion(id, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}