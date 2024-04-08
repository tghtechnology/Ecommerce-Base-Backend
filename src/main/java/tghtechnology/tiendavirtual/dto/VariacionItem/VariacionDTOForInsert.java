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
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
public class VariacionDTOForInsert implements DTOForInsert<Variacion>{

	@NotNull(message = "El campo no puede ser nulo")
	private TipoVariacion tipo_variacion;
	
	@NotEmpty(message = "El campo no puede estar vacio")
	@Size(min = 6, max = 8, message = "Formato incorrecto")
	private String valor_variacion;
	
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
	
	@NotNull(message = "No puede ser nulo")
	private boolean aplicar_descuento;
	
	@NotNull(message = "No puede ser nulo")
	private Integer id_item;
	
	@Override
	public Variacion toModel() {
		Variacion var = new Variacion();
		
		var.setTipo_variacion(tipo_variacion);
		var.setValor_variacion(valor_variacion);
		var.setPrecio(precio);
		var.setCosto(costo == null ? precio : costo);
		var.setDisponibilidad(disponibilidad);
		var.setStock(stock);
		var.setAplicarDescuento(aplicar_descuento);
		var.setEstado(true);
		
		return var;
	}

	@Override
	public Variacion updateModel(Variacion var) {
		var.setValor_variacion(valor_variacion);
		var.setPrecio(precio);
		var.setCosto(costo == null ? precio : costo);
		var.setDisponibilidad(disponibilidad);
		var.setStock(stock);
		var.setAplicarDescuento(aplicar_descuento);
		
		return var;
	}

}
