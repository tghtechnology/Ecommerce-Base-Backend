package tghtechnology.tiendavirtual.Controllers.Api;

import java.util.List;

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
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.ClienteService;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForInsert;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForList;

@RequestMapping("/api/cliente")
@RestController
public class ClienteController {

	@Autowired
    private ClienteService cliService;
	
	@Gerente
	@GetMapping
    public ResponseEntity<List<ClienteDTOForList>> listar(){
        List<ClienteDTOForList> clis = cliService.listarClientes();
        return ResponseEntity.status(HttpStatus.OK).body(clis);
    }
	
	@Cliente
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTOForList> ListarUno(@PathVariable Integer id,
														Authentication auth) {
		ClienteDTOForList cli = cliService.listarUno(id, auth);
        return ResponseEntity.status(HttpStatus.OK).body(cli);
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTOForList> crear(@RequestBody @Valid ClienteDTOForInsert iCli){
		ClienteDTOForList cli = cliService.crearEmpleado(iCli);
        return ResponseEntity.status(HttpStatus.CREATED).body(cli);
	}
	
	@Cliente
	@PutMapping("/{id}")
	public ResponseEntity<Void> actualizar(@PathVariable Integer id,
													@RequestBody @Valid ClienteDTOForInsert iCli,
													Authentication auth){
		cliService.actualizarCliente(id, iCli, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@Gerente
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id,
													Authentication auth){
		cliService.eliminarCliente(id, auth);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}