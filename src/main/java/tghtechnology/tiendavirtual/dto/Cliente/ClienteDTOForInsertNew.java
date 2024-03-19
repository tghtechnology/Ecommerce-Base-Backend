package tghtechnology.tiendavirtual.dto.Cliente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;
import tghtechnology.tiendavirtual.dto.Persona.PersonaDTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTOForInsertNew implements DTOForInsert<Cliente>{
	
	@Valid
	@NotNull(message = "El campo no puede ser nulo")
	private PersonaDTOForInsert persona;
	
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
	
    private Double latitud;
    
    private Double longitud;

	@Override
	public Cliente toModel() {
		Cliente cli = new Cliente();
		
		cli.setRegion(region);
		cli.setProvincia(provincia);
		cli.setDistrito(distrito);
		cli.setDireccion(direccion);
		cli.setReferencia(referencia);
		cli.setLatitud(latitud);
		cli.setLongitud(longitud);
		cli.setEstado(true);
		
		return cli;
	}

	@Override
	public Cliente updateModel(Cliente cli) {
		
		cli.setPersona(persona.updateModel(cli.getPersona()));
		
		cli.setRegion(region);
		cli.setProvincia(provincia);
		cli.setDistrito(distrito);
		cli.setDireccion(direccion);
		cli.setReferencia(referencia);
		cli.setLatitud(latitud);
		cli.setLongitud(longitud);
		
		return cli;
	}
    
	
}
