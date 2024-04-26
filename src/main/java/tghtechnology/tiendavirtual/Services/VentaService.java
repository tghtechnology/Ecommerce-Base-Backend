package tghtechnology.tiendavirtual.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Enums.SettingType;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Carrito;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Models.DetalleVenta;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Repository.ClienteRepository;
import tghtechnology.tiendavirtual.Repository.DetalleCarritoRepository;
import tghtechnology.tiendavirtual.Repository.DetalleVentaRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Repository.VentaRepository;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoComprobante;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.APTranslatorService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.ApisPeruService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruResponse;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.Utils.Sockets.SocketIOService;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForListMinimal;

@Service
@AllArgsConstructor
public class VentaService {

	VentaRepository venRepository;
	DetalleVentaRepository dvRepository;
	DetalleCarritoRepository dcRepository;
	UsuarioRepository userRepository;
	ClienteRepository cliRepository;
	ItemRepository itemRepository;
	
	APTranslatorService apTranslator;
	ApisPeruService apService;
	SocketIOService socketService;
	SettingsService settings;
	
	public List<VentaDTOForListMinimal> listarVentas(Integer pagina) {

		if(pagina < 1) throw new DataMismatchException("pagina", "No puede ser menor a 1");
        Pageable pag = PageRequest.of(pagina-1, settings.getInt("paginado.venta"));
		
		List<Venta> ventas = venRepository.listar(pag);
		
		return ventas
				.stream()
				.map(ven -> new VentaDTOForListMinimal().from(ven))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * Lista todas las ventas de un cliente en formato DTOForList (Minimal).
	 * <p>
	 * Utiliza la autorización de usuario para obtener la persona y
	 * sus respectivas ventas.
	 * 
	 * @param page La página a seleccionar (Tamaño de página configurable mediante {@link tghtechnology.tiendavirtual.Services.SettingsService SettingsService})
	 * @param auth La autenticación del usuario
	 * @throws IdNotFoundException si la autenticación no se corresponde con un usuario
	 * o si el usuario no se corresponde con ningún cliente.
	 * @return Una lista de las ventas.
	 */
	public List<VentaDTOForListMinimal> listarVentasPorUsuario(Integer page, Authentication auth) {
		Usuario user = user_buscarPorUsername(auth.getName());
		return listarVentasPorUsuario(page, user.getPersona().getId_persona());
	}
	
	public List<VentaDTOForListMinimal> listarAdmin(Integer page, Integer id_persona){
		if(id_persona != null)
			return listarVentasPorUsuario(page, id_persona);
		else
			return listarVentas(page);
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
	public List<VentaDTOForListMinimal> listarVentasPorUsuario(Integer pagina, Integer id_persona){
		final List<VentaDTOForListMinimal> ventas = new ArrayList<>();
		
		if(pagina < 1) throw new DataMismatchException("pagina", "No puede ser menor a 1");
        
        Pageable pag = PageRequest.of(pagina-1, settings.getInt("paginado.venta"));
		Cliente cli  = cli_buscarPorId(id_persona);
		
		List<Venta> listaVentas = venRepository.listarPorCliente(cli, pag);
		
		listaVentas.forEach(vv -> {
			ventas.add(new VentaDTOForListMinimal().from(vv));
		});
		
		return ventas;
	}
	
	/**
	 * Lista una venta en particular en formato DTOForList.
	 * 
	 * @param id_venta La ID de la venta por listar
	 * @param auth La autenticación del usuario
	 * @return La venta en formato DTOForList
	 * @throws AccessDeniedException Si la venta no existe
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
	public Venta realizarVentaCliente(VentaDTOForInsert venta, Authentication auth) {
		Usuario user = user_buscarPorUsername(auth.getName());
		Cliente cli  = cli_buscarPorId(user.getPersona().getId_persona());
		Carrito car = user.getCarrito();
		List<DetalleVenta> dets = new ArrayList<>();
		
		if(car.getDetalles().isEmpty()) throw new DataMismatchException("carrito", "No se puede realizar una compra con el carrito vacío");
		
		Venta ven = venta.toModel();
		ven.setCliente(cli);
		ven.setPorcentaje_igv(settings.getInt("facturacion.igv"));
		ven.setAntes_de_igv(settings.getBoolean("facturacion.antes_de_igv"));
		// Guardando venta para obtener una ID
		final Venta v = venRepository.save(ven);
		// Añadiendo items del carrito a la venta
		car.getDetalles().forEach(det -> {
			validarDetalle(det.getItem(), det.getCantidad().shortValue());
			DetalleVenta dv = procesarDetalle(det.getItem(), det.getCantidad().shortValue());
			dv.setVenta(v);
			dv = dvRepository.save(dv);
			dets.add(dv);
		});
		ven = v;
		ven.getDetalles().addAll(dets);
		
		// Vaciar carrito del cliente
		dcRepository.deleteAll(car.getDetalles());
		
		return ven;
	}
	
	/**
	 * Realiza una venta tomando de base el carrito del DTOForInsert
	 * para obtener los productos del carrito de venta.
	 * <p>
	 * <strong>NO</strong> requiere autenticación.
	 * @param venta Los datos de la venta en formato ForInsert
	 * @return La venta realizada en formato DTOForList
	 * @throws IdNotFoundException Si no se encontró la ID de alguna de las variaciones proporcionadas.
	 */
	@Transactional(rollbackFor = {Exception.class})
	public Venta realizarVentaAnonima(VentaDTOForInsert venta) {
		List<DetalleVenta> dets = new ArrayList<>();
		Venta ven = venta.toModel();
		ven.setCorrelativo(nextComprobante(ven.getTipo_comprobante()));
		ven.setPorcentaje_igv(settings.getInt("facturacion.igv"));
		ven.setAntes_de_igv(settings.getBoolean("facturacion.antes_de_igv"));
		// Guardando venta para obtener una ID
		final Venta v = venRepository.save(ven);
		// Añadiendo items del carrito a la venta
		if(venta.getCarrito() == null || venta.getCarrito().isEmpty())
			throw new DataMismatchException("carrito", "No puede estar vacío");
		
		venta.getCarrito().forEach(vv -> {
			Item item = item_buscarPorId(vv.getId_item());
			
			validarDetalle(item, vv.getCantidad());
			
			DetalleVenta dv = procesarDetalle(item, vv.getCantidad());
			dv.setVenta(v);
			dv = dvRepository.save(dv);
			dets.add(dv);
		});
		
		ven = v;
		ven.getDetalles().addAll(dets);
		
		return ven;
	}
	
	/**
	 * Envía un pedido a ApisPeru y devuelve la respuesta de la sunat.
	 * @param venta La venta realizada en formato ForList.
	 * @return La respuesta de la sunat en formato <strong>xml</strong>.
	 * @throws IOException Si hay algún problema al transcribir la solicitud o recibir la respuesta de ApisPeru
	 * @throws ApisPeruResponseException Si ApisPeru responde con un error.
	 */
	public ApisPeruResponse enviarApisPeru(Venta venta) throws IOException, ApisPeruResponseException {
		VentaDTOForList vd = new VentaDTOForList().from(venta);
		Boleta bol = apTranslator.toBoleta(vd);
		return apService.enviarBoleta(bol);
	}
	
	/**
	 * Envía un pedido a ApisPeru y devuelve la boleta en formato <strong>PDF</strong> como un bytearray.
	 * @param venta La venta realizada en formato ForList
	 * @return La boleta correspondiente al pedido como un bytearray.
	 * @throws ApisPeruResponseException Si ApisPeru responde con un error.
	 */
	public byte[] apisPeruPDF(Venta venta) throws ApisPeruResponseException {
		VentaDTOForList vd = new VentaDTOForList().from(venta);
		Boleta bol = apTranslator.toBoleta(vd);
		return apService.enviarBoletaPdf(bol);
	}
	
	/**
	 * Notifica a todos los encargados que esten logueados sobre la nueva venta
	 * @param venta La venta a notificar en formato DTOForList
	 * @throws JsonProcessingException Si ocurre algún error al realizar el mapeo hacia JSON
	 */
	public void notificarVenta(Venta venta) throws JsonProcessingException {
		VentaDTOForListMinimal vd = new VentaDTOForListMinimal().from(venta);
		ObjectMapper om = new ObjectMapper();
		om.registerModule(new JavaTimeModule());
		String mapped = om.writeValueAsString(vd);
		
		socketService.broadcast("ventas", mapped);
	}
	
	/**
	 * Cambia el estado de una venta.<br>
	 * Si el estado es cambiado a CANCELADO, también se reestablece el stock de los items incluidos en la compra.
	 * @param id_venta ID de la venta a cambiar
	 * @param estado Estado al que cambiar la venta
	 * @throws DataMismatchException Si se intenta cambiar el estado de una venta finalizada (Cancelada o Completada)
	 */
	public void cambiarEstado(Integer id_venta, EstadoPedido estado) {
		
		Venta ven = buscarPorId(id_venta);
		
		if(ven.getEstado_pedido() == EstadoPedido.CANCELADO || ven.getEstado_pedido() == EstadoPedido.COMPLETADO) {
			throw new DataMismatchException("estado", "No se puede cambiar el estado de una venta completada");
		}
		
		ven.setEstado_pedido(estado);
		
		// Restablcer el stock de los pedidos si la venta fue cancelada
		if(estado == EstadoPedido.CANCELADO) {
			ven.getDetalles().forEach(dv -> {
				Item item = item_buscarPorId(dv.getId_item());
				item.setStock(item.getStock() + dv.getCantidad());
				if(item.getDisponibilidad() == DisponibilidadItem.SIN_STOCK)
					item.setDisponibilidad(DisponibilidadItem.DISPONIBLE);
				itemRepository.save(item);
			});
		}
		
		venRepository.save(ven);
	}
	
	/**
	 * Cancela una venta si es que no se ha completado anteriormente.<br>
	 * También reestablece el stock de los items comprados.
	 * @param id_venta La ID de la venta a cancelar.
	 * @param auth La autenticación del usuario.
	 * @throws DataMismatchException Si se intenta cambiar el estado de una venta finalizada (Cancelada o Completada)
	 */
	public void cancelarVenta(Integer id_venta, Authentication auth) {
		
		Venta ven = venRepository.listarUno(id_venta).orElse(null);
		
		if(ven == null || !checkPermitted(ven.getCliente(), auth)) 
			throw new AccessDeniedException("");
		
		cambiarEstado(id_venta, EstadoPedido.CANCELADO);
		
	}
	
	private String nextComprobante(TipoComprobante tc) {
		String tipo = tc == TipoComprobante.BOLETA_DE_VENTA ? "facturacion.serie_boleta" : "facturacion.serie_factura";
		
		// Obtener el numero de comprobante
		Integer numComprobante = settings.getInt(tipo);
		// Aumentar el numero en 1 para el siguiente
		settings.alterSetting(tipo, numComprobante+1, SettingType.INT);
		
		return numComprobante.toString();
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
	
	private Item item_buscarPorId(Integer id) {
		return itemRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("item"));
	}
	
	private DetalleVenta procesarDetalle(Item item, Short cantidad) {
		DetalleVenta dv = new DetalleVenta();
		
		dv.setId_item(item.getId_item());
		dv.setNombre_item(item.getNombre());
		dv.setPrecio_unitario(item.getPrecio());
		dv.setCosto_unitario(item.getCosto());
		dv.setCantidad(cantidad.shortValue());
		if(item.getDescuento() != null)
			dv.setPorcentaje_descuento(item.getDescuento().getPorcentaje());
		else
			dv.setPorcentaje_descuento(0);
		
		//Disminuir el stock
		item.setStock(item.getStock()-cantidad);
		if(item.getStock() == 0)
			item.setDisponibilidad(DisponibilidadItem.SIN_STOCK);
		itemRepository.save(item);
		
		return dv;
	}
	
	private void validarDetalle(Item item, Short cantidad) {
		
		// Validar disponibilidad de item y variación
		if(item.getDisponibilidad() != DisponibilidadItem.DISPONIBLE)
			throw new DataMismatchException("item", "No está disponible para la venta.");
		
		// Validar stock de item
		if(item.getStock() < cantidad)
			throw new DataMismatchException("item", "No hay stock suficiente.");
	}
	
}
