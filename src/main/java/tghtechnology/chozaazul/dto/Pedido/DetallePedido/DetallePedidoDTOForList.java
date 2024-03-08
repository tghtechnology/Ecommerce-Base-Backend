package tghtechnology.chozaazul.dto.Pedido.DetallePedido;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.DetallePedido;
import tghtechnology.chozaazul.dto.Plato.PlatoDTOMinimal;

@Getter
@Setter
@NoArgsConstructor
public class DetallePedidoDTOForList {

    private PlatoDTOMinimal plato;
	
    private Integer cantidad;
    private BigDecimal subtotal;
    
    public DetallePedidoDTOForList(DetallePedido dp) {
    	this.cantidad = dp.getCantidad();
    	this.subtotal = dp.getSub_total();
    	this.plato = new PlatoDTOMinimal(dp.getPlato());
    }
    
}
