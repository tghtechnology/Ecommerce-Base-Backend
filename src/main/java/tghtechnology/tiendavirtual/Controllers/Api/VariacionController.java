package tghtechnology.tiendavirtual.Controllers.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.Services.VariacionService;
import tghtechnology.tiendavirtual.dto.VariacionItem.VariacionDTOForInsert;
import tghtechnology.tiendavirtual.dto.VariacionItem.VariacionDTOForList;

@RequestMapping("/api/variacion")
@RestController
public class VariacionController {

	@Autowired
    private VariacionService varService;
	
	@Empleado
	@PostMapping
	public ResponseEntity<VariacionDTOForList> crear(@RequestBody @Valid VariacionDTOForInsert iVar){
		VariacionDTOForList var = varService.crearVariacionItem(iVar);
		return ResponseEntity.status(HttpStatus.CREATED).body(var); 
	}
	
	@Empleado
	@PutMapping("/{id}")
	public ResponseEntity<Void> modificar(@PathVariable Integer id,
											@RequestBody @Valid VariacionDTOForInsert mVar){
		varService.actualizarVariacionItem(id, mVar);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Empleado
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id){
		varService.eliminarVariacionItem(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}