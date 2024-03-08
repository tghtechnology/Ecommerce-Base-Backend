package tghtechnology.chozaazul.dto.Reserva;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.dto.Cliente.ClienteDTOForInsert;
@Getter
@Setter
@NoArgsConstructor
public class ReservaDTOForInsert {
    
	@DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal pago_reserva;
	
	@NotNull(message = "No puede ser nulo")
    private LocalDate fecha_reserva;
	
	@NotNull(message = "No puede ser nulo")
    private LocalTime hora_inicio;
	
	@NotNull(message = "No puede ser nulo")
    private Integer empleado_encargado;
    
	@NotNull(message = "No puede ser nulo")
    private ClienteDTOForInsert cliente;

}
