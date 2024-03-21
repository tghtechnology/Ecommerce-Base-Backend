package tghtechnology.tiendavirtual.dto.Cliente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;
import tghtechnology.tiendavirtual.dto.Persona.PersonaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForModify;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTOForModify implements DTOForInsert<Cliente>{
    
    @Valid
    @NotNull(message = "La persona no puede ser nula")
    private PersonaDTOForInsert persona;
    
    @NotNull(message = "El campo no puede ser nulo")
    private boolean recibe_correos;
    
    private UsuarioDTOForModify credenciales;

	@Override
	public Cliente toModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Cliente updateModel(Cliente cli) {
		cli.setPersona(persona.updateModel(cli.getPersona()));

		cli.setRecibe_correos(recibe_correos);;
		
		return cli;
	}

}


