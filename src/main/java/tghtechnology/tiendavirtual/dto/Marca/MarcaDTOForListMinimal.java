package tghtechnology.tiendavirtual.dto.Marca;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Marca;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class MarcaDTOForListMinimal implements DTOForList<Marca>{

	private String nombre;
	private String url;
	private String logo;
	
	@Override
	public MarcaDTOForListMinimal from(Marca mar) {
		this.nombre = mar.getNombre();
		this.url = mar.getText_id();
		this.logo = mar.getLogo() == null ? null : mar.getLogo().getImagen();
		return this;
	}

}
