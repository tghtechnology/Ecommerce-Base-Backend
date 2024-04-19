package tghtechnology.tiendavirtual.dto.Publicidad;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Publicidad;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class PublicidadDTOForInsert implements DTOForInsert<Publicidad>{
	
	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 3, max = 50, message = "El largo debe ser entre 3 y 50 caracteres")
	@Pattern(regexp = "^[\\w\\s\\+\\-\\*_]+$", message = "Formato incorrecto")
	private String nombre;
	
	@NotEmpty(message = "No puede estar vacio")
	@Size(min = 3, max = 200, message = "El largo debe ser entre 3 y 200 caracteres")
	private String enlace;
	
	@NotNull(message = "No puede ser nulo")
	private Boolean mostrar;

	@Override
	public Publicidad toModel() {
		Publicidad pub = new Publicidad();
		
		pub.setNombre(nombre);
		pub.setEnlace(enlace);
		pub.setMostrar(mostrar);
		pub.setEstado(true);
		return pub;
	}

	@Override
	public Publicidad updateModel(Publicidad pub) {
		pub.setNombre(nombre);
		pub.setEnlace(enlace);
		pub.setMostrar(mostrar);
		return pub;
	}
	
	
}
