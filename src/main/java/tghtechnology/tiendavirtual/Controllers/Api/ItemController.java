package tghtechnology.tiendavirtual.Controllers.Api;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.ItemService;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForInsert;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForList;

@RequestMapping("/api/item")
@RestController
public class ItemController {

	@Autowired
    private ItemService itemService;
	
	@GetMapping
    public ResponseEntity<List<ItemDTOForList>> listar(@RequestParam(defaultValue = "", name = "query") String query,
														@RequestParam(defaultValue = "1.00", name = "min") BigDecimal minimo,
														@RequestParam(defaultValue = "99999.99", name = "max") BigDecimal maximo,
														@RequestParam(defaultValue = "", name = "category") String categoria,
														@RequestParam(defaultValue = "", name = "brand") String marca
    												){
        List<ItemDTOForList> items = itemService.listar(query, minimo, maximo, categoria);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ItemDTOForList> listarUno(@PathVariable Integer id) {
		ItemDTOForList item = itemService.listarUno(id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
	}
	
	@Empleado
	@PostMapping
	public ResponseEntity<ItemDTOForList> crear(@RequestBody @Valid ItemDTOForInsert iItem){
		ItemDTOForList item = itemService.crearItem(iItem);
		return ResponseEntity.status(HttpStatus.CREATED).body(item);
	}
	
	@Empleado
	@PutMapping("/{id}")
	public ResponseEntity<Void> actualizar(@PathVariable Integer id,
											@RequestBody @Valid ItemDTOForInsert mItem){
		itemService.actualizarItem(id, mItem);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Gerente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id){
		itemService.eliminarItem(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}