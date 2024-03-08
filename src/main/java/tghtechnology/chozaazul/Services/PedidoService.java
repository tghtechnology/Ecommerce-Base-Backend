package tghtechnology.chozaazul.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import tghtechnology.chozaazul.Models.Cliente;
import tghtechnology.chozaazul.Models.DetallePedido;
import tghtechnology.chozaazul.Models.Pedido;
import tghtechnology.chozaazul.Models.Plato;
import tghtechnology.chozaazul.Models.Enums.EstadoPedido;
import tghtechnology.chozaazul.Models.Enums.TipoDelivery;
import tghtechnology.chozaazul.Repository.ClienteRepository;
import tghtechnology.chozaazul.Repository.DetallePedidoRepository;
import tghtechnology.chozaazul.Repository.PedidoRepository;
import tghtechnology.chozaazul.Repository.PlatoRepository;
import tghtechnology.chozaazul.Utils.DeliveryCalculator;
import tghtechnology.chozaazul.Utils.Propiedades;
import tghtechnology.chozaazul.Utils.ApisPeru.Exceptions.ApisPeruResponseException;
import tghtechnology.chozaazul.Utils.ApisPeru.Functions.APTranslator;
import tghtechnology.chozaazul.Utils.ApisPeru.Functions.ApisPeruService;
import tghtechnology.chozaazul.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.chozaazul.Utils.ApisPeru.Objects.Response.ApisPeruResponse;
import tghtechnology.chozaazul.Utils.Emails.EmailService;
import tghtechnology.chozaazul.Utils.Emails.Formatos.ClienteEmail;
import tghtechnology.chozaazul.Utils.Emails.Formatos.GerenteEmail;
import tghtechnology.chozaazul.Utils.Exceptions.DataMismatchException;
import tghtechnology.chozaazul.Utils.Exceptions.IdNotFoundException;
import tghtechnology.chozaazul.Utils.Exceptions.PriceInconsistencyException;
import tghtechnology.chozaazul.dto.Cliente.ClienteDTOForInsert;
import tghtechnology.chozaazul.dto.Pedido.PedidoDTOForInsert;
import tghtechnology.chozaazul.dto.Pedido.PedidoDTOForList;
import tghtechnology.chozaazul.dto.Pedido.DetallePedido.DetallePedidoDTOForInsert;

@Service
@AllArgsConstructor
public class PedidoService {
	
	private Propiedades propiedades;
    private PedidoRepository pedRepository;
	private ClienteRepository cliRepository;
	private DetallePedidoRepository detRepository;
	private PlatoRepository plaRepository;
	private NotificacionService notiService;
	private EmailService emailService;
	private ApisPeruService apService;
	private DeliveryCalculator deliveryCalculator;
	
    /*Listar pedido*/
    public List<PedidoDTOForList> listarPedido (){
        List<PedidoDTOForList> pedidoList = new ArrayList<>();
        List<Pedido> peds = (List<Pedido>) pedRepository.listarPedido();
        
        peds.forEach( x -> {
            pedidoList.add(new PedidoDTOForList(x));
        });
        return pedidoList;
    }
    
    /*Obtener un pedido especifico*/
    public PedidoDTOForList listarUno( Integer id){
    	Pedido pedido = buscarPorId(id);
        return new PedidoDTOForList(pedido);
    }
    
    /**Registrar nuevo pedido*/
    public PedidoDTOForList crearPedido(PedidoDTOForInsert iPed){
    	
    	Pedido ped = regPedido(iPed);
    	notiService.registrarNotificacion(ped);
        return new PedidoDTOForList(ped);
    }
    
    /**Registrar nuevo pedido ApisPeru y evolver el formato de boleta para enviar
     * @throws ApisPeruResponseException 
     * @throws IOException */
    public Boleta crearPedidoAP(PedidoDTOForInsert iPed) throws IOException, ApisPeruResponseException{
    	
    	Pedido ped = regPedido(iPed);
        Boleta boletaAP = APTranslator.pedidoToApiBoleta(ped, iPed.getCliente(), LocalDateTime.now(), propiedades.getIgv(), propiedades.getAntesDeIGV());
        notiService.registrarNotificacion(ped);
		return boletaAP;
    }
    
