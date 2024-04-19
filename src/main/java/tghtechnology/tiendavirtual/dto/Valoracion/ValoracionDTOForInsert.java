package tghtechnology.tiendavirtual.dto.Valoracion;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Valoracion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class ValoracionDTOForInsert implements DTOForInsert<Valoracion>{

	@NotNull(message = "No puede ser nulo")
	private Integer id_item;
	
	@NotNull(message = "No puede ser nulo")
	@Min(value = 1, message = "No puede ser menor a 1")
	@Max(value = 5, message = "No puede ser mayor a 5")
	private Short estrellas;
	
	@Size(max = 1000, message = "El tamaño debe ser menor a 1000 caracteres")
	private String comentario;
	
	
	@Override
	public Valoracion toModel() {
		Valoracion val = new Valoracion();
		val.setEstrellas(estrellas);
		
		if(!comentario.isBlank())
			val.setComentario(comentario.strip());
		
		val.setFecha_creacion(LocalDateTime.now());
		val.setEstado(true);
		
		return val;
	}

	@Override
	public Valoracion updateModel(Valoracion val) {
		val.setEstrellas(estrellas);
		
		if(!comentario.isBlank())
			val.setComentario(comentario.strip());
		else
			val.setComentario(null);
		
		val.setFecha_modificacion(LocalDateTime.now());
		
		return val;
	}

}