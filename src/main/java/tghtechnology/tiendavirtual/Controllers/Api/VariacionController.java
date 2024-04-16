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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.Services.VariacionService;
import tghtechnology.tiendavirtual.Utils.CustomBeanValidator;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.dto.VariacionItem.VariacionDTOForInsert;
import tghtechnology.tiendavirtual.dto.VariacionItem.VariacionDTOForList;

@RequestMapping("/api/variacion")
@RestController
public class VariacionController {

	@Autowired
    private VariacionService varService;
	@Autowired
	private CustomBeanValidator validator;
	
	@Empleado
	@PostMapping
	public ResponseEntity<VariacionDTOForList> crear(@RequestParam String variacion,
													 @RequestParam(value = "imagen", required = true) MultipartFile imagen) throws IOException, CustomValidationFailedException{
		
		VariacionDTOForInsert iVar = new ObjectMapper().readValue(variacion, VariacionDTOForInsert.class);
		validator.validar(iVar);
		
		VariacionDTOForList var = varService.crearVariacionItem(iVar, imagen);
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
	@PutMapping("/{id}/imagen")
	public ResponseEntity<Void> cambiarImagen(@PathVariable Integer id,
												@RequestParam(value = "imagen", required = true) MultipartFile imagen) throws Exception{
		varService.actualizarImagen(id, imagen);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Empleado
	@PutMapping("/restock/{id}")
	public ResponseEntity<Integer> restock(@PathVariable Integer id,
											@RequestParam(value = "cantidad", required = true) Integer cantidad){
		Integer resp = varService.restock(id, cantidad);
		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}
	
	@Empleado
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id){
		varService.eliminarVariacionItem(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}