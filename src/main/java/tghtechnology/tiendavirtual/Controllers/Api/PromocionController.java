package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tghtechnology.tiendavirtual.Services.PromocionService;
import tghtechnology.tiendavirtual.dto.Promocion.PromocionDTOForList;

@RequestMapping("/api/promocion")
@RestController
public class PromocionController {

	@Autowired
    private PromocionService promService;
	
	@GetMapping
    public ResponseEntity<List<PromocionDTOForList>> listarPromociones(){
            List<PromocionDTOForList> proms = promService.listarPromociones();
            return ResponseEntity.status(HttpStatus.OK).body(proms);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<PromocionDTOForList> ListarUno(@PathVariable Integer id) {
		PromocionDTOForList prom = promService.listarUno(id);
		
        return ResponseEntity.status(HttpStatus.OK).body(prom);
	}

}