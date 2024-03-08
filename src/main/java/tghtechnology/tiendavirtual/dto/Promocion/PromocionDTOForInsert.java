package tghtechnology.tiendavirtual.dto.Promocion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.dto.Promocion.PromocionPlato.PromocionPlatoDTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class PromocionDTOForInsert {
	
	/**
	 * La cantidad de dinero a descontar
	 */
	@DecimalMin(value = "0.0", inclusive = true, message = "El descuento debe ser mayor a S/.0.00")
	private BigDecimal descuento;
	
	@NotNull(message = "No puede ser nulo")
	private boolean repetible;
	
	@NotNull(message = "No puede ser nulo")
	private LocalDate fecha_inicio;
	
	@NotNull(message = "No puede ser nulo")
	private LocalDate fecha_finalizacion;
	
	@NotNull(message = "No puede ser nulo")
	@Valid
	private List<PromocionPlatoDTOForInsert> platos = new ArrayList<>();
	
}
