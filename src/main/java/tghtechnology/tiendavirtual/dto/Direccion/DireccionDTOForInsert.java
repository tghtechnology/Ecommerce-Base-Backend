package tghtechnology.tiendavirtual.dto.Direccion;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Direccion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class DireccionDTOForInsert implements DTOForInsert<Direccion>{
	
	@Valid
	private Integer id_cliente;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, max = 30, message = "la región debe tener entre 3 y 30 caracteres")
	private String region;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, max = 30, message = "La provincia debe tener entre 3 y 30 caracteres")
	private String provincia;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, max = 30, message = "El distrito debe tener entre 3 y 30 caracteres")
	private String distrito;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 150, message = "La dirección no debe estar vacía y debe tener un maximo de 150 caracteres")
	private String direccion;
	
    @Size(max = 150, message = "La referencia debe tener un maximo de 150 caracteres")
	private String referencia;
	

	@Override
	public Direccion toModel() {
		Direccion dir = new Direccion();
		
		dir.setRegion(region);
		dir.setProvincia(provincia);
		dir.setDistrito(distrito);
		dir.setDireccion(direccion);
		dir.setReferencia(referencia);
		dir.setShalom(false);
		dir.setEstado(true);
		
		return dir;
	}

	@Override
	public Direccion updateModel(Direccion dir) {
		
		dir.setRegion(region);
		dir.setProvincia(provincia);
		dir.setDistrito(distrito);
		dir.setDireccion(direccion);
		dir.setReferencia(referencia);
		
		return dir;
	}
    
	
}
