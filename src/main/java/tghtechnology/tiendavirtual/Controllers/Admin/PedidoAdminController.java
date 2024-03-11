package tghtechnology.tiendavirtual.Controllers.Admin;

import java.io.IOException;
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

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Services.PedidoService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruResponse;
import tghtechnology.tiendavirtual.Utils.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.Utils.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.dto.Pedido.PedidoDTOForInsert;
import tghtechnology.tiendavirtual.dto.Pedido.PedidoDTOForList;

@RequestMapping("/admin/pedido")
@RestController
public class PedidoAdminController {

	@Autowired
    private PedidoService pedService;
	
	@Empleado
	@GetMapping
    public ResponseEntity<List<PedidoDTOForList>> listarPedido(){
            List<PedidoDTOForList> peds = pedService.listarPedido();
            return ResponseEntity.status(HttpStatus.OK).body(peds);
    }
	
	@Empleado
	@GetMapping("/{id}")
	public ResponseEntity<PedidoDTOForList> ListarUno(@PathVariable Integer id) {
	        PedidoDTOForList ped = pedService.listarUno(id);

	        if (ped != null) {
	            return ResponseEntity.status(HttpStatus.OK).body(ped);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	}
	
	@Empleado
	@PostMapping    
    public ResponseEntity<PedidoDTOForList> crearPedido(@Valid @RequestBody PedidoDTOForInsert pedido){

            PedidoDTOForList newPedido = pedService.crearPedido(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPedido);
            
    }
	
	@Gerente
	@PostMapping("/apisperu")
    public ResponseEntity<ApisPeruResponse> subirPedidoAP(@Valid @RequestBody PedidoDTOForInsert pedido) throws IOException, ApisPeruResponseException{

		ApisPeruResponse newPedido = pedService.subirPedidoAP(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPedido);
            
    }
	
	@Gerente
	@PostMapping("/apisperu/upload")
    public ResponseEntity<Boleta> crearPedidoAP(@Valid @RequestBody PedidoDTOForInsert pedido) throws IOException, ApisPeruResponseException{

		Boleta bol = pedService.crearPedidoAP(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(bol);
            
    }
	
	@Gerente
	@PostMapping("/apisperu/pdf")
    public ResponseEntity<byte[]> crearPedidoAPpdf(@Valid @RequestBody PedidoDTOForInsert pedido) throws IOException, ApisPeruResponseException, MessagingException{

		byte[] pdf = pedService.subirPedidoAPpdf(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pdf);
            
    }
	
    @Gerente
	@PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPedido(@PathVariable Integer id, @Valid @RequestBody PedidoDTOForInsert body){
        /*if(body.getNombre_cliente() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }*/

        pedService.actualizarPedido(id, body);
        return ResponseEntity.status(HttpStatus.OK).build();    
    }
	
    @Empleado
	@PutMapping("/{id}/{estado}")
    public ResponseEntity<Void> modificarEstadoPedido(@PathVariable Integer id, @PathVariable EstadoPedido estado){
        pedService.modificarEstadoPedido(id, estado);
        return ResponseEntity.status(HttpStatus.OK).build();    
    }
    
    @Gerente
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id){
            pedService.eliminarPedido(id);
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
