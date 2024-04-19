package tghtechnology.tiendavirtual.dto.Publicidad;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Publicidad;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class PublicidadDTOForList implements DTOForList<Publicidad>{

	private Integer id_publicidad;
	private String nombre;
	private String enlace;
	private String imagen;
	private Boolean mostrar;
	
	@Override
	public PublicidadDTOForList from(Publicidad pub) {
		this.id_publicidad = pub.getId_publicidad();
		this.nombre = pub.getNombre();
		this.enlace = pub.getEnlace();
		this.imagen = pub.getImagen().getImagen();
		this.mostrar = pub.getMostrar();
		return this;
	}
	
}
