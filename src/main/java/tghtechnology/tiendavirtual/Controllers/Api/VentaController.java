package tghtechnology.tiendavirtual.Controllers.Api;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Security.Interfaces.Cliente;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.VentaService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.tiendavirtual.Utils.izipay.IzipayService;
import tghtechnology.tiendavirtual.Utils.izipay.IzipayTranslatorService;
import tghtechnology.tiendavirtual.Utils.izipay.DTO.PaymentDTO;
import tghtechnology.tiendavirtual.Utils.izipay.DTO.PaymentValidationDTO;
import tghtechnology.tiendavirtual.Utils.izipay.Exceptions.IzipayResponseException;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.IzipayCharge;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.Response.IzipayResponse;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.Response.Answers.FormToken;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForListMinimal;

@AllArgsConstructor
@RequestMapping("/api/venta")
@RestController
public class VentaController {

    private VentaService venService;
    
    private IzipayTranslatorService iziTranslator;
    private IzipayService iziService;
	
	@PostMapping("/createPayment")
	public ResponseEntity<PaymentDTO> enviarPedidoIzipay(@Valid @RequestBody VentaDTOForInsert iVen, Authentication auth) throws JsonMappingException, JsonProcessingException, IzipayResponseException {
		Venta ven;
		if(auth == null)
			ven = venService.realizarVentaAnonima(iVen);
		else
			ven = venService.realizarVentaCliente(iVen, auth);
		
		VentaDTOForList venta = new VentaDTOForList().from(ven);
		
		IzipayCharge charge = iziTranslator.toCharge(venta);
		IzipayResponse<FormToken> response = iziService.createPayment(charge);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(new PaymentDTO(response, ven.getUid()));
	}
	
	@PostMapping("/validatePayment")
	public ResponseEntity<PaymentValidationDTO> validarIzipay(@NotNull(message = "No puede ser nulo") @RequestBody Map<String, Object> body) throws JsonMappingException, JsonProcessingException, IzipayResponseException, InvalidKeyException, NoSuchAlgorithmException {
		
		Boolean response = iziService.validatePayment(body);
		return ResponseEntity.status(HttpStatus.OK).body(new PaymentValidationDTO(response));
	}
	
	@PostMapping("/facturar")
	public ResponseEntity<byte[]> enviarPedidoApisPeruPdf(@RequestParam(required = true, name = "uid") String text_uid) throws IOException, ApisPeruResponseException, MessagingException {

		Venta ven = venService.obtenerPorUid(text_uid);
		
		byte[] p = venService.apisPeruPDF(ven);
		return ResponseEntity.status(HttpStatus.OK).body(p);
	}
	
	
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

//	@Cliente
//	@PostMapping("/cliente")
//	public ResponseEntity<byte[]> realizarPdf(@RequestBody @Valid VentaDTOForInsert iVen,
//														Authentication auth) throws IOException, ApisPeruResponseException{
//		Venta ven = venService.realizarVentaCliente(iVen, auth);
//		venService.notificarVenta(ven);
//		byte[] resp = venService.apisPeruPDF(ven);
//		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
//	}
//	
//	//Venta anonima
//	@PostMapping("/no-cliente2")
//	public ResponseEntity<ApisPeruResponse> realizarAnonimo(@RequestBody @Valid VentaDTOForInsert iVen) throws IOException, ApisPeruResponseException{
//		Venta ven = venService.realizarVentaAnonima(iVen);
//		ApisPeruResponse resp = venService.enviarApisPeru(ven);
//		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
//	}
//	
//	//Venta test
//	@PostMapping("/no-cliente")
//	public ResponseEntity<byte[]> realizarPdfAnonimo(@RequestBody @Valid VentaDTOForInsert iVen) throws IOException, ApisPeruResponseException{
//		Venta ven = venService.realizarVentaAnonima(iVen);
//		byte[] resp = venService.apisPeruPDF(ven);
//		venService.notificarVenta(ven);
//		return ResponseEntity.status(HttpStatus.CREATED).body(resp); 
//	}
	
	
	// Cambiar estado de la venta
	@Gerente
	@PutMapping("/{id}")
	public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id, @RequestParam(name = "estado") EstadoPedido estado) {
		venService.cambiarEstado(id, estado);
		return ResponseEntity.status(HttpStatus.OK).build(); 
	}
	
	// Cancelar una venta
	@Gerente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancelarVenta(@PathVariable Integer id, Authentication authentication) {
		venService.cancelarVenta(id, authentication);
		return ResponseEntity.status(HttpStatus.OK).build(); 
	}
	
}