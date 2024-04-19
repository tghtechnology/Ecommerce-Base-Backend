package tghtechnology.tiendavirtual.Controllers.Api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import tghtechnology.tiendavirtual.Services.PublicidadService;
import tghtechnology.tiendavirtual.Utils.CustomBeanValidator;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.dto.Publicidad.PublicidadDTOForInsert;
import tghtechnology.tiendavirtual.dto.Publicidad.PublicidadDTOForList;

@RequestMapping("/api/publicidad")
@RestController
@AllArgsConstructor
public class PublicidadController {

    private PublicidadService pubService;
	private CustomBeanValidator validator;
	
	@GetMapping
    public ResponseEntity<List<PublicidadDTOForList>> listar(@RequestParam(required = false, name = "mostrar") Boolean mostrar,
    															Authentication auth){
        List<PublicidadDTOForList> publ = pubService.listarPublicidad(mostrar, auth);
        return ResponseEntity.status(HttpStatus.OK).body(publ);
    }
	
	@Empleado
	@GetMapping("/{id}")
	public ResponseEntity<PublicidadDTOForList> listarUno(@PathVariable Integer id) {
        PublicidadDTOForList pub = pubService.listarUno(id);
        return ResponseEntity.status(HttpStatus.OK).body(pub);
	}
	
	@Gerente
	@PostMapping
	public ResponseEntity<PublicidadDTOForList> crear(@RequestParam String publicidad,
														@RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException, CustomValidationFailedException{
		
		PublicidadDTOForInsert iPub = new ObjectMapper().readValue(publicidad, PublicidadDTOForInsert.class);
		validator.validar(iPub);
		PublicidadDTOForList pub = pubService.crearPublicidad(iPub, imagen);
		return ResponseEntity.status(HttpStatus.CREATED).body(pub); 
	}
	
	@Gerente
	@PutMapping("/{id}")
	public ResponseEntity<Void> modificar(@PathVariable Integer id,
											@RequestParam String publicidad,
											@RequestParam(value = "imagen", required = false) MultipartFile imagen) throws Exception{
		
		PublicidadDTOForInsert mPub = new ObjectMapper().readValue(publicidad, PublicidadDTOForInsert.class);
		validator.validar(mPub);
		
		pubService.actualizarPublicidad(id, mPub, imagen);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Gerente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id) throws Exception{
		pubService.eliminarPublicidad(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}