package tghtechnology.tiendavirtual.dto.Plato;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoPlato;

@Getter
@Setter
@NoArgsConstructor
public class PlatoDTOForInsert {

	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 40, message = "El nombre del plato debe tener menos de 40 caracteres")
	private String nombre_plato;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 150, message = "La descripción del plato debe tener menos de 150 caracteres")
    private String descripcion;
	
	@DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor a S/.0.00")
    private BigDecimal precio;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    private String disponibilidad;
    
	@NotNull(message = "No puede ser nulo")
    private int id_categoria;
    
    //Antes de insertar
    @NotNull(message = "No puede ser nulo")
    private TipoPlato tipo_plato;
}
