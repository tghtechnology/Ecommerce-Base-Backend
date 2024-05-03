package tghtechnology.tiendavirtual.dto.Venta;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.DetalleVenta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.ApisPeruUtils;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class DetalleVentaDTOForList implements DTOForList<DetalleVenta>{

	private Integer id_detalle;
	
	private Integer id_item;
	private String nombre_item;
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
		this.precio_unitario = ApisPeruUtils.calcularPrecioUnitario(dv.getPrecio_unitario(), igv, antes_de_igv);
		this.porcentaje_descuento = dv.getPorcentaje_descuento();
		this.descuento_unitario = porcentaje_descuento == null ? BigDecimal.ZERO : precio_unitario.multiply(new BigDecimal(porcentaje_descuento).divide(new BigDecimal(100)));
		this.cantidad = dv.getCantidad();
		this.subtotal = new BigDecimal(this.cantidad).multiply(this.precio_unitario.subtract(this.descuento_unitario));
		return this;
	}

}
