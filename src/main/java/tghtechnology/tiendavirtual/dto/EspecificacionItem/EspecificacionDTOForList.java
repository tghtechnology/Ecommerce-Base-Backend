package tghtechnology.tiendavirtual.dto.EspecificacionItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Especificacion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class EspecificacionDTOForList implements DTOForList<Especificacion>{

	private Integer id_especificacion;
	private Integer correlativo;
	private String nombre_especificacion;
	private DisponibilidadItem disponibilidad;
	
	@Override
	public EspecificacionDTOForList from(Especificacion esp) {
		this.id_especificacion = esp.getId_especificacion();
		this.correlativo = esp.getCorrelativo();
		this.nombre_especificacion = esp.getNombre_especificacion();
		this.disponibilidad = esp.getDisponibilidad();
		return this;
	}

}
