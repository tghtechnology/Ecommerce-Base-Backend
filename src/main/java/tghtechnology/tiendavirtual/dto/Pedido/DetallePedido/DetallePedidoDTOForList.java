package tghtechnology.tiendavirtual.dto.Pedido.DetallePedido;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.DetallePedido;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class DetallePedidoDTOForList implements DTOForList<DetallePedido>{

    private ItemDTOForList item;
	
    private Integer cantidad;
    private BigDecimal subtotal;

	@Override
	public DetallePedidoDTOForList from(DetallePedido dp) {
		this.cantidad = dp.getCantidad();
		this.subtotal = dp.getSub_total();
		this.item = new ItemDTOForList().from(dp.getItem());

		return this;
	}
    
}
