package tghtechnology.tiendavirtual.dto.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTOForInsert implements DTOForInsert<Item>{

	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 40, message = "El nombre del plato debe tener menos de 40 caracteres")
	private String nombre;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 150, message = "La descripción del plato debe tener menos de 150 caracteres")
    private String descripcion;
	
	@DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor a S/.0.00")
    private BigDecimal precio;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    private String disponibilidad;
    
	@NotNull(message = "No puede ser nulo")
    private int id_categoria;

	@Override
	public Item toModel() {
		LocalDateTime now = LocalDateTime.now();
		
		Item item = new Item();
		item.setNombre(nombre);
		item.setText_id(transform_id());
		item.setDescripcion(descripcion);
		item.setPrecio(precio);
		item.setDisponibilidad(disponibilidad);
		item.setFecha_creacion(now);
		item.setFecha_modificacion(now);
		item.setEstado(true);
		return item;
	}

	@Override
	public Item updateModel(Item item) {
		item.setNombre(nombre);
		item.setText_id(transform_id());
		item.setDescripcion(descripcion);
		item.setPrecio(precio);
		item.setDisponibilidad(disponibilidad);
		item.setFecha_modificacion(LocalDateTime.now());
		return item;
	}
	
    public String transform_id() {
		return nombre.strip()				// sin espacios al inicio o final
				.replace(' ', '_')			// espacios y guiones por _
				.replace('-', '_')
				.replaceAll("(\\+|,|')+","")// simbolos por vacio
				.toLowerCase();				// minusculas
	}
}
