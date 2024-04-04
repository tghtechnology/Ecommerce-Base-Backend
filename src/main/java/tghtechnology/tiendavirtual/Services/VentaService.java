package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Models.DetalleCarrito;
import tghtechnology.tiendavirtual.Models.DetalleVenta;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Repository.ClienteRepository;
import tghtechnology.tiendavirtual.Repository.DetalleVentaRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Repository.VariacionRepository;
import tghtechnology.tiendavirtual.Repository.VentaRepository;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.APTranslatorService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.ApisPeruService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruResponse;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForListMinimal;

@Service
@AllArgsConstructor
public class VentaService {

	VentaRepository venRepository;
	DetalleVentaRepository dvRepository;
	UsuarioRepository userRepository;
	ClienteRepository cliRepository;
	VariacionRepository varRepository;
	
	APTranslatorService apTranslator;
	ApisPeruService apService;
	SettingsService settings;
	
	
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
	
	/**
	 * Realiza una venta tomando de base un {@link tghtechnology.tiendavirtual.Models.Cliente Cliente}
	 * para obtener los productos del carrito de venta.
	 * <p>
	 * Requiere autenticación.
	 * @param venta Los datos de la venta en formato ForInsert
	 * @param auth La autenticación del cliente
	 * @return La venta realizada en formato DTOForList
	 */
	@Transactional(rollbackFor = {Exception.class})
	public VentaDTOForList realizarVentaCliente(VentaDTOForInsert venta, Authentication auth) {
		Usuario user = user_buscarPorUsername(auth.getName());
		Cliente cli  = cli_buscarPorId(user.getPersona().getId_persona());
		List<DetalleVenta> dets = new ArrayList<>();
		Venta ven = venta.toModel();
		ven.setCliente(cli);
		ven.setPorcentaje_igv(settings.getInt("facturacion.igv"));
		// Guardando venta para obtener una ID
		final Venta v = venRepository.save(ven);
		// Añadiendo items del carrito a la venta
		user.getCarrito().getDetalles().forEach(det -> {
			DetalleVenta dv = detalleCarritoAVenta(det);
			dv.setVenta(v);
			dv = dvRepository.save(dv);
			dets.add(dv);
		});
		ven = v;
		ven.getDetalles().addAll(dets);
		
		return new VentaDTOForList().from(ven);
	}
	
	/**
	 * Realiza una venta tomando de base el carrito del DTOForInsert
	 * para obtener los productos del carrito de venta.
	 * <p>
	 * <strong>NO</strong> requiere autenticación.
	 * @param venta Los datos de la venta en formato ForInsert
	 * @param auth La autenticación del cliente
	 * @return La venta realizada en formato DTOForList
	 * @throws IdNotFoundException Si no se encontró la ID de alguna de las variaciones proporcionadas.
	 */
	@Transactional(rollbackFor = {Exception.class})
	public VentaDTOForList realizarVentaAnonima(VentaDTOForInsert venta) {
		List<DetalleVenta> dets = new ArrayList<>();
		Venta ven = venta.toModel();
		ven.setPorcentaje_igv(settings.getInt("facturacion.igv"));
		// Guardando venta para obtener una ID
		final Venta v = venRepository.save(ven);
		// Añadiendo items del carrito a la venta
		if(venta.getCarrito() == null || venta.getCarrito().isEmpty())
			throw new DataMismatchException("carrito", "No puede estar vacío");
		
		venta.getCarrito().forEach(vv -> {
			DetalleVenta dv = vv.toModel();
			Variacion var = var_buscarPorId(vv.getId_variacion());
			Item itm = var.getItem();
			
			dv.setVenta(v);
			dv.setId_item(itm.getId_item());
			dv.setNombre_item(itm.getNombre());
			dv.setTipo_variacion(var.getTipo_variacion());
			dv.setValor_variacion(var.getValor_variacion());
			dv.setPrecio_unitario(var.getPrecio());
			if(itm.getDescuento() != null && var.getAplicarDescuento())
				dv.setPorcentaje_descuento(itm.getDescuento().getPorcentaje());
			else
				dv.setPorcentaje_descuento(0);
			
			dets.add(dv);
		});
		
		ven = v;
		ven.getDetalles().addAll(dets);
		
		return new VentaDTOForList().from(ven);
	}
	
	/**
	 * Envía un pedido a ApisPeru y devuelve la respuesta de la sunat.
	 * @param venta La venta realizada en formato ForList.
	 * @return La respuesta de la sunat en formato <strong>xml</strong>.
	 * @throws IOException Si hay algún problema al transcribir la solicitud o recibir la respuesta de ApisPeru
	 * @throws ApisPeruResponseException Si ApisPeru responde con un error.
	 */
	public ApisPeruResponse enviarApisPeru(VentaDTOForList venta) throws IOException, ApisPeruResponseException {
		Boleta bol = apTranslator.toBoleta(venta);
		return apService.enviarBoleta(bol);
	}
	
	/**
	 * Envía un pedido a ApisPeru y devuelve la boleta en formato <strong>PDF</strong> como un bytearray.
	 * @param venta La venta realizada en formato ForList
	 * @return La boleta correspondiente al pedido como un bytearray.
	 * @throws ApisPeruResponseException Si ApisPeru responde con un error.
	 */
	public byte[] apisPeruPDF(VentaDTOForList venta) throws ApisPeruResponseException {
		Boleta bol = apTranslator.toBoleta(venta);
		return apService.enviarBoletaPdf(bol);
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
	
	private Variacion var_buscarPorId(Integer id) {
		return varRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("variacion"));
	}
	
	private DetalleVenta detalleCarritoAVenta(DetalleCarrito dc) {
		
		Variacion var = dc.getVariacion();
		Item itm = var.getItem();
		
		DetalleVenta dv = new DetalleVenta();
		
		dv.setId_item(itm.getId_item());
		dv.setNombre_item(itm.getNombre());
		dv.setId_variacion(var.getId_variacion());
		dv.setTipo_variacion(var.getTipo_variacion());
		dv.setValor_variacion(var.getValor_variacion());
		dv.setPrecio_unitario(var.getPrecio());
		dv.setCantidad(dc.getCantidad().shortValue());
		if(itm.getDescuento() != null && var.getAplicarDescuento())
			dv.setPorcentaje_descuento(itm.getDescuento().getPorcentaje());
		else
			dv.setPorcentaje_descuento(0);
		
		return dv;
	}
	
}
