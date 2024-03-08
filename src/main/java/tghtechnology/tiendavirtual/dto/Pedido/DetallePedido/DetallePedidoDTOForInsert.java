package tghtechnology.tiendavirtual.dto.Pedido.DetallePedido;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetallePedidoDTOForInsert {

	@NotNull(message = "No puede ser nulo")
    private Integer id_plato;
	
	@NotNull(message = "No puede ser nulo")
	@Min(value = 0, message = "La cantidad no puede ser menor a 0")
    private Integer cantidad;
	
	@DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal subtotal;
    
}
