package tghtechnology.tiendavirtual.Controllers.Api;

import java.io.IOException;

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
import tghtechnology.tiendavirtual.Services.EspecificacionService;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.dto.EspecificacionItem.EspecificacionDTOForInsert;
import tghtechnology.tiendavirtual.dto.EspecificacionItem.EspecificacionDTOForList;

@RequestMapping("/api/especificacion")
@RestController
public class EspecificacionController {

	@Autowired
    private EspecificacionService espService;
	
	@Empleado
	@PostMapping
	public ResponseEntity<EspecificacionDTOForList> crear(@RequestBody @Valid EspecificacionDTOForInsert iEsp) throws IOException, CustomValidationFailedException{
		EspecificacionDTOForList esp = espService.crearEspecificacion(iEsp);
		return ResponseEntity.status(HttpStatus.CREATED).body(esp); 
	}
	
	@Empleado
	@PutMapping("/{id}")
	public ResponseEntity<Void> modificar(@PathVariable Integer id,
											@RequestBody @Valid EspecificacionDTOForInsert iEsp){
		espService.actualizarEspecificacion(id, iEsp);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Empleado
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id){
		espService.eliminarEspecificacion(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}