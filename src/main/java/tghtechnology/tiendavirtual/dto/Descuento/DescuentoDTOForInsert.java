package tghtechnology.tiendavirtual.dto.Descuento;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Descuento;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class DescuentoDTOForInsert implements DTOForInsert<Descuento>{

	@NotNull(message = "El campo no puede estar vacío")
	private Integer id_item;
	
	@Min(value = 1, message = "El campo no puede ser menor a 1")
	@Max(value = 100, message = "El campo no puede ser mayor a 100")
	private Integer porcentaje;
	
	
	private LocalDate programacion_inicio;
	
	private LocalDate programacion_final;
	
	private Boolean activado;
	
	@Override
	public Descuento toModel() {
		Descuento desc = new Descuento();
		
		desc.setPorcentaje(porcentaje);
		desc.setProgramacion_inicio(programacion_inicio);
		desc.setProgramacion_final(programacion_final);
		desc.setActivado(false);
		desc.setEstado(true);
		
		return desc;
	}

	@Override
	public Descuento updateModel(Descuento desc) {
		desc.setPorcentaje(porcentaje);
		desc.setProgramacion_inicio(programacion_inicio);
		desc.setProgramacion_final(programacion_final);
		if(activado != null) desc.setActivado(activado);
		desc.setEstado(true);
		
		return desc;
	}

	
	
}
