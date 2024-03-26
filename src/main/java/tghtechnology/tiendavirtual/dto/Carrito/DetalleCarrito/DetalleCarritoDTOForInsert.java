package tghtechnology.tiendavirtual.dto.Carrito.DetalleCarrito;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.DetalleCarrito;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;

@Getter
@Setter
@NoArgsConstructor
public class DetalleCarritoDTOForInsert implements DTOForInsert<DetalleCarrito>{
	
	private Integer id_variacion;
	
	@NotNull(message = "No puede ser nulo")
	@Min(value = 0, message = "Debe ser mayor a 0")
	private Integer cantidad;
	
	@Override
	public DetalleCarrito toModel() {
		
		if(id_variacion == null)
			throw new DataMismatchException("detalle_carrito.id_variacion", "No puede ser nulo");
		
		DetalleCarrito dc = new DetalleCarrito();
		dc.setCantidad(cantidad);
		return dc;
	}

	@Override
	public DetalleCarrito updateModel(DetalleCarrito dc) {
		dc.setCantidad(cantidad);
		return dc;
	}

}
