package tghtechnology.tiendavirtual.Controllers.Admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Models.Enums.TipoPlato;
import tghtechnology.tiendavirtual.Services.PlatoService;
import tghtechnology.tiendavirtual.Utils.CustomBeanValidator;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.Utils.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.dto.Plato.PlatoDTOForInsert;
import tghtechnology.tiendavirtual.dto.Plato.PlatoDTOForList;

@RequestMapping("/admin/plato")
@RestController
public class PlatoAdminController {

	@Autowired
    private PlatoService plaService;
	@Autowired
	private CustomBeanValidator validator;
	
	@Empleado
	@GetMapping
    public ResponseEntity<List<PlatoDTOForList>> listarPlato(
		    		@RequestParam(defaultValue = "", name = "query") String query,
					@RequestParam(defaultValue = "1.00", name = "min") BigDecimal minimo,
					@RequestParam(defaultValue = "99999.99", name = "max") BigDecimal maximo,
					@RequestParam(defaultValue = "", name = "categoria") String categoria,
					@RequestParam(defaultValue = "NINGUNO", name = "tipo") TipoPlato tipoPlato){
		
        List<PlatoDTOForList> plas = plaService.listarPlato( query, minimo, maximo, categoria, tipoPlato);
        return ResponseEntity.status(HttpStatus.OK).body(plas);
    }
	
	@Empleado
	@GetMapping("/id/{id}")
	public ResponseEntity<PlatoDTOForList> ListarUno(@PathVariable Integer id) {
	        PlatoDTOForList pla = plaService.listarUno(id); 
            return ResponseEntity.status(HttpStatus.OK).body(pla);
	}
	
	@Empleado
	@GetMapping("/{id}")
	public ResponseEntity<PlatoDTOForList> ListarPorTextId(@PathVariable String text_id) {
	        PlatoDTOForList pla = plaService.listarUno(text_id); 
            return ResponseEntity.status(HttpStatus.OK).body(pla);
	}
	
	@Empleado
	@PostMapping    
    public ResponseEntity<PlatoDTOForList> crearPlato(@Valid @RequestParam(value = "plato") String plato,
													  @RequestParam(value = "imagen", required = true) MultipartFile imagen) throws IOException, CustomValidationFailedException{
		
		PlatoDTOForInsert iPla =  new ObjectMapper().readValue(plato, PlatoDTOForInsert.class);
		validator.validar(iPla);
		
        PlatoDTOForList newPlato = plaService.crearPlato(iPla, imagen);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlato);
    }
	
	@Empleado
	@PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPlato(@PathVariable Integer id, @Valid @RequestParam(value = "plato") String plato,
			  									@RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException, CustomValidationFailedException{

		PlatoDTOForInsert mPla =  new ObjectMapper().readValue(plato, PlatoDTOForInsert.class);
		validator.validar(mPla);

        plaService.actualizarPlato(id, mPla, imagen);
        return ResponseEntity.status(HttpStatus.OK).build();    
    }
	
	@Empleado
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Integer id){
            plaService.eliminarPlato(id);
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
