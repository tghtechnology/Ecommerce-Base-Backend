package tghtechnology.tiendavirtual.dto.VariacionItem;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
public class VariacionDTOForInsert implements DTOForInsert<Variacion>{
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 40, message = "El nombre del modelo debe tener menos de 40 caracteres")
	private String nombre_variacion;
	
	@NotNull(message = "No puede ser nulo")
    private DisponibilidadItem disponibilidad;
	
	@NotNull(message = "No puede ser nulo")
	@Positive(message = "El stock debe ser positivo")
	private Integer stock;
	
	@NotNull(message = "No puede ser nulo")
	private boolean aplicar_descuento;
	
	@NotNull(message = "No puede ser nulo")
	private Integer id_item;
	
	@Override
	public Variacion toModel() {
		Variacion var = new Variacion();
		
		var.setNombre_variacion(nombre_variacion);
		var.setDisponibilidad(disponibilidad);
		var.setStock(stock);
		var.setAplicarDescuento(aplicar_descuento);
		var.setEstado(true);
		
		return var;
	}

	@Override
	public Variacion updateModel(Variacion var) {
		var.setNombre_variacion(nombre_variacion);
		var.setDisponibilidad(disponibilidad);
		var.setStock(stock);
		var.setAplicarDescuento(aplicar_descuento);
		
		return var;
	}

}