    /**Registrar nuevo pedido ApisPeru y recibir falsa respuesta de sunat
     * @throws ApisPeruResponseException 
     * @throws IOException */
    public ApisPeruResponse subirPedidoAP(PedidoDTOForInsert iPed) throws IOException, ApisPeruResponseException{
    	
    	Pedido ped = regPedido(iPed);
        Boleta boletaAP = APTranslator.pedidoToApiBoleta(ped, iPed.getCliente(), LocalDateTime.now(), propiedades.getIgv(), propiedades.getAntesDeIGV());
        notiService.registrarNotificacion(ped);
		return apService.enviarBoleta(boletaAP);
    }
    
    /**Registrar nuevo pedido ApisPeru y recibir un pdf
     * @throws ApisPeruResponseException 
     * @throws IOException 
     * @throws MessagingException */
    public byte[] subirPedidoAPpdf(PedidoDTOForInsert iPed) throws IOException, ApisPeruResponseException, MessagingException{
    	
    	Pedido ped = regPedido(iPed);
        Boleta boletaAP = APTranslator.pedidoToApiBoleta(ped, iPed.getCliente(), LocalDateTime.now(), propiedades.getIgv(), propiedades.getAntesDeIGV());
        byte[] boletaPDF = apService.enviarBoletaPdf(boletaAP);
        
        notiService.registrarNotificacion(ped);
        emailService.enviarEmail(new ClienteEmail(iPed.getCliente().getEmail(), ped, boletaPDF));
        emailService.enviarEmail(new GerenteEmail(propiedades.getEmailGerente(), ped, boletaPDF));
        
		return boletaPDF;
    }
    /*Actualizar pedido */
    public void actualizarPedido(Integer id, PedidoDTOForInsert body){
    	Pedido pedido = buscarPorId(id);
        pedido.setFecha_pedido(LocalDateTime.now());
        pedido.setEstado_pedido(EstadoPedido.PENDIENTE);
        pedido.setPrecio_total(body.getPrecio_total());
        pedido.setTipo_delivery(body.getTipo_delivery());
        pedido.setCosto_delivery(costoPorTipoDelivery(body.getTipo_delivery()));
        pedido.setAntesDeIGV(true);
        
        ClienteDTOForInsert body_cliente = body.getCliente();
        Cliente cliente = pedido.getCliente();
        
        cliente.setTipo_documento(body_cliente.getTipo_documento());
        cliente.setNumero_documento(body_cliente.getNumero_documento());
        cliente.setDistrito(body_cliente.getDistrito());
        cliente.setDireccion(body_cliente.getDireccion());
        cliente.setReferencia(body_cliente.getReferencia());
        
        cliente = cliRepository.save(cliente);
        pedido.setCliente(cliente);
        
        
        
        pedRepository.save(pedido);
    }
    
	// Modificar estado del pedido
	public void modificarEstadoPedido(Integer id, EstadoPedido est) {
		// Buscar el pedido a modificar
		Pedido ped = buscarPorId(id);
		//modificar su estado
		ped.setEstado_pedido(est);
		ped = pedRepository.save(ped);
		
	}
    
    /**Eliminar pedido */
    public void eliminarPedido(Integer id){
        Pedido ped = buscarPorId(id);
        ped.setEstado(false);
        pedRepository.save(ped);
    }
    
    private Pedido regPedido(PedidoDTOForInsert iPed) {
    	
    	validarPedido(iPed);
    	
    	Pedido ped = new Pedido();
    	ped.setTipo_comprobante(iPed.getTipo_comprobante());
        ped.setFecha_pedido(LocalDateTime.now());
        ped.setEstado_pedido(EstadoPedido.PENDIENTE);
        ped.setPrecio_total(iPed.getPrecio_total());
        ped.setTipo_delivery(iPed.getTipo_delivery());
        ped.setCosto_delivery(costoPorTipoDelivery(iPed.getTipo_delivery()));
        ped.setAntesDeIGV(true);
        ped.setEstado(true);
        
        ClienteDTOForInsert iCli = iPed.getCliente();
        Cliente cliente = new Cliente();
        
        cliente.setTipo_documento(iCli.getTipo_documento());
        cliente.setNumero_documento(iCli.getNumero_documento());
        cliente.setCorreo(iCli.getEmail());
        cliente.setNombre(iCli.getNombre());
        cliente.setTelefono(iCli.getTelefono());
        cliente.setDistrito(iCli.getDistrito());
        cliente.setDireccion(iCli.getDireccion());
        cliente.setReferencia(iCli.getReferencia());
        
        cliente = cliRepository.save(cliente);
        ped.setCliente(cliente);
        
        ped = pedRepository.save(ped);
        
        if(iPed.getDetalles() != null) {
        	final List<DetallePedido> detalles = new ArrayList<>();
        	detRepository.saveAll(insertarDetalles(ped, iPed.getDetalles()))
        	.forEach(d -> detalles.add(d));
        	ped.getDetallePedido().addAll(detalles);
        }
        
        return pedRepository.save(ped);
    }
    
