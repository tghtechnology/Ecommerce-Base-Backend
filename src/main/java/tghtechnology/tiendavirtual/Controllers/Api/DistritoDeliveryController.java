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
import tghtechnology.tiendavirtual.Enums.DistritoLima;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.DistritoDeliveryService;
import tghtechnology.tiendavirtual.dto.DistritoDelivery.DistritoDeliveryDTOForInsert;
import tghtechnology.tiendavirtual.dto.DistritoDelivery.DistritoDeliveryDTOForList;

@RequestMapping("/api/distrito")
@RestController
public class DistritoDeliveryController {

	@Autowired
    private DistritoDeliveryService ddService;
	
	@GetMapping
    public ResponseEntity<List<DistritoDeliveryDTOForList>> listar(@RequestParam(required = false, name = "activo") Boolean activo,
    														Authentication auth){
        List<DistritoDeliveryDTOForList> dists = ddService.listarDistritos(activo, auth);
        return ResponseEntity.status(HttpStatus.OK).body(dists);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<DistritoDeliveryDTOForList> listarUno(@PathVariable DistritoLima id, Authentication auth) {
        DistritoDeliveryDTOForList dist = ddService.listarUno(id, auth);
        return ResponseEntity.status(HttpStatus.OK).body(dist);
	}
	
//	@Gerente
//	@PutMapping("/{id}")
//	public ResponseEntity<Void> modificar(@PathVariable DistritoLima id,
//											@RequestBody @Valid DistritoDeliveryDTOForInsert mDist){
//		ddService.actualizarDistrito(id, mDist);
//		return ResponseEntity.status(HttpStatus.OK).build();
//	}
//
//	@Gerente
//	@PutMapping("/{id}/activo")
//	public ResponseEntity<Void> modificar(@PathVariable DistritoLima id,
//											@RequestBody @Valid DistritoDeliveryDTOForInsert mDist){
//		ddService.actualizarDistrito(id, mDist);
//		return ResponseEntity.status(HttpStatus.OK).build();
//	}

}