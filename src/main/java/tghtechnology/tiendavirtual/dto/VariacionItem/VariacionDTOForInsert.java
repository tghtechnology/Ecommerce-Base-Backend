package tghtechnology.tiendavirtual.dto.VariacionItem;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Enums.TipoVariacion;
import tghtechnology.tiendavirtual.Models.VariacionItem;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
public class VariacionDTOForInsert implements DTOForInsert<VariacionItem>{

	@NotNull(message = "El campo no puede ser nulo")
	private TipoVariacion tipo_variacion;
	
	@NotEmpty(message = "El campo no puede estar vacio")
	@Size(min = 7, max = 8, message = "")
	private String valor_variacion;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor a S/.0.00")
	private BigDecimal precio;
	
	@NotNull(message = "No puede ser nulo")
    private DisponibilidadItem disponibilidad;
	
	@NotNull(message = "No puede ser nulo")
	@Positive(message = "El stock debe ser positivo")
	private Integer stock;
	
	@NotNull(message = "No puede ser nulo")
	private Integer id_item;
	
	@Override
	public VariacionItem toModel() {
		VariacionItem var = new VariacionItem();
		
		var.setTipo_variacion(tipo_variacion);
		var.setValor_variacion(valor_variacion);
		var.setPrecio(precio);
		var.setDisponibilidad(disponibilidad);
		var.setStock(stock);
		
		return var;
	}

	@Override
	public VariacionItem updateModel(VariacionItem var) {
		var.setValor_variacion(valor_variacion);
		var.setPrecio(precio);
		var.setDisponibilidad(disponibilidad);
		var.setStock(stock);
		
		return var;
	}

}
