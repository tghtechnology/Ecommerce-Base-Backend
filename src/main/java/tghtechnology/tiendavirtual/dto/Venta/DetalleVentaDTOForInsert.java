package tghtechnology.tiendavirtual.dto.Venta;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.DetalleVenta;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class DetalleVentaDTOForInsert implements DTOForInsert<DetalleVenta>{

	@NotNull(message = "No puede ser nulo")
	private Integer id_item;
	
	@Min(value = 1, message = "No puede ser menor a 1")
	@NotNull(message = "No puede ser nulo")
	private Short cantidad;
	
	@Override
	public DetalleVenta toModel() {
		DetalleVenta det = new DetalleVenta();
		det.setId_item(id_item);
		det.setCantidad(cantidad);
		return det;
	}

	@Override
	public DetalleVenta updateModel(DetalleVenta modelToUpdate) {
		throw new UnsupportedOperationException();
	}
}
