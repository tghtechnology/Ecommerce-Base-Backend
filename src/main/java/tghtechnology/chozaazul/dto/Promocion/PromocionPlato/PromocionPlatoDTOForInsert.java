package tghtechnology.chozaazul.dto.Promocion.PromocionPlato;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PromocionPlatoDTOForInsert {
	
	@NotNull(message = "No puede ser nulo")
	@Min(value = 0, message = "La cantidad no puede ser menor a 0")
	private Integer cantidad;
	
	@NotNull(message = "No puede ser nulo")
	private Integer id_plato;
	
	private Integer id_plato_2;
	
	private Integer id_plato_3;
	
}
