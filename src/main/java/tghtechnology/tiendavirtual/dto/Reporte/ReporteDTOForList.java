package tghtechnology.tiendavirtual.dto.Reporte;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
	private BigDecimal total_egresos;
	private BigDecimal total_impuestos;
	private Integer numero_ventas;
	private Integer items_vendidos;
	
	private final Map<Integer, ItemReporteDTOForList> top_sellers = new HashMap<>();
	private final Map<Integer, ItemReporteDTOForList> top_earners = new HashMap<>();
	
	@Override
	public ReporteDTOForList from(ReporteMensual rep) {
		
		if(rep == null) return null;
		
		this.anio = rep.getId().getYear();
		this.mes = rep.getId().getMonth();
		
		this.total_ingresos = rep.getTotalIngresos();
		this.total_egresos = rep.getTotalEgresos();
		this.total_impuestos = rep.getTotalImpuestos();
		this.numero_ventas = rep.getNumeroVentas();
		this.items_vendidos = rep.getNumItemsVendidos();
		
		List<ItemReporteDTOForList> sellers = rep.getRelevant_items()
					.stream()
					.filter(ri -> ri.getTopSeller())
					.map(ri -> new ItemReporteDTOForList().from(ri))
					.sorted(new Comparator<ItemReporteDTOForList>() {
						@Override
						public int compare(ItemReporteDTOForList o1, ItemReporteDTOForList o2) {
							return o2.getVentas().compareTo(o1.getVentas());
						}
					})
					.collect(Collectors.toList());
		
		top_sellers.putAll(IntStream
				.range(0, sellers.size())
				.boxed()
				.collect(Collectors.toMap(Function.identity(), sellers::get)));
		
		List<ItemReporteDTOForList> earners = rep.getRelevant_items()
				.stream()
				.filter(ri -> ri.getTopSeller())
				.map(ri -> new ItemReporteDTOForList().from(ri))
				.sorted(new Comparator<ItemReporteDTOForList>() {
					@Override
					public int compare(ItemReporteDTOForList o1, ItemReporteDTOForList o2) {
						return o2.getIngresos().subtract(o2.getEgresos()).compareTo(o1.getIngresos().subtract(o1.getEgresos()));
					}
				})
				.collect(Collectors.toList());
		
		top_earners.putAll(IntStream
				.range(0, earners.size())
				.boxed()
				.collect(Collectors.toMap(Function.identity(), earners::get)));
		
		return this;
	}
}
