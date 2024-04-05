package tghtechnology.tiendavirtual.dto.Reporte;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.Mes;
import tghtechnology.tiendavirtual.Models.ReporteMensual;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ReporteDTOForList implements DTOForList<ReporteMensual>{

	private Short anio;
	private Mes mes;
	
	private BigDecimal total_ingresos;
	private BigDecimal total_ganancias;
	private Integer numero_ventas;
	private Integer productos_vendidos;
	
	private final List<ItemReporteDTOForList> top_sellers = new ArrayList<>();
	private final List<ItemReporteDTOForList> top_earners = new ArrayList<>();
	
	@Override
	public ReporteDTOForList from(ReporteMensual rep) {
		this.anio = rep.getId().getYear();
		this.mes = rep.getId().getMonth();
		
		this.total_ingresos = rep.getTotalIngresos();
		this.total_ganancias = rep.getTotalGanancias();
		this.numero_ventas = rep.getNumeroVentas();
		this.productos_vendidos = rep.getNumProductosVendidos();
		
		rep.getRelevant_items().forEach(item -> {
			ItemReporteDTOForList item_dto = new ItemReporteDTOForList().from(item);
			if(item.getTopSeller())
				top_sellers.add(item_dto);
			if(item.getTopEarner())
				top_earners.add(item_dto);
		});
		return this;
	}
}
