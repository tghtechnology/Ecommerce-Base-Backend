package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;
import tghtechnology.tiendavirtual.Enums.RegionPeru;
import tghtechnology.tiendavirtual.Security.Interfaces.Admin;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.PuntoShalomService;
import tghtechnology.tiendavirtual.dto.PuntoShalom.PuntoShalomDTOForList;

@RequestMapping("/api/shalom")
@RestController
public class PuntoShalomController {

	@Autowired
    private PuntoShalomService psService;
	
	@GetMapping
    public ResponseEntity<List<PuntoShalomDTOForList>> listar(@RequestParam(required = true, name = "departamento") RegionPeru departamento){
        List<PuntoShalomDTOForList> pss = psService.listarPuntos(departamento);
        return ResponseEntity.status(HttpStatus.OK).body(pss);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<PuntoShalomDTOForList> listarUno(@PathVariable Integer id) {
		PuntoShalomDTOForList ps = psService.listarUno(id);
        return ResponseEntity.status(HttpStatus.OK).body(ps);
	}
	
	@Admin
	@PostMapping
	public ResponseEntity<Map<String, String>> crear(@RequestBody List<Map<String, Object>> pointJson){
		Map<String, String> resp = psService.cargarPuntos(pointJson);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
	}
	
	@Gerente
	@PutMapping("/{id}")
	public ResponseEntity<Void> modificar(@PathVariable Integer id,
											@RequestBody @NotNull(message = "No puede ser nulo") Boolean activo){
		psService.actualizarPunto(id, activo);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Gerente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id){
		psService.eliminarPunto(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}