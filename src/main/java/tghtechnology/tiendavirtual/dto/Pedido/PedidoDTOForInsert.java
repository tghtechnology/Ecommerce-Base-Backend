package tghtechnology.tiendavirtual.dto.Pedido;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Pedido;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForInsert;
import tghtechnology.tiendavirtual.dto.Pedido.DetallePedido.DetallePedidoDTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class PedidoDTOForInsert implements DTOForInsert<Pedido>{
	
	private Integer id_cliente;
	
	@Valid
    private ClienteDTOForInsert cliente;
    
	@Valid
	@NotNull(message = "No puede ser nulo")
    private Set<DetallePedidoDTOForInsert> detalles;

	@Override
	public Pedido toModel() {
		Pedido ped = new Pedido();
		ped.setCompletado(false);
		ped.setEstado(true);
		return ped;
	}
	
	@Override
	public Pedido updateModel(Pedido ped) {
		return ped;
	}
	
}
