package tghtechnology.chozaazul.dto.MenuCumple;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Enums.Mes;
import tghtechnology.chozaazul.dto.Plato.PlatoDTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class MenuCumpleDTOForInsert {

	@NotNull(message = "No puede ser nulo")
	private Mes mes;
	
	@Valid
	@NotNull(message = "No puede ser nulo")
	private PlatoDTOForInsert plato;

	
}
