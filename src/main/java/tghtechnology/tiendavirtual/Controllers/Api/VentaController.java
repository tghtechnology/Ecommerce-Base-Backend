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
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.APTranslatorService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruResponse;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForList;

@RequestMapping("/api/venta")
@RestController
public class VentaController {

	@Autowired
    private VentaService venService;
	@Autowired
	APTranslatorService apTranslator;
	
	//Venta de cliente
	@Cliente
	@PostMapping("/cliente")
	public ResponseEntity<ApisPeruResponse> realizar(@RequestBody @Valid VentaDTOForInsert iVen,
														Authentication auth) throws IOException, ApisPeruResponseException{
		VentaDTOForList ven = venService.realizarVentaCliente(iVen, auth);
		ApisPeruResponse resp = venService.enviarApisPeru(ven);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
	}
	
	//Venta test
	@Cliente
	@PostMapping("/cliente-pdf")
	public ResponseEntity<byte[]> realizarPdf(@RequestBody @Valid VentaDTOForInsert iVen,
														Authentication auth) throws IOException, ApisPeruResponseException{
		VentaDTOForList ven = venService.realizarVentaCliente(iVen, auth);
		byte[] resp = venService.apisPeruPDF(ven);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
	}
	
	//Venta anonima
	@PostMapping("/no-cliente")
	public ResponseEntity<ApisPeruResponse> realizarAnonimo(@RequestBody @Valid VentaDTOForInsert iVen) throws IOException, ApisPeruResponseException{
		VentaDTOForList ven = venService.realizarVentaAnonima(iVen);
		ApisPeruResponse resp = venService.enviarApisPeru(ven);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
	}
	
	//Venta test
	@PostMapping("/no-cliente-pdf")
	public ResponseEntity<byte[]> realizarPdfAnonimo(@RequestBody @Valid VentaDTOForInsert iVen) throws IOException, ApisPeruResponseException{
		VentaDTOForList ven = venService.realizarVentaAnonima(iVen);
		byte[] resp = venService.apisPeruPDF(ven);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
	}
}