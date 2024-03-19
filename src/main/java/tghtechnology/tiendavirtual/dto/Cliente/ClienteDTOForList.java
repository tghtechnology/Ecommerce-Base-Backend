package tghtechnology.tiendavirtual.dto.Cliente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Persona.PersonaDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTOForList implements DTOForList<Cliente>{

	private PersonaDTOForList persona;
	
	private String region;
	private String provincia;
	private String distrito;
	private String direccion;
	private String referencia;

	@Override
	public ClienteDTOForList from(Cliente cliente) {
		this.persona = new PersonaDTOForList().from(cliente.getPersona());
		this.region = cliente.getRegion();
		this.provincia = cliente.getProvincia();
		this.distrito = cliente.getDistrito();
		this.direccion = cliente.getDireccion();
		this.referencia = cliente.getReferencia();
		return this;
	}
	
}
