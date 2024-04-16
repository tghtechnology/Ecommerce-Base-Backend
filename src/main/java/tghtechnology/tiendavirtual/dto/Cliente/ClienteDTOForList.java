package tghtechnology.tiendavirtual.dto.Cliente;

import java.util.ArrayList;
import java.util.List;

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
	
	private boolean recibe_correos;
	
	private List<String> direcciones = new ArrayList<>();

	@Override
	public ClienteDTOForList from(Cliente cliente) {
		this.persona = new PersonaDTOForList().from(cliente.getPersona());
		
		this.recibe_correos = cliente.isRecibe_correos();
		
		cliente.getDirecciones().forEach(dir ->{
			direcciones.add(dir.getRegion());
		});
		
		return this;
	}
	
}
