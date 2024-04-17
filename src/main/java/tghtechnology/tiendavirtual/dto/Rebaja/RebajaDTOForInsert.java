package tghtechnology.tiendavirtual.dto.Rebaja;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Rebaja;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;
import tghtechnology.tiendavirtual.Utils.Validation.Constraints.InDateRange;

@Getter
@Setter
@NoArgsConstructor
public class RebajaDTOForInsert implements DTOForInsert<Rebaja>{
	
	@NotEmpty(message = "No puede estar vacío")
	@Pattern(regexp = "^[\\w\\-\\s]+$", message = "Texto inválido.")
	@Size(min = 3, max = 50, message = "El tamaño debe estar entre 3 y 50 caracteres")
	private String nombre;
	
	@Size(min = 3, max = 400, message = "El tamaño debe estar entre 3 y 400 caracteres")
	private String descripcion;
	
	@InDateRange(message = "Fecha inválida")
	private LocalDate fecha_inicio;
	
	@InDateRange(message = "Fecha inválida")
	private LocalDate fecha_fin;
	
	@NotNull(message = "No puede ser nulo")
	private Boolean es_descuento;
	
	private Integer valor_descuento;
	
	private Boolean es_evento;
	
	@NotNull(message = "No puede ser nulo")
	private Boolean activo;
	
	@NotNull(message = "No puede ser nulo")
	private Set<String> items;
	
	@NotNull(message = "No puede ser nulo")
	private Set<String> categorias;
	
	@Override
	public Rebaja toModel() {
		Rebaja reb = new Rebaja();
		
		reb.setNombre(nombre);
		reb.setText_id(Rebaja.transform_id(nombre));
		reb.setDescripcion(descripcion);
		reb.setFecha_inicio(fecha_inicio);
		reb.setFecha_fin(fecha_fin);
		reb.setEs_descuento(es_descuento);
		reb.setValor_descuento(valor_descuento == null ? 0 : valor_descuento);
		reb.setEs_evento(es_evento == null ? false : es_evento);
		reb.setActivo(activo);
		reb.setEstado(true);
		
		return reb;
	}

	@Override
	public Rebaja updateModel(Rebaja reb) {
		reb.setFecha_inicio(fecha_inicio);
		reb.setFecha_fin(fecha_fin);
		reb.setEs_descuento(es_descuento);
		reb.setValor_descuento(valor_descuento == null ? 0 : valor_descuento);
		
		return reb;
	}
}
