package tghtechnology.tiendavirtual.dto.Marca;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Marca;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class MarcaDTOForList implements DTOForList<Marca>{

	private Integer id_marca;
	private String nombre;
	private String url;
	private String descripcion;
	private LocalDateTime fecha_creacion;
	private String logo;
	
	@Override
	public MarcaDTOForList from(Marca mar) {
		this.id_marca = mar.getId_marca();
		this.nombre = mar.getNombre();
		this.url = mar.getText_id();
		this.descripcion = mar.getDescripcion();
		this.fecha_creacion = mar.getFecha_creacion();
		this.logo = mar.getLogo() == null ? null : mar.getLogo().getImagen();
		return this;
	}

}
