package tghtechnology.tiendavirtual.dto.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTOForInsert implements DTOForInsert<Item>{

	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 40, message = "El nombre del item debe tener menos de 40 caracteres")
	private String nombre;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 150, message = "La descripción del item debe tener menos de 150 caracteres")
    private String descripcion;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor a S/.0.00")
	private BigDecimal precio;
	
	@DecimalMin(value = "0.0", inclusive = true, message = "El costo debe ser mayor a S/.0.00")
	private BigDecimal costo;
	
	@NotNull(message = "No puede ser nulo")
    private DisponibilidadItem disponibilidad;
	
	@NotNull(message = "No puede ser nulo")
	@Positive(message = "El stock debe ser positivo")
	private Integer stock;
    
    private Integer id_categoria;
	
	@NotNull(message = "No puede ser nulo")
    private Integer id_marca;
	
	private Integer id_descuento;

	@Override
	public Item toModel() {
		LocalDateTime now = LocalDateTime.now();
		
		Item item = new Item();
		item.setNombre(nombre);
		item.setText_id(Item.transform_id(nombre));
		item.setDescripcion(descripcion);
		item.setDisponibilidad(disponibilidad);
		item.setPrecio(precio);
		item.setCosto(costo);
		item.setStock(stock);
		item.setFecha_creacion(now);
		item.setFecha_modificacion(now);
		item.setEstado(true);
		
		return item;
	}

	@Override
	public Item updateModel(Item item) {
		item.setNombre(nombre);
		item.setText_id(Item.transform_id(nombre));
		item.setDescripcion(descripcion);
		item.setDisponibilidad(disponibilidad);
		item.setPrecio(precio);
		item.setCosto(costo);
		item.setStock(stock);
		item.setFecha_modificacion(LocalDateTime.now());
		return item;
	}
	
}