    private List<DetallePedido> insertarDetalles(Pedido ped, Set<DetallePedidoDTOForInsert> idet) {
    	List<DetallePedido> dets = new ArrayList<>();
    	for(DetallePedidoDTOForInsert det : idet) {
    		
    		DetallePedido d = new DetallePedido();
    		d.setPedido(ped);
    		d.setCantidad(det.getCantidad());
    		d.setSub_total(det.getSubtotal());
    		d.setPlato(plaRepository.listarUno(det.getId_plato()).orElseThrow(() -> new IdNotFoundException("plato")));
    		
    		dets.add(d);    		
    	}
    	
    	return dets;
    }
    
    private Pedido buscarPorId(Integer id) {
		return pedRepository.listarUno(id).orElseThrow( () -> new IdNotFoundException("pedido"));
    }
    
    
	private void validarPedido(PedidoDTOForInsert ip) {
		BigDecimal totalEsperado = new BigDecimal(0);
		
		for(DetallePedidoDTOForInsert sp : ip.getDetalles()) {
			// Obteniendo precio y oferta
			final Plato prod = plaRepository.listarUno(sp.getId_plato()).orElseThrow(() -> new IdNotFoundException("plato"));
			//final LocalDate today = LocalDate.now();
			BigDecimal subtotalEsperado = prod.getPrecio();
			// Validando subtotal
			subtotalEsperado = subtotalEsperado.multiply(new BigDecimal(sp.getCantidad()));
			
			if(subtotalEsperado.compareTo(sp.getSubtotal()) == 0) {
				// Sumar al total
				totalEsperado = totalEsperado.add(sp.getSubtotal());
				
			} else {
				// inconsistencia encontrada
				throw new PriceInconsistencyException(subtotalEsperado, sp.getSubtotal());
			}
		}		
		
		// Validar delivery
		BigDecimal delivery = costoPorTipoDelivery(ip.getTipo_delivery());
		double distancia;
		switch(ip.getTipo_delivery()) {
		case DELIVERY_CORTO:
			if(ip.getCliente().getLatitud() == null || ip.getCliente().getLongitud() == null)
					throw new DataMismatchException("latitud|longitud", "datos invalidos");
			
			distancia = deliveryCalculator.distance(ip.getCliente().getLatitud(), ip.getCliente().getLongitud());
//			System.out.println("Tipo: " + ip.getTipo_delivery().toString() + ", Distancia: " + distancia);
			if( distancia > 1000L + deliveryCalculator.getTolerancia()) {
				throw new DataMismatchException("delivery", "Distancia no coincide con el tipo de delivery");
			}
			break;
		case DELIVERY_LARGO:
			if(ip.getCliente().getLatitud() == null || ip.getCliente().getLongitud() == null)
				throw new DataMismatchException("latitud|longitud", "datos invalidos");
			
			distancia = deliveryCalculator.distance(ip.getCliente().getLatitud(), ip.getCliente().getLongitud());
//			System.out.println("Tipo: " + ip.getTipo_delivery().toString() + ", Distancia: " + distancia);
			if(distancia > 5000L + deliveryCalculator.getTolerancia()) {
				throw new DataMismatchException("delivery", "Distancia no coincide con el tipo de delivery");
			}
			break;
		default:
		}
		
		totalEsperado = totalEsperado.add(delivery);
				
		// Validar total
		if(ip.getPrecio_total().compareTo(totalEsperado) != 0) {
			// inconsistencia encontrada
			throw new PriceInconsistencyException(totalEsperado, ip.getPrecio_total());
		}
		
	}
	
	private BigDecimal costoPorTipoDelivery(TipoDelivery tipo) {
		return new BigDecimal(0);
//		switch(tipo) {
//		case DELIVERY_CORTO:
//			return propiedades.getPrecioDeliveryCorto();
//		case DELIVERY_LARGO:
//			return propiedades.getPrecioDeliveryLargo();
//		default:
//			return new BigDecimal(0);
//		}
	}
    
    
}
