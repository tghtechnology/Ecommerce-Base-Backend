package tghtechnology.tiendavirtual.Controllers.Api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Security.Interfaces.Cliente;
import tghtechnology.tiendavirtual.Services.VentaService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.APTranslatorService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruResponse;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForListMinimal;

@RequestMapping("/api/venta")
@RestController
public class VentaController {

	@Autowired
    private VentaService venService;
	@Autowired
	APTranslatorService apTranslator;
	
	@Cliente
	@GetMapping
	public ResponseEntity<List<VentaDTOForListMinimal>> listar(@RequestParam(defaultValue = "1", name = "page") Integer page,
														Authentication auth){
		
		List<VentaDTOForListMinimal> ventas = venService.listarVentasPorUsuario(page, auth);
		return ResponseEntity.status(HttpStatus.OK).body(ventas);
	}
	
	@Cliente
	@GetMapping("/{id}")
	public ResponseEntity<VentaDTOForList> listarUno(@PathVariable Integer id, Authentication auth){
		
		VentaDTOForList venta = venService.listarVenta(id, auth);
		return ResponseEntity.status(HttpStatus.OK).body(venta);
		
	}
	
	//Venta de cliente
//	@Cliente
//	@PostMapping("/cliente")
//	public ResponseEntity<ApisPeruResponse> realizar(@RequestBody @Valid VentaDTOForInsert iVen,
//														Authentication auth) throws IOException, ApisPeruResponseException{
//		VentaDTOForList ven = venService.realizarVentaCliente(iVen, auth);
//		ApisPeruResponse resp = venService.enviarApisPeru(ven);
//		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
//	}
	
	//Venta test
	@Cliente
	@PostMapping("/cliente")
	public ResponseEntity<byte[]> realizarPdf(@RequestBody @Valid VentaDTOForInsert iVen,
														Authentication auth) throws IOException, ApisPeruResponseException{
		Venta ven = venService.realizarVentaCliente(iVen, auth);
		venService.notificarVenta(ven);
		byte[] resp = venService.apisPeruPDF(ven);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
	}
	
//	//Venta test
//	@Cliente
//	@PostMapping("/cliente-bol")
//	public ResponseEntity<Boleta> realizarBoleta(@RequestBody @Valid VentaDTOForInsert iVen,
//														Authentication auth) throws IOException, ApisPeruResponseException{
//		VentaDTOForList ven = venService.realizarVentaCliente(iVen, auth);
//		return ResponseEntity.status(HttpStatus.CREATED).body(apTranslator.toBoleta(ven)); 
//	}
	
//	//Venta anonima
//	@PostMapping("/no-cliente")
//	public ResponseEntity<ApisPeruResponse> realizarAnonimo(@RequestBody @Valid VentaDTOForInsert iVen) throws IOException, ApisPeruResponseException{
//		Venta ven = venService.realizarVentaAnonima(iVen);
//		ApisPeruResponse resp = venService.enviarApisPeru(ven);
//		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
//	}
	
	//Venta test
	@PostMapping("/no-cliente")
	public ResponseEntity<byte[]> realizarPdfAnonimo(@RequestBody @Valid VentaDTOForInsert iVen) throws IOException, ApisPeruResponseException{
		Venta ven = venService.realizarVentaAnonima(iVen);
		byte[] resp = venService.apisPeruPDF(ven);
		venService.notificarVenta(ven);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
	}
}