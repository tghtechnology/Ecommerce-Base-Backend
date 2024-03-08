package tghtechnology.tiendavirtual.Controllers.Api;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tghtechnology.tiendavirtual.Models.Enums.TipoPlato;
import tghtechnology.tiendavirtual.Services.PlatoService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.DineroALetras;
import tghtechnology.tiendavirtual.dto.Plato.PlatoDTOForList;

@RequestMapping("/api/plato")
@RestController
public class PlatoController {

	@Autowired
    private PlatoService plaService;
	
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
	
	@GetMapping("/id/{id}")
	public ResponseEntity<PlatoDTOForList> ListarUno(@PathVariable Integer id) {
	        PlatoDTOForList pla = plaService.listarUno(id); 
            return ResponseEntity.status(HttpStatus.OK).body(pla);
	}
	
	@GetMapping("/{text_id}")
	public ResponseEntity<PlatoDTOForList> ListarPorTextId(@PathVariable String text_id) {
	        PlatoDTOForList pla = plaService.listarUno(text_id); 
            return ResponseEntity.status(HttpStatus.OK).body(pla);
	}
	
	@PostMapping("/{num}")
	public ResponseEntity<String> ConvertirNum(@PathVariable BigDecimal num){
		return ResponseEntity.status(HttpStatus.OK).body(DineroALetras.convertirADinero(num));
	}
	
}
