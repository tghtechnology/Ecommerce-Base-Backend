package tghtechnology.tiendavirtual.Controllers.Api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Security.Interfaces.Cliente;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.VentaService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.APTranslatorService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
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
	public ResponseEntity<List<VentaDTOForListMinimal>> listarPropios(@RequestParam(defaultValue = "1", name = "page") Integer page,
														Authentication auth){
		
		List<VentaDTOForListMinimal> ventas = venService.listarVentasPorUsuario(page, auth);
		return ResponseEntity.status(HttpStatus.OK).body(ventas);
	}
	
	@Gerente
	@GetMapping("/admin")
	public ResponseEntity<List<VentaDTOForListMinimal>> listarAdmin(@RequestParam(defaultValue = "1", name = "page") Integer page,
														@RequestParam(required = false, name = "id_cliente") Integer id_cliente){
		
		List<VentaDTOForListMinimal> ventas = venService.listarAdmin(page, id_cliente);
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
	
	//Venta anonima
//	@PostMapping("/no-cliente2")
//	public ResponseEntity<ApisPeruResponse> realizarAnonimo(@RequestBody @Valid VentaDTOForInsert iVen) throws IOException, ApisPeruResponseException{
//		Venta ven = venService.realizarVentaAnonima(iVen);
//		ApisPeruResponse resp = venService.enviarApisPeru(ven);
//		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
//	}
	
	@PostMapping("/no-cliente4")
	public ResponseEntity<Map<String, Object>> culqiTest(@RequestBody @Valid VentaDTOForInsert iVen) throws Exception{
		Venta ven = venService.realizarVentaAnonima(iVen);
		VentaDTOForList vd = new VentaDTOForList().from(ven);
		Map<String, Object> test = venService.test_culqi(iVen, vd.getPrecio_total());
		return ResponseEntity.status(HttpStatus.CREATED).body(test); 
	}
	
	@PostMapping("/no-cliente3")
	public ResponseEntity<Map<String, Object>> culqiCargo(@RequestBody @Valid VentaDTOForInsert iVen) throws Exception{
		Venta ven = venService.realizarVentaAnonima(iVen);
		VentaDTOForList vd = new VentaDTOForList().from(ven);
		Map<String, Object> test = venService.cargo_culqi(iVen, vd.getPrecio_total());
		return ResponseEntity.status(HttpStatus.CREATED).body(test); 
	}
	
	@PostMapping("/no-cliente2")
	public ResponseEntity<Boleta> realizarAnonimo(@RequestBody @Valid VentaDTOForInsert iVen) throws IOException, ApisPeruResponseException{
		Venta ven = venService.realizarVentaAnonima(iVen);
		VentaDTOForList vd = new VentaDTOForList().from(ven);
		Boleta bol = apTranslator.toBoleta(vd);
		return ResponseEntity.status(HttpStatus.CREATED).body(bol); 
	}
	
	//Venta test
	@PostMapping("/no-cliente")
	public ResponseEntity<byte[]> realizarPdfAnonimo(@RequestBody @Valid VentaDTOForInsert iVen) throws IOException, ApisPeruResponseException{
		Venta ven = venService.realizarVentaAnonima(iVen);
		byte[] resp = venService.apisPeruPDF(ven);
		venService.notificarVenta(ven);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
	}
	
	
	// Cambiar estado de la venta
	@Gerente
	@PutMapping("/{id}")
	public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id, @RequestParam(name = "estado") EstadoPedido estado) {
		venService.cambiarEstado(id, estado);
		return ResponseEntity.status(HttpStatus.OK).build(); 
	}
	
	// Cancelar una venta
	@Cliente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancelarVenta(@PathVariable Integer id, Authentication authentication) {
		venService.cancelarVenta(id, authentication);
		return ResponseEntity.status(HttpStatus.OK).build(); 
	}
	
}