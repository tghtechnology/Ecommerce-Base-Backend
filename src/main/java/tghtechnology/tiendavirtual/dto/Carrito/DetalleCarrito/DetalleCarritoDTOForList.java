package tghtechnology.tiendavirtual.dto.Carrito.DetalleCarrito;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.DetalleCarrito;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForListCarrito;

@Getter
@Setter
@NoArgsConstructor
public class DetalleCarritoDTOForList implements DTOForList<DetalleCarrito>{

	private Integer correlativo;
	private Integer cantidad;
	private ItemDTOForListCarrito item;
	private BigDecimal subtotal;
	
	@Override
	public DetalleCarritoDTOForList from(DetalleCarrito det) {

		this.correlativo = det.getCorrelativo();
		this.cantidad = det.getCantidad();
		this.item = new ItemDTOForListCarrito().from(det.getItem(), det.getImg());
		
		this.subtotal = new BigDecimal(cantidad).multiply(item.getPrecio().subtract(item.getDescuento()));
		
		return this;
	}

}
