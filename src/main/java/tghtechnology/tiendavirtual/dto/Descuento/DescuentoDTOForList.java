package tghtechnology.tiendavirtual.dto.Descuento;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Descuento;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForListMinimal;

@Getter
@Setter
@NoArgsConstructor
public class DescuentoDTOForList implements DTOForList<Descuento>{

	private Integer id_descuento;
	private BigDecimal nuevo_precio;
	private Integer porcentaje;
	private LocalDate programacion_inicio;
	private LocalDate programacion_final;
	private Boolean activado;
	
	private ItemDTOForListMinimal item;
	
	@Override
	public DescuentoDTOForList from(Descuento desc) {
		this.id_descuento = desc.getId_descuento();
		this.porcentaje = desc.getPorcentaje();
		this.programacion_inicio = desc.getProgramacion_inicio();
		this.programacion_final = desc.getProgramacion_final();
		this.activado = desc.getActivado();
		
		this.item = new ItemDTOForListMinimal().from(desc.getItem());
		
		return this;
	}

	
	
}
