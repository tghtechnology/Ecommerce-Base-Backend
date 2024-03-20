package tghtechnology.tiendavirtual.dto.Marca;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Marca;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class MarcaDTOForInsert implements DTOForInsert<Marca>{

	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre de marca debe tener entre 3 y 50 caracteres")
	private String nombre;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, max = 300, message = "La descripcion debe tener entre 3 y 50 caracteres")
	private String descripcion;
	
	@Override
	public Marca toModel() {
		Marca mar = new Marca();
		mar.setNombre(nombre);
		mar.setText_id(Marca.transform_id(nombre));
		mar.setDescripcion(descripcion);
		mar.setFecha_creacion(LocalDateTime.now());
		mar.setEstado(true);
		return mar;
	}

	@Override
	public Marca updateModel(Marca mar) {
		mar.setNombre(nombre);
		mar.setText_id(Marca.transform_id(nombre));
		mar.setDescripcion(descripcion);
		mar.setFecha_creacion(LocalDateTime.now());
		mar.setEstado(true);
		return mar;
	}


	
}
