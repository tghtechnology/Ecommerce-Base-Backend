package tghtechnology.tiendavirtual.dto.EspecificacionItem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Especificacion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class EspecificacionDTOForInsert implements DTOForInsert<Especificacion>{

	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 40, message = "El nombre de la especificacion debe tener menos de 40 caracteres")
	private String nombre;
	
	private DisponibilidadItem disponibilidad;

	@Override
	public Especificacion toModel() {
		Especificacion esp = new Especificacion();
		esp.setNombre_especificacion(nombre);
		esp.setDisponibilidad(disponibilidad == null ? DisponibilidadItem.DISPONIBLE : disponibilidad);
		esp.setEstado(true);
		
		return esp;
	}

	@Override
	public Especificacion updateModel(Especificacion esp) {
		esp.setNombre_especificacion(nombre);
		esp.setDisponibilidad(disponibilidad == null ? DisponibilidadItem.DISPONIBLE : disponibilidad);
		esp.setEstado(true);
		
		return esp;
	}
}
