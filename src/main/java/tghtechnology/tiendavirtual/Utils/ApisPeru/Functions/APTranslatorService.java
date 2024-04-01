package tghtechnology.tiendavirtual.Utils.ApisPeru.Functions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Utils.Propiedades;
import tghtechnology.tiendavirtual.Utils.ApisPeru.CompanyData;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.AfectacionIGV;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Leyenda;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoCargo;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoOperacion;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.UnidadMedida;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Address;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Charge;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Client;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.SaleDetail;
import tghtechnology.tiendavirtual.dto.Venta.ClienteVentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.DetalleVentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForList;

@Service
@AllArgsConstructor
public class APTranslatorService {
	
	private Propiedades propiedades;
	private CompanyData companyData;
	
	public Boleta toBoleta(VentaDTOForList ven) {
		
		final Client client = toClient(ven.getCliente());
				
		final List<Leyenda> legends = new ArrayList<>();
		legends.add(Leyenda.MONTO_EN_LETRAS);
		
		final List<Charge> descuentos = new ArrayList<>();
		final List<Charge> cargos = new ArrayList<>();
		
		final List<SaleDetail> detalles = new ArrayList<>();
		ven.getDetalles().forEach(d -> detalles.add(toSaleDetail(d)));
		
		return new Boleta(
				TipoOperacion.VENTA_INTERNA,
				ven.getTipo_comprobante(),
				ven.getId_venta(), //TODO: Cambiar por correlativo de boletas/facturas
				ven.getFecha_realizacion(),
				client,
				companyData.getCompanyData(),
				BigDecimal.ZERO, // Suma de otros cargos
				ven.getAntes_de_igv(),
				detalles,
				legends,
				ven.getObservacion(),
				null,
				descuentos,
				cargos
				);
	}

	public SaleDetail toSaleDetail(DetalleVentaDTOForList det) {
		List<Charge> descuentos = new ArrayList<>();
		List<Charge> cargos = new ArrayList<>();
		
		if(det.getPorcentaje_descuento() != null && det.getPorcentaje_descuento() != 0) {
			descuentos.add(toDescuento(det));
		}
		
		return new SaleDetail(
				UnidadMedida.UNIDAD_BIENES,
				det.getCantidad(),
				String.format("PROD-%04d%02d",det.getId_item(), det.getId_variacion()),
				det.getNombre_item()
					.concat(det.getTipo_variacion().getDescripcion())
					.concat(" ")
					.concat(det.getValor_variacion()),
				det.getPrecio_unitario(),
				new BigDecimal(propiedades.getIgv()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP),
				AfectacionIGV.GRAVADO_OPERACION_ONEROSA,
				BigDecimal.ZERO,
				descuentos,
				cargos
				);
	}
	
	public Client toClient(ClienteVentaDTOForList cli) {
		return new Client(
				cli.getTipo_documento(),
				cli.getNumero_documento(),
				cli.getRazon_social(),
				toAddress(cli),
				cli.getCorreo(),
				cli.getTelefono()
				);
	}
	
	public Address toAddress(ClienteVentaDTOForList cli) {
		return new Address(
				"", //TODO obtener ubigeo
				cli.getRegion(),
				cli.getProvincia(),
				cli.getDistrito(),
				cli.getDireccion()
				);
	}
	
	public Charge toDescuento(DetalleVentaDTOForList det) {
		final BigDecimal factor = new BigDecimal(det.getPorcentaje_descuento()).divide(new BigDecimal(100));
		final BigDecimal montoBase = det.getPrecio_unitario().multiply(new BigDecimal(det.getCantidad()));
		return new Charge(
				TipoCargo.DESC_AFECTA_BASE_IMPONIBLE_IGV_IVAP,
				factor,
				montoBase,
				montoBase.multiply(factor)
				);
		
		
	}
	
/*
	
	public static Boleta pedidoToApiBoleta(Pedido ped, ClienteDTOForInsert cli, LocalDateTime fechaEmision, BigDecimal igv, Boolean antesDeIGV) {
		
		final Client client = clienteDTOtoApiClient(cli);
		final List<SaleDetail> details = new ArrayList<>();
		final List<Leyenda> legends = new ArrayList<>();
		final List<Charge> descuentos = new ArrayList<>();
		final List<Charge> cargos = new ArrayList<>();
		
		final CompanyData companyData = new CompanyData();
		
		ped.getDetallePedido().forEach(sp -> details.add(subpedidoToApiDetail(sp, igv, antesDeIGV)));
		
		legends.add(Leyenda.MONTO_EN_LETRAS);
		
		BigDecimal total = ped.getPrecio_total();//.subtract(ped.getCostoDelivery()), TODO añadir costo de delivery
		if(!antesDeIGV) total = total.divide(BigDecimal.ONE.setScale(2).add(igv), 2, RoundingMode.UP); // total = total * 1-igv -> total - 0.82
		
		return new Boleta(
				TipoOperacion.VENTA_INTERNA, // tipoOperacion
				ped.getTipo_comprobante(),
				ped.getId_pedido(),
				fechaEmision,
				client,
				companyData.getCompanyData(),
				new BigDecimal(0),
				total,
				details,
				legends.isEmpty() ? null : legends,
				"",		//observacion
				(ped.getTipo_delivery() == null || ped.getTipo_delivery() == TipoDelivery.EN_TIENDA) ? null : direccionDTOToApiAddress(cli),	// direccion de la entrega
				descuentos.isEmpty() ? null : descuentos,
				cargos.isEmpty() ? null : cargos
				);
	}
	
	public static Client clienteDTOtoApiClient(ClienteDTOForInsert cli) {
		return new Client(
				cli.getTipo_documento(),
				cli.getNumero_documento(),
				cli.getNombre(),
				direccionDTOToApiAddress(cli),
				cli.getEmail(),
				cli.getTelefono()
				);
				
	}
	
	public static Address direccionDTOToApiAddress(ClienteDTOForInsert cli) {
		return new Address(
				"", //TODO obtener ubigeo
				"LIMA",
				"LIMA",
				cli.getDistrito(),
				cli.getDireccion()
				);
	}
	
	public static SaleDetail subpedidoToApiDetail(DetallePedido sp, BigDecimal igv, Boolean antesDeIGV) {
		final List<Charge> descuentos = new ArrayList<>();
		/*if(sp.getOferta() != null) {
			descuentos.add(ofertaToApiCharge(sp.getOferta(),
			sp.getProducto().getPrecio_soles().multiply(new BigDecimal(sp.getCantidad()))));
		}*
		BigDecimal unitario = sp.getPlato().getPrecio();
		
		if(!antesDeIGV) unitario = unitario.divide(BigDecimal.ONE.setScale(2).add(igv), 2, RoundingMode.UP);
		return new SaleDetail(
				UnidadMedida.UNIDAD_BIENES,
				sp.getCantidad(),
				String.format("PROD-%06d",sp.getPlato().getId_plato()),
				sp.getPlato().getNombre_plato(),
				unitario,
				igv,
				AfectacionIGV.GRAVADO_OPERACION_ONEROSA,
				new BigDecimal(0),
				null,
				descuentos.isEmpty() ? null : descuentos
				);
	}
	/*
	public static Charge ofertaToApiCharge(Oferta ofe, BigDecimal montoBase) {
		final BigDecimal factor = new BigDecimal(ofe.getPorcentaje()).divide(new BigDecimal(100));
		return new Charge(
				TipoCargo.DESC_AFECTA_BASE_IMPONIBLE_IGV_IVAP,
				factor,
				montoBase,
				montoBase.multiply(factor)
				);
	}
*/
}
