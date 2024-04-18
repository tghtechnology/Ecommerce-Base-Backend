package tghtechnology.tiendavirtual.Controllers.Api;

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
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.DescuentoService;
import tghtechnology.tiendavirtual.dto.Descuento.DescuentoDTOForInsert;
import tghtechnology.tiendavirtual.dto.Descuento.DescuentoDTOForList;

@RequestMapping("/api/descuento")
@RestController
public class DescuentoController {

	@Autowired
    private DescuentoService desService;
	
	@Gerente
	@GetMapping
    public ResponseEntity<List<DescuentoDTOForList>> listar(){
        List<DescuentoDTOForList> dess = desService.listarDescuentos();
        return ResponseEntity.status(HttpStatus.OK).body(dess);
    }
	
	@Gerente
	@GetMapping("/{id}")
	public ResponseEntity<DescuentoDTOForList> listarUno(@PathVariable Integer id) {
        DescuentoDTOForList des = desService.listarUno(id);
        return ResponseEntity.status(HttpStatus.OK).body(des);
	}
	
	@Gerente
	@PostMapping
	public ResponseEntity<DescuentoDTOForList> crear(@RequestBody @Valid DescuentoDTOForInsert iDes){
		DescuentoDTOForList des = desService.crearDescuento(iDes);
		return ResponseEntity.status(HttpStatus.CREATED).body(des); 
	}
	
	@Gerente
	@PutMapping("/{id}")
	public ResponseEntity<Void> modificar(@PathVariable Integer id,
											@RequestBody @Valid DescuentoDTOForInsert mDes){
		desService.actualizarDescuento(id, mDes);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Gerente
	@PutMapping("/{id}/{activo}")
	public ResponseEntity<Void> modificarActivo(@PathVariable Integer id,
												@PathVariable Boolean activo){
		desService.actualizarActivo(id, activo);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Gerente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Integer id){
		desService.eliminarDescuento(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 

}