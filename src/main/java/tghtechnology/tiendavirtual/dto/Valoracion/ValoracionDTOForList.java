package tghtechnology.tiendavirtual.dto.Valoracion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Valoracion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ValoracionDTOForList implements DTOForList<Valoracion>{

	private Integer id_valoracion;
	private Short estrellas;
	private String comentario;
	
	private String nombre_cliente;
	
	@Override
	public ValoracionDTOForList from(Valoracion val) {
		this.id_valoracion = val.getId_valoracion();
		this.estrellas = val.getEstrellas();
		this.comentario = val.getComentario();
		this.nombre_cliente = String.format("%s %s",
				val.getUsuario().getPersona().getNombres(),
				val.getUsuario().getPersona().getApellidos());
		return this;
	}

}
