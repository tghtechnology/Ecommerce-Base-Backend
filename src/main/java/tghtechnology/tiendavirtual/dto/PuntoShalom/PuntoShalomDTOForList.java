package tghtechnology.tiendavirtual.dto.PuntoShalom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.RegionPeru;
import tghtechnology.tiendavirtual.Models.PuntoShalom;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class PuntoShalomDTOForList implements DTOForList<PuntoShalom>{

    private Integer id_punto;
    private String nombre;
    private String lugar;
    private RegionPeru departamento;
    private String provincia;
    private String distrito;
    private String direccion;
    private Boolean activo;
	
	
	@Override
	public PuntoShalomDTOForList from(PuntoShalom ps) {
		this.id_punto = ps.getId_punto();
		this.nombre = ps.getNombre();
		this.lugar = ps.getLugar();
		this.departamento = ps.getDepartamento();
		this.provincia = ps.getProvincia();
		this.distrito = ps.getDistrito();
		this.direccion = ps.getDireccion();
		this.activo = ps.getActivo();
		return this;
	}

}
