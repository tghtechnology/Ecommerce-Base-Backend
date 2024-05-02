package tghtechnology.tiendavirtual.dto.DistritoDelivery;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DistritoLima;
import tghtechnology.tiendavirtual.Models.DistritoDelivery;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class DistritoDeliveryDTOForInsert implements DTOForInsert<DistritoDelivery>{
	
	@NotNull(message = "No puede ser nulo")
	@Min(value = 0, message = "No puede ser menor a 0")
	private BigDecimal precio_delivery;
	
	private Boolean activo;
	
	@Override
	public DistritoDelivery toModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public DistritoDelivery updateModel(DistritoDelivery dd) {
		dd.setPrecio_delivery(precio_delivery);
		if(activo != null) dd.setActivo(activo);
		return dd;
	}

}
