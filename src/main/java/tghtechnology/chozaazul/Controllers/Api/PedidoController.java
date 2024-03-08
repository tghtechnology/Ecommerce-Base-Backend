package tghtechnology.chozaazul.Controllers.Api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import tghtechnology.chozaazul.Services.PedidoService;
import tghtechnology.chozaazul.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.chozaazul.dto.Pedido.PedidoDTOForInsert;

@RequestMapping("/api/pedido")
@RestController
public class PedidoController {

	@Autowired
    private PedidoService pedService;
	
	@PostMapping
    public ResponseEntity<byte[]> crearPedidoAPpdf(@Valid @RequestBody PedidoDTOForInsert pedido) throws IOException, ApisPeruResponseException, MessagingException{

		byte[] pdf = pedService.subirPedidoAPpdf(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pdf);
            
    }
}
