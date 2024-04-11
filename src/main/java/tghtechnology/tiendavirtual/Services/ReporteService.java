package tghtechnology.tiendavirtual.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Enums.Mes;
import tghtechnology.tiendavirtual.Models.ReporteItem;
import tghtechnology.tiendavirtual.Models.ReporteMensual;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Models.composite.ReportMonth;
import tghtechnology.tiendavirtual.Repository.ItemReporteRepository;
import tghtechnology.tiendavirtual.Repository.ItemRepository;
import tghtechnology.tiendavirtual.Repository.ReporteRepository;
import tghtechnology.tiendavirtual.Repository.VentaRepository;
import tghtechnology.tiendavirtual.Utils.ExcelReports.ReportSheet;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Reporte.ReporteDTOForList;

@Service
@AllArgsConstructor
public class ReporteService {

    private ReporteRepository repRepository;
    private ItemReporteRepository riRepository;
    private VentaRepository venRepository;
    private ItemRepository itemRepository;

    /**
     * Lista todos los reportes de un año en específico
     * 
     * @return Una lista de los reportes en formato DTOForList
     */
    public Map<Mes, ReporteDTOForList> listarReportes (Integer anio){
        Map<Mes, ReporteDTOForList> repMap = new TreeMap<>();
        List<ReporteMensual> reps = repRepository.listarPorAnio(anio);
        
        // Obtener los registros que ya hayan en la base de datos
        reps.forEach( x -> {
            repMap.put(x.getId().getMonth(), new ReporteDTOForList().from(x));
        });
        // Generar los registros que no existan ya
        for(Mes mes : Mes.values()) {
        	if(!repMap.containsKey(mes)) {
        		repMap.put(mes, new ReporteDTOForList().from(generarReporteMensual(mes, anio)));
        	}
        }
        
        return repMap;
    }
    
    public byte[] generarReporteExcel(Map<Mes, ReporteDTOForList> reporte, Integer anio) throws IOException {
    	
    	XSSFWorkbook book = new XSSFWorkbook();
    	new ReportSheet(book, reporte).build();
    	
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	try {
    		book.write(bos);
    	} finally {
    		bos.close();
    	}
    	return bos.toByteArray();
    }
    
    @Transactional
    public ReporteDTOForList listarReporte(Integer anio, Mes mes) {
    	return new ReporteDTOForList().from(generarReporteMensual(mes, anio));
    }
    
    @Transactional //rollbackFor
    public ReporteMensual generarReporteMensual(Mes mes,Integer anio) {
    	
    	Integer _mes = mes.ordinal()+1;
    	
    	// Devolver null si el mes solicitado aún no ocurre
    	if(LocalDate.of(anio, _mes, 1).isAfter(LocalDate.now())) return null;
    	
    	final AtomicReference<BigDecimal> ingresos = new AtomicReference<>(BigDecimal.ZERO); // Ingresos totales del mes
    	final AtomicReference<BigDecimal> egresos = new AtomicReference<>(BigDecimal.ZERO); // Egresos totales del mes
    	final AtomicReference<BigDecimal> impuestos = new AtomicReference<>(BigDecimal.ZERO); // Impuestos totales del mes
    	final AtomicReference<Integer> num_items = new AtomicReference<>(0); // Numero de items vendidos en el mes
    	final Map<Integer, ReporteItem> items = new HashMap<>(); // Reportes de todos los items vendidos en el mes (para filtrar)
    	Integer num_ventas; // Numero de ventas realizadas en el mes
    	
    	//Obtener todas las ventas del mes
    	Set<Venta> ventas = venRepository.findAllByFechaBetween(LocalDateTime.of(anio, _mes, 1, 0, 0), LocalDateTime.of(anio+((_mes+1)/13), (_mes+1)%13, 1, 0, 0))
    			.stream()
        		.filter(v -> v.getEstado_pedido() != EstadoPedido.CANCELADO)
        		.collect(Collectors.toSet());
    	// Calcular numero de ventas
    	num_ventas = ventas.size();
    	// Calcular ingresos, egresos y cantidad de items vendidos
		ventas.forEach(ven -> {
			ven.getDetalles().forEach(dv -> {
				Integer vend = dv.getCantidad().intValue();
				BigDecimal cant = new BigDecimal(vend);
				BigDecimal ingr = dv.getPrecio_unitario().multiply(cant);
				BigDecimal egre = dv.getCosto_unitario().multiply(cant);
				BigDecimal imps = dv.getPrecio_unitario().multiply(new BigDecimal(ven.getPorcentaje_igv()/100.0).setScale(2, RoundingMode.HALF_UP));
				// Sumar ingresos totales
				ingresos.set(ingresos.get().add(ingr));
				// Sumar egresos totales
				egresos.set(egresos.get().add(egre));
				// Sumar impuestos totales
				impuestos.set(impuestos.get().add(imps));
				// Sumar cantidad de items totales
				num_items.set(num_items.get() + vend);
				
				// Generar reporte del item si no existe
				ReporteItem ri = items.get(dv.getId_item());
				if(ri == null) {
					ri = new ReporteItem();
					ri.setItem(itemRepository.findById(dv.getId_item()).orElseThrow(() -> new IdNotFoundException("item")));
					items.put(dv.getId_item(), ri);
				}
				// Asignar valores al reporte de item
				ri.setEgresos(ri.getEgresos().add(egre));
				ri.setIngresos(ri.getIngresos().add(ingr));
				ri.setImpuestos(ri.getImpuestos().add(imps));
				ri.setVentas(ri.getVentas() + vend);
			});
		});
    	// Guardar reporte mensual
		ReporteMensual rep = new ReporteMensual();
		rep.setTotalIngresos(ingresos.get());
		rep.setTotalEgresos(egresos.get());
		rep.setTotalImpuestos(impuestos.get());
		rep.setNumeroVentas(num_ventas);
		rep.setNumItemsVendidos(num_items.get());
		rep.setId(new ReportMonth(anio.shortValue(), mes));
		
		// Devolver null si el mes está vacío
		if(num_ventas == 0) return null;
		
		// Solo guardar el reporte en la BD si el mes no es el actual
		Boolean save = LocalDate.now().getMonthValue() != _mes;
		
		final ReporteMensual frep = (save) ? repRepository.save(rep) : rep;
		
		// Asignar los top sellers y top earners
		List<ReporteItem> top_sellers = new ArrayList<>();
		List<ReporteItem> top_earners = new ArrayList<>();
		Set<ReporteItem> relevant = new HashSet<>();
		
		// Obtener el top 5 de top sellers
		top_sellers = items.values()
				.stream()
				.sorted(new Comparator<ReporteItem>() {
					@Override
					public int compare(ReporteItem o1, ReporteItem o2) {
						return o1.getVentas().compareTo(o2.getVentas());
					}
				})
				.limit(5)
				.toList();
		top_sellers.forEach(ri -> {ri.setTopSeller(true); ri.setReporte(frep); });
		
		// Obtener el top 5 de top earners
		top_earners = items.values()
				.stream()
				.sorted(new Comparator<ReporteItem>() {
					@Override
					public int compare(ReporteItem o1, ReporteItem o2) {
						return o1.getIngresos().subtract(o1.getEgresos()).compareTo(o2.getIngresos().subtract(o2.getEgresos()));
					}
				})
				.limit(5)
				.toList();
		top_earners.forEach(ri -> {ri.setTopEarner(true); ri.setReporte(frep); });
		
		relevant.addAll(top_sellers);
		relevant.addAll(top_earners);

		if(save) riRepository.saveAll(relevant);
		frep.setRelevant_items(relevant);
		
    	return frep;
    }
    
}