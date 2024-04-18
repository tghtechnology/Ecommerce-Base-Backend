package tghtechnology.tiendavirtual.dto.Persona;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Persona;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class PersonaDTOForInsert implements DTOForInsert<Persona>{
	
	@NotNull(message = "El campo no puede estar vacío")
	private TipoDocIdentidad tipo_documento;
	
    @Size(min = 8, max = 15, message = "El documento tener entre 8 y 15 caracteres")
	private String numero_documento;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 80, message = "El nombre debe tener menos de 80 caracteres")
	private String nombres;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 80, message = "El nombre debe tener menos de 80 caracteres")
	private String apellidos;
	
	@Pattern(regexp = "^[0-9+]*$", message = "El teléfono no puede contener letras o simbolos")
    @Size(min = 7, max = 12, message = "El teléfono debe tener entre 7 y 12 caracteres")
	private String telefono;
	
//	@NotEmpty(message = "El campo no puede estar vacío")
//	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El correo es invalido")
//  @Size(min = 10, max = 80, message = "El correo debe tener entre 10 y 80 caracteres")
	private String correo_personal;
	
	

	@Override
	public Persona toModel() {
		Persona per = new Persona();
		per.setTipo_documento(tipo_documento);
		per.setNumero_documento(numero_documento);
		per.setNombres(nombres);
		per.setApellidos(apellidos);
		per.setTelefono(telefono);
		per.setCorreo_personal(correo_personal);
		
		LocalDateTime now = LocalDateTime.now();
		per.setFecha_creacion(now);
		per.setFecha_modificacion(now);
		per.setEstado(true);
		
		return per;
	}

	@Override
	public Persona updateModel(Persona per) {
		per.setNumero_documento(numero_documento);
		per.setNombres(nombres);
		per.setApellidos(apellidos);
		per.setTelefono(telefono);
//		per.setCorreo_personal(correo_personal);
		
		
		per.setFecha_modificacion(LocalDateTime.now());
		return per;
	}
    
	
}
