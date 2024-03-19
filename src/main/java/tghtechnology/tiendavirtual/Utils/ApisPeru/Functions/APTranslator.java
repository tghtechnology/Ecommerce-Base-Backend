package tghtechnology.tiendavirtual.Utils.ApisPeru.Functions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tghtechnology.tiendavirtual.Enums.TipoDelivery;
import tghtechnology.tiendavirtual.Models.DetallePedido;
import tghtechnology.tiendavirtual.Models.Pedido;
import tghtechnology.tiendavirtual.Utils.ApisPeru.CompanyData;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.AfectacionIGV;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Leyenda;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoOperacion;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.UnidadMedida;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Address;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Charge;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Client;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.SaleDetail;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForInsert;

public class APTranslator {
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
