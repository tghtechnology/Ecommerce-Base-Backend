package tghtechnology.tiendavirtual.dto.Pedido.DetallePedido;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.DetallePedido;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class DetallePedidoDTOForInsert implements DTOForInsert<DetallePedido> {

	@NotNull(message = "No puede ser nulo")
    private Integer id_item;
	
	@NotNull(message = "No puede ser nulo")
	@Min(value = 0, message = "La cantidad no puede ser menor a 0")
    private Integer cantidad;
	
	@DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal subtotal;

	@Override
	public DetallePedido toModel() {
		DetallePedido dp = new DetallePedido();
		dp.setCantidad(cantidad);
		dp.setSub_total(subtotal);
		return dp;
	}

	@Override
	public DetallePedido updateModel(DetallePedido modelToUpdate) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
