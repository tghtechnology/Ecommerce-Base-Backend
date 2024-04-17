package tghtechnology.tiendavirtual.dto.Direccion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Direccion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class DireccionDTOForList implements DTOForList<Direccion>{
	
	private Integer id_direccion;
	private String region;
	private String provincia;
	private String distrito;
	private String direccion;
	private String referencia;

	@Override
	public DireccionDTOForList from(Direccion direccion) {
		this.id_direccion = direccion.getId_direccion();
		this.region = direccion.getRegion();
		this.provincia = direccion.getProvincia();
		this.distrito = direccion.getDistrito();
		this.direccion = direccion.getDireccion();
		this.referencia = direccion.getReferencia();
		return this;
	}
	
}
