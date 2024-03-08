package tghtechnology.tiendavirtual.Controllers.Admin;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Services.PromocionService;
import tghtechnology.tiendavirtual.Utils.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.dto.Promocion.PromocionDTOForInsert;
import tghtechnology.tiendavirtual.dto.Promocion.PromocionDTOForList;

@RequestMapping("/admin/promocion")
@RestController
public class PromocionAdminController {

	@Autowired
    private PromocionService promService;
	
	@Empleado
	@GetMapping
    public ResponseEntity<List<PromocionDTOForList>> listarPromociones(){
            List<PromocionDTOForList> proms = promService.listarPromociones();
            return ResponseEntity.status(HttpStatus.OK).body(proms);
    }
	
	@Empleado
	@GetMapping("/{id}")
	public ResponseEntity<PromocionDTOForList> ListarUno(@PathVariable Integer id) {
		PromocionDTOForList prom = promService.listarUno(id);
		
        return ResponseEntity.status(HttpStatus.OK).body(prom);
	}
	
	@Empleado
	@PostMapping    
    public ResponseEntity<PromocionDTOForList> crearPromocion(@Valid @RequestBody PromocionDTOForInsert prom){
            PromocionDTOForList newProm = promService.crearPromocion(prom);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProm);
    }
	
	@Empleado
	@PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPromocion(@PathVariable Integer id, @Valid @RequestBody PromocionDTOForInsert body){
        promService.actualizarPromocion(id, body);
        return ResponseEntity.status(HttpStatus.OK).build();    
    }
	
	@Empleado
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable Integer id){
            promService.eliminarPromocion(id);
            return ResponseEntity.status(HttpStatus.OK).build();
    }

}