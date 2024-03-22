package tghtechnology.tiendavirtual.dto.Descuento;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Descuento;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class DescuentoDTOForListMinimal implements DTOForList<Descuento>{

	private BigDecimal nuevo_precio;
	private Integer porcentaje;
	private LocalDate programacion_final;
	
	@Override
	public DescuentoDTOForListMinimal from(Descuento desc) {
		this.porcentaje = desc.getPorcentaje();
		this.programacion_final = desc.getProgramacion_final();
		
		return this;
	}

	
	
}
