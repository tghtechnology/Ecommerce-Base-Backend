package tghtechnology.tiendavirtual.dto.Reporte;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.ItemReporte;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ItemReporteDTOForList implements DTOForList<ItemReporte>{

	private Integer ventas;
	private BigDecimal ingresos;
	private BigDecimal ganancias;
	
	@Override
	public ItemReporteDTOForList from(ItemReporte item) {

		this.ventas = item.getVentas();
		this.ingresos = item.getIngresos();
		this.ganancias = item.getGanancias();
		return this;
	}
}
