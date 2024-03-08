package tghtechnology.chozaazul.dto.Pedido;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Enums.TipoDelivery;
import tghtechnology.chozaazul.Utils.ApisPeru.Enums.TipoDocumento;
import tghtechnology.chozaazul.dto.Cliente.ClienteDTOForInsert;
import tghtechnology.chozaazul.dto.Pedido.DetallePedido.DetallePedidoDTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class PedidoDTOForInsert {

	@NotNull(message = "El campo no puede estar vacío")
	private TipoDocumento tipo_comprobante;
	
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal precio_total;
	
	@Valid
	@NotNull(message = "No puede ser nulo")
    private ClienteDTOForInsert cliente;
    
	@Valid
	@NotNull(message = "No puede ser nulo")
    private Set<DetallePedidoDTOForInsert> detalles;
    
	@NotNull(message = "El campo no puede estar vacío")
	private TipoDelivery tipo_delivery;
	
	private List<Integer> promociones;
	
}
