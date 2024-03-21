package tghtechnology.tiendavirtual.dto.Cliente;

import java.time.LocalDateTime;
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
	private LocalDateTime ultima_compra;
	
	private List<String> direcciones = new ArrayList<>();

	@Override
	public ClienteDTOForList from(Cliente cliente) {
		this.persona = new PersonaDTOForList().from(cliente.getPersona());
		
		this.recibe_correos = cliente.isRecibe_correos();
		this.ultima_compra = cliente.getUltima_compra();
		
		cliente.getDirecciones().forEach(dir ->{
			direcciones.add(dir.getRegion());
		});
		
		return this;
	}
	
}
