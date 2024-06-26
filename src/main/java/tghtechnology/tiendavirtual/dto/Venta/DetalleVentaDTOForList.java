package tghtechnology.tiendavirtual.dto.Venta;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoVariacion;
import tghtechnology.tiendavirtual.Models.DetalleVenta;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class DetalleVentaDTOForList implements DTOForList<DetalleVenta>{

	private Integer id_detalle;
	
	private Integer id_item;
	private String nombre_item;
	private Integer id_variacion;
	private Integer var_correlativo;
	private TipoVariacion tipo_variacion;
	private String valor_variacion;
	private BigDecimal precio_unitario;
	
	private Integer porcentaje_descuento;
	private BigDecimal descuento_unitario;
	
	private Short cantidad;
	private BigDecimal subtotal;
	
	@Override
	public DetalleVentaDTOForList from(DetalleVenta dv) {
		return from(dv, 18, true);
	}
	
	public DetalleVentaDTOForList from(DetalleVenta dv, Integer igv, Boolean antes_de_igv) {
		this.id_detalle = dv.getId_detalle();
		this.id_item = dv.getId_item();
		this.nombre_item = dv.getNombre_item();
		this.id_variacion = dv.getId_variacion();
		this.var_correlativo = dv.getVariacion_correlativo();
		this.tipo_variacion = dv.getTipo_variacion();
		this.valor_variacion = dv.getValor_variacion();
		this.precio_unitario = calcularPrecioUnitario(dv, igv, antes_de_igv);
		this.porcentaje_descuento = dv.getPorcentaje_descuento();
		this.descuento_unitario = porcentaje_descuento == null ? BigDecimal.ZERO : precio_unitario.multiply(new BigDecimal(porcentaje_descuento).divide(new BigDecimal(100)));
		this.cantidad = dv.getCantidad();
		this.subtotal = new BigDecimal(this.cantidad).multiply(this.precio_unitario.subtract(this.descuento_unitario));
		return this;
	}
	
	private BigDecimal calcularPrecioUnitario(DetalleVenta dv, Integer igv, Boolean antes_de_igv) {
		return antes_de_igv
				// Antes de igv, el precio es el mismo y el IGV se cuenta normalmente
				? dv.getPrecio_unitario()
				// Después de igv, el precio se reduce a aproximadamente 84.7458%
				// (4 decimales para que no hayan errores de redondeo hasta los 1000 productos aprox.)
				: dv.getPrecio_unitario().divide(BigDecimal.ONE.setScale(2).add(new BigDecimal(igv/100.0)), 4, RoundingMode.UP)
				;
	}

}
