package tghtechnology.tiendavirtual.dto.Reporte;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.ReporteItem;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ItemReporteDTOForList implements DTOForList<ReporteItem>{

	private Integer id_item;
	private String nombre_item;
	private Integer ventas;
	private BigDecimal ingresos;
	private BigDecimal ganancias;
	
	@Override
	public ItemReporteDTOForList from(ReporteItem item) {

		this.id_item = item.getItem().getId_item();
		this.nombre_item = item.getItem().getNombre();
		this.ventas = item.getVentas();
		this.ingresos = item.getIngresos();
		this.ganancias = item.getGanancias();
		return this;
	}
}
