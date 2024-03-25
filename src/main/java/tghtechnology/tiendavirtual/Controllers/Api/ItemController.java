package tghtechnology.tiendavirtual.Controllers.Api;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.ItemService;
import tghtechnology.tiendavirtual.Utils.CustomBeanValidator;
import tghtechnology.tiendavirtual.Utils.Exceptions.CustomValidationFailedException;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForInsert;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForList;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForListFull;

@RequestMapping("/api/item")
@RestController
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;
	private CustomBeanValidator validator;
	
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
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ItemDTOForListFull> listarUno(@PathVariable Integer id) {
		ItemDTOForListFull item = itemService.listarUno(id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
	}
	
	@GetMapping("/{text_id}")
	public ResponseEntity<ItemDTOForListFull> listarUno(@PathVariable String text_id) {
		ItemDTOForListFull item = itemService.listarUno(text_id);
        return ResponseEntity.status(HttpStatus.OK).body(item);
	}
	
	//@Empleado
	@PostMapping
	public ResponseEntity<ItemDTOForList> crear(@RequestParam String item,
			 									@RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException, CustomValidationFailedException{
		
		ItemDTOForInsert iItem = new ObjectMapper().readValue(item, ItemDTOForInsert.class);
		validator.validar(iItem);
		System.out.println("a");
		ItemDTOForList lItem = itemService.crearItem(iItem, imagen);
		return ResponseEntity.status(HttpStatus.CREATED).body(lItem);
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
	
	//@Empleado
	@PostMapping("/{id}/imagen")
	public ResponseEntity<Void> addImagen(@PathVariable Integer id,
										  @RequestParam(value = "imagen", required = true) MultipartFile imagen) throws IOException{
		
		itemService.addImagen(id, imagen);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	//@Empleado
	@DeleteMapping("/{id}/imagen/{index}")
	public ResponseEntity<Void> eliminarImagen(@PathVariable Integer id, @PathVariable Integer index) throws Exception{
		
		itemService.eliminarImagen(id, index);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}