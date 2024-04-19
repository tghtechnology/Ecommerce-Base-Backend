package tghtechnology.tiendavirtual.dto.Direccion;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Direccion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class DireccionDTOForInsertShalom implements DTOForInsert<Direccion>{
	
	private Integer id_cliente;
	
	@NotNull(message = "No puede ser nulo")
	private Integer id_shalom;
	

	@Override
	public Direccion toModel() {
		Direccion dir = new Direccion();
		
		dir.setShalom(true);
		dir.setId_shalom(id_shalom);
		dir.setEstado(true);
		
		return dir;
	}

	@Override
	public Direccion updateModel(Direccion dir) {
		throw new UnsupportedOperationException();
	}
    
	
}
