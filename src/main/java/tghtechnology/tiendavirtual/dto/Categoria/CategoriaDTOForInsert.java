package tghtechnology.tiendavirtual.dto.Categoria;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaDTOForInsert {

	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, max = 50, message = "la categoría debe tener entre 3 y 50 caracteres")
	private String descripcion;
}
