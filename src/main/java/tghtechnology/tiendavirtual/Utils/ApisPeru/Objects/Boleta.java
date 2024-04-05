package tghtechnology.tiendavirtual.Utils.ApisPeru.Objects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Leyenda;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Moneda;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoComprobante;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoOperacion;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.ApisPeruUtils;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.DineroALetras;

@Getter
@Setter
/**
 * Objeto que representa una boleta para ApisPeru.
 *
 */
public class Boleta {

	private String ublVersion;
	private String tipoOperacion;
	private String tipoDoc;
	private String serie;
	private String correlativo;
	private String fechaEmision;
	private PaymentTerms formaPago;
	private Client client;
	private Company company;
	private String tipoMoneda;
	private BigDecimal sumOtrosCargos;
	private BigDecimal mtoOperGravadas;
	private BigDecimal mtoOperInafectas;
	private BigDecimal mtoOperExoneradas;
	private BigDecimal mtoIGV;
	private BigDecimal mtoIGVGratuitas;
	private BigDecimal totalImpuestos;
	private BigDecimal valorVenta;
	private BigDecimal subTotal;
	private BigDecimal mtoImpVenta;
	private List<SaleDetail> details;
	private List<Legend> legends;
	private String observacion;
	private BigDecimal mtoOperGratuitas;
	//private SalePerception perception;
	//private Detraction detraccion;
	private Address direccionEntrega;
	private List<Charge> descuentos;
	private List<Charge> cargos;
	private BigDecimal sumDsctoGlobal;
	private BigDecimal mtoDescuentos;
	private BigDecimal mtoCargos;
	
	
	public Boleta(TipoOperacion tipoOperacion,
					TipoComprobante tipoDoc,
					Integer idBoleta,
					LocalDateTime fechaEmision,
					Client client,
					Company company,
					BigDecimal sumOtrosCargos,
					BigDecimal mtoOperGravadas,
					List<SaleDetail> details,
					List<Leyenda> legends,
					String observacion,
					Address direccionEntrega,
					List<Charge> descuentos,
					List<Charge> cargos) {
		
		this.ublVersion = "2.1";	// Investigar
		this.tipoOperacion = tipoOperacion.getLabel();
		this.tipoDoc = tipoDoc.getLabel();
		this.serie = "B001";			// Autogenerar
		this.correlativo = String.format("%08d", idBoleta);	//Investigar
		this.fechaEmision = ApisPeruUtils.dateFormat(fechaEmision);
		this.formaPago = new PaymentTerms(Moneda.PEN, "Contado");
		this.client = client;
		this.company = company;//company;
		this.tipoMoneda = Moneda.PEN.getLabel();
		this.details = details;
		this.observacion = observacion;
		this.direccionEntrega = direccionEntrega;
		
		this.sumOtrosCargos = sumOtrosCargos;
		this.mtoOperGravadas = mtoOperGravadas;
		
		this.mtoIGV = calcularIGV(details).setScale(2, RoundingMode.HALF_UP);
		this.totalImpuestos = mtoIGV;	// Sumar todos los impuestos
		
		this.descuentos = descuentos;
		this.cargos = cargos;
		//this.mtoDescuentos = mtoDescuentos;
		//this.mtoCargos = mtoCargos;
		
		this.valorVenta = mtoOperGravadas;
		this.subTotal = mtoOperGravadas.add(this.totalImpuestos).setScale(2, RoundingMode.HALF_UP);
		this.mtoImpVenta = this.subTotal;
		
		this.legends = convertirLeyendas(legends, this.subTotal);
	}
	
	public Boleta(String ublVersion,
					TipoOperacion tipoOperacion,
					TipoComprobante tipoDoc,
					String serie,
					String correlativo,
					LocalDateTime fechaEmision,
					PaymentTerms formaPago,
					Client client,
					Company company,
					Moneda tipoMoneda,
					BigDecimal sumOtrosCargos,
					BigDecimal mtoOperGravadas,
					BigDecimal mtoOperInafectas,
					BigDecimal mtoOperExoneradas,
					BigDecimal mtoIGV,
					BigDecimal mtoIGVGratuitas,
					BigDecimal totalImpuestos,
					BigDecimal valorVenta,
					BigDecimal subTotal,
					BigDecimal mtoImpVenta,
					List<SaleDetail> details,
					List<Legend> legends,
					String observacion,
					BigDecimal mtoOperGratuitas,
					Address direccionEntrega,
					List<Charge> descuentos,
					List<Charge> cargos,
					BigDecimal sumDsctoGlobal,
					BigDecimal mtoDescuentos,
					BigDecimal mtoCargos) {
		
		this.ublVersion = ublVersion;
		this.tipoOperacion = tipoOperacion.getLabel();
		this.tipoDoc = tipoDoc.getLabel();
		this.serie = serie;
		this.correlativo = correlativo;
		this.fechaEmision = ApisPeruUtils.dateFormat(fechaEmision);
		this.formaPago = formaPago;
		this.client = client;
		this.company = company;
		this.tipoMoneda = tipoMoneda.getLabel();
		this.sumOtrosCargos = sumOtrosCargos;
		this.mtoOperGravadas = mtoOperGravadas;
		this.mtoOperInafectas = mtoOperInafectas;
		this.mtoOperExoneradas = mtoOperExoneradas;
		this.mtoIGV = mtoIGV;
		this.mtoIGVGratuitas = mtoIGVGratuitas;
		this.totalImpuestos = totalImpuestos;
		this.valorVenta = valorVenta;
		this.subTotal = subTotal;
		this.mtoImpVenta = mtoImpVenta;
		this.details = details;
		this.legends = legends;
		this.observacion = observacion;
		this.mtoOperGratuitas = mtoOperGratuitas;
		this.direccionEntrega = direccionEntrega;
		this.descuentos = descuentos;
		this.cargos = cargos;
		this.sumDsctoGlobal = sumDsctoGlobal;
		this.mtoDescuentos = mtoDescuentos;
		this.mtoCargos = mtoCargos;
	}


	private BigDecimal calcularIGV(List<SaleDetail> detalles) {
		BigDecimal total = new BigDecimal(0);
		
		for(SaleDetail det : detalles) {
			//System.out.println("Sumando " + det.getIgv());
			total = total.add(det.getIgv());
		}
		return total;
	}
	
	
	private List<Legend> convertirLeyendas(List<Leyenda> leg, BigDecimal subtotal) {
		
		final List<Legend> leyendas = new ArrayList<>();
		
		leg.forEach(l -> {
			if(l == Leyenda.MONTO_EN_LETRAS)
				leyendas.add(new Legend(l, DineroALetras.convertirADinero(subtotal)));
			else
				leyendas.add(new Legend(l));
		});
		
		return leyendas;
	}
	
}
