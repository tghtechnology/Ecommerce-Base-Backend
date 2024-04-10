package tghtechnology.tiendavirtual.Controllers.Api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.MarcaService;
import tghtechnology.tiendavirtual.Utils.CustomBeanValidator;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.dto.Marca.MarcaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Marca.MarcaDTOForList;

@RequestMapping("/api/marca")
@RestController
@AllArgsConstructor
public class MarcaController {

    private MarcaService marService;
	private CustomBeanValidator validator;
	
	@GetMapping
    public ResponseEntity<List<MarcaDTOForList>> listar(@RequestParam(defaultValue = "1", name = "page") Integer page){
        List<MarcaDTOForList> mars = marService.listarMarcas(page);
        return ResponseEntity.status(HttpStatus.OK).body(mars);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<MarcaDTOForList> listarUno(@PathVariable String id) {
        MarcaDTOForList mar = marService.listarUno(id);
        return ResponseEntity.status(HttpStatus.OK).body(mar);
	}
	
	@Empleado
	@GetMapping("/id/{id}")
	public ResponseEntity<MarcaDTOForList> listarUnoPorID(@PathVariable Integer id) {
        MarcaDTOForList mar = marService.listarUnoPorID(id);
        return ResponseEntity.status(HttpStatus.OK).body(mar);
	}
	
	@Empleado
	@PostMapping
	public ResponseEntity<MarcaDTOForList> crear(@RequestParam String marca,
												 @RequestParam(value = "imagen", required = false) MultipartFile imagen)
												 throws IOException, CustomValidationFailedException {
		
		MarcaDTOForInsert iMar = new ObjectMapper().readValue(marca, MarcaDTOForInsert.class);
		validator.validar(iMar);
		
		MarcaDTOForList mar = marService.crearMarca(iMar, imagen);
		return ResponseEntity.status(HttpStatus.CREATED).body(mar); 
	}
	
	@Empleado
	@PutMapping("/{id}")
	public ResponseEntity<Void> actualizar(@PathVariable Integer id,
										  @RequestParam String marca,
										  @RequestParam(value = "imagen", required = false) MultipartFile imagen)
										  throws IOException, CustomValidationFailedException {
		
		MarcaDTOForInsert mMar = new ObjectMapper().readValue(marca, MarcaDTOForInsert.class);
		validator.validar(mMar);
		
		marService.actualizarMarca(id, mMar);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Gerente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id){
		marService.eliminarMarca(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}