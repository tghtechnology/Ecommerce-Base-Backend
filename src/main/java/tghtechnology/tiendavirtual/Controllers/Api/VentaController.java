package tghtechnology.tiendavirtual.Controllers.Api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Security.Interfaces.Cliente;
import tghtechnology.tiendavirtual.Services.VentaService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruResponse;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForInsert;

@RequestMapping("/api/venta")
@RestController
public class VentaController {

	@Autowired
    private VentaService venService;
	
	//Venta de cliente
	@Cliente
	@PostMapping("/cliente")
	public ResponseEntity<ApisPeruResponse> realizar(@RequestBody @Valid VentaDTOForInsert iVen,
														Authentication auth) throws IOException, ApisPeruResponseException{
		ApisPeruResponse ven = venService.realizarVentaCliente(iVen, auth);
		return ResponseEntity.status(HttpStatus.CREATED).body(ven); 
	}
	
	//Venta test
	@Cliente
	@PostMapping("/cliente2")
	public ResponseEntity<Boleta> realizar2(@RequestBody @Valid VentaDTOForInsert iVen,
														Authentication auth) throws IOException, ApisPeruResponseException{
		Boleta ven = venService.testVenta(iVen, auth);
		return ResponseEntity.status(HttpStatus.CREATED).body(ven); 
	}
}