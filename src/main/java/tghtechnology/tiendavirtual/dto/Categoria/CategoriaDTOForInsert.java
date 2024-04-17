package tghtechnology.tiendavirtual.dto.Categoria;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Categoria;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaDTOForInsert implements DTOForInsert<Categoria> {

	@NotEmpty(message = "El campo no puede estar vacío")
	@Pattern(regexp = "^[\\w\\-\\s]+$", message = "Texto inválido.")
    @Size(min = 3, max = 50, message = "la categoría debe tener entre 3 y 50 caracteres")
	private String descripcion;

	@Override
	public Categoria toModel() {
		Categoria cat = new Categoria();
		cat.setDescripcion(this.descripcion);
        cat.setText_id(Categoria.transform_id(descripcion));
        cat.setFecha_creacion(LocalDateTime.now());
        cat.setEstado(true);
		return cat;
	}

	@Override
	public Categoria updateModel(Categoria cat) {
		cat.setDescripcion(descripcion);
		cat.setText_id(Categoria.transform_id(descripcion));
		return cat;
	}
}
