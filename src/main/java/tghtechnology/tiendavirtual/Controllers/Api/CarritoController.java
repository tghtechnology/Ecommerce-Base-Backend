package tghtechnology.tiendavirtual.Controllers.Api;

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
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Security.Interfaces.Cliente;
import tghtechnology.tiendavirtual.Services.CarritoService;
import tghtechnology.tiendavirtual.dto.Carrito.CarritoDTOForList;
import tghtechnology.tiendavirtual.dto.Carrito.DetalleCarrito.DetalleCarritoDTOForInsert;

@RequestMapping("/api/carrito")
@RestController
public class CarritoController {

	@Autowired
    private CarritoService carService;
	
	@Cliente
	@GetMapping
	public ResponseEntity<CarritoDTOForList> ListarCarrito(Authentication auth) {
		CarritoDTOForList car = carService.listarUno(auth);
        return ResponseEntity.status(HttpStatus.OK).body(car);
	}
	
	@Cliente
	@PostMapping
	public ResponseEntity<Void> crearDetalle(@RequestBody @Valid DetalleCarritoDTOForInsert detalle,
												Authentication auth){
		carService.addDetalle(detalle, auth);
        return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Cliente
	@PutMapping("/{id_detalle}")
	public ResponseEntity<Void> actualizarDetalle(@PathVariable Integer id_detalle,
													@RequestBody @Valid DetalleCarritoDTOForInsert detalle,
													Authentication auth){
		carService.actualizarDetalleCarrito(id_detalle, detalle, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Cliente
	@DeleteMapping("/{id_detalle}")
	public ResponseEntity<Void> eliminarDetalle(@PathVariable Integer id_detalle,
													Authentication auth){
		carService.eliminarDetalle(id_detalle, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}