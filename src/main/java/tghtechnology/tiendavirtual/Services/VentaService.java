package tghtechnology.tiendavirtual.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Repository.ClienteRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Repository.VentaRepository;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForListMinimal;

@Service
@AllArgsConstructor
public class VentaService {

	VentaRepository venRepository;
	UsuarioRepository userRepository;
	ClienteRepository cliRepository;
	
	
	/**
	 * Lista todas las ventas de un cliente en formato DTOForList (Minimal).
	 * <p>
	 * Utiliza la autorización de usuario para obtener la persona y
	 * sus respectivas ventas.
	 * 
	 * @param auth La autenticación del usuario
	 * @throws IdNotFoundException si la autenticación no se corresponde con un usuario
	 * o si el usuario no se corresponde con ningún cliente.
	 * @return Una lista de las ventas.
	 */
	public List<VentaDTOForListMinimal> listarVentasPorUsuario(Authentication auth) {
		Usuario user = user_buscarPorUsername(auth.getName());
		return listarVentasPorUsuario(user.getPersona().getId_persona());
	}
	
	/**
	 * Lista todas las ventas de un cliente en formato DTOForList (Minimal).
	 * <p>
	 * Utiliza una ID de persona para obtener las ventas.
	 * 
	 * @param id_persona La ID de la persona.
	 * @throws IdNotFoundException Si la ID no se corresponde con ningún cliente.
	 * @return Una lista de las ventas.
	 */
	public List<VentaDTOForListMinimal> listarVentasPorUsuario(Integer id_persona){
		List<VentaDTOForListMinimal> ventas = new ArrayList<>();
		
		Cliente cli  = cli_buscarPorId(id_persona);
		
		cli.getVentas().stream().sorted().forEach(ven -> {
			ventas.add(new VentaDTOForListMinimal().from(ven));
		});
		
		return ventas;
	}
	
	/**
	 * Lista una venta en particular en formato DTOForList.
	 * 
	 * @param id_venta La ID de la venta por listar
	 * @param auth La autenticación del usuario
	 * @return La venta en formato DTOForList
	 * @throws AccessDeniedException Si la venta no existe, si 
	 */
	public VentaDTOForList listarVenta(Integer id_venta, Authentication auth) {
		
		Venta ven = venRepository.listarUno(id_venta).orElse(null);
		
		if(ven == null || !checkPermitted(ven.getCliente(), auth)) 
			throw new AccessDeniedException("");
		
		return new VentaDTOForList().from(ven);
	}
	
	
	public void realizarVenta(Authentication auth) {
		
		Usuario user = user_buscarPorUsername(auth.getName());
		
		Cliente cli  = cli_buscarPorId(user.getPersona().getId_persona());
		
		
		
	}
	
	
	private boolean checkPermitted(Cliente cli, Authentication auth) {
    	return (cli != null && auth.getName().equals(cli.getUsuario().getUsername())) // Si la venta tiene un cliente y ese cliente es el que esta solicitando
    			|| TipoUsuario.checkRole(auth.getAuthorities(), TipoUsuario.GERENTE); // O si es un gerente quien hace la solicitud.
    }
	
	private Venta buscarPorId(Integer id) {
		return venRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("venta"));
	}
	
	private Usuario user_buscarPorUsername(String username) {
		return userRepository.listarPorUserName(username).orElseThrow( () -> new IdNotFoundException("usuario"));
	}
	
	private Cliente cli_buscarPorId(Integer id) {
		return cliRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("cliente"));
	}
	
}
