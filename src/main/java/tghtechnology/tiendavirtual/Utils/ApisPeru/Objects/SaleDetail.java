package tghtechnology.tiendavirtual.Utils.ApisPeru.Objects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.AfectacionIGV;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.UnidadMedida;

@Getter
@Setter
public class SaleDetail {

	private String unidad;
	private BigDecimal cantidad;
	private String codProducto;
	private String codProdSunat;
	private String descripcion;
	private BigDecimal mtoValorUnitario;	// precio antes de impuestos
	private BigDecimal igv;					// 18%
	private String tipAfeIgv;
	private BigDecimal totalImpuestos;
	private BigDecimal mtoPrecioUnitario;	// precio despues de impuestos
	private BigDecimal mtoValorVenta;
	private BigDecimal mtoValorGratuito;
	private BigDecimal mtoBaseIgv;
	private BigDecimal porcentajeIgv;
	private List<Charge> cargos;
	private List<Charge> descuentos;
	
	public SaleDetail(UnidadMedida unidad,
					int cantidad,
					String codProducto,
					String descripcion,
					BigDecimal mtoValorUnitario,
					BigDecimal igv,
					AfectacionIGV tipoAfeIgv,
					BigDecimal mtoValorGratuito,
					List<Charge> cargos,
					List<Charge> descuentos) {
		
		this.unidad = unidad.getLabel();
		this.cantidad = new BigDecimal(cantidad);
		this.codProducto = codProducto;
		this.descripcion = descripcion;
		this.mtoValorUnitario = mtoValorUnitario.setScale(2, RoundingMode.HALF_UP);
		this.tipAfeIgv = tipoAfeIgv.getLabel();
		this.mtoValorGratuito = mtoValorGratuito;
		this.cargos = cargos;
		this.descuentos = descuentos;
		
		this.porcentajeIgv = igv.multiply(new BigDecimal(100)).setScale(0);	// Leer de config
		this.mtoValorVenta = mtoValorUnitario.multiply(this.cantidad);
		this.mtoBaseIgv = this.mtoValorVenta;
		this.igv = this.mtoBaseIgv.multiply(igv);
		this.totalImpuestos = this.igv; 			// sumar todos los impuestos
		this.mtoPrecioUnitario = this.mtoBaseIgv.add(this.totalImpuestos).divide(this.cantidad, 2, RoundingMode.HALF_UP);
		
	}
	
	
	public SaleDetail(UnidadMedida unidad,
						int cantidad,
						String codProducto,
						String codProdSunat,
						String descripcion,
						BigDecimal mtoValorUnitario,
						BigDecimal igv,
						AfectacionIGV tipoAfeIgv,
						BigDecimal totalImpuestos,
						BigDecimal mtoPrecioUnitario,
						BigDecimal mtoValorVenta,
						BigDecimal mtoValorGratuito,
						BigDecimal mtoBaseIgv,
						BigDecimal porcentajeIgv,
						List<Charge> cargos,
						List<Charge> descuentos) {
		
		this.unidad = unidad.getLabel();
		this.cantidad = new BigDecimal(cantidad);
		this.codProducto = codProducto;
		this.codProdSunat = codProdSunat;
		this.descripcion = descripcion;
		this.mtoValorUnitario = mtoValorUnitario;
		this.igv = igv;
		this.tipAfeIgv = tipoAfeIgv.getLabel();
		this.totalImpuestos = totalImpuestos;
		this.mtoPrecioUnitario = mtoPrecioUnitario;
		this.mtoValorVenta = mtoValorVenta;
		this.mtoValorGratuito = mtoValorGratuito;
		this.mtoBaseIgv = mtoBaseIgv;
		this.porcentajeIgv = porcentajeIgv;
		this.cargos = cargos;
		this.descuentos = descuentos;
	}
	
	
	
	
}
