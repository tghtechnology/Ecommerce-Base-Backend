package tghtechnology.tiendavirtual.dto.Categoria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Categoria;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaDTOForList implements DTOForList<Categoria>{

	private Integer id_categoria;
    private String descripcion;

	@Override
	public CategoriaDTOForList from(Categoria model) {
		this.id_categoria = model.getId_categoria();
		this.descripcion = model.getDescripcion();
		return this;
	}
}
