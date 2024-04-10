package tghtechnology.tiendavirtual.Utils.ExcelReports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.Mes;
import tghtechnology.tiendavirtual.Utils.ExcelReports.builders.RowBuilder;
import tghtechnology.tiendavirtual.dto.Reporte.ItemReporteDTOForList;
import tghtechnology.tiendavirtual.dto.Reporte.ReporteDTOForList;

@AllArgsConstructor
public class ReportRow implements RowBuilder<ReportSheet>{
	
	private Mes mes;
	private ReporteDTOForList report;
	
	@Override
	public void buildAndWrite(ReportSheet reportSheet, Integer row_id) {
		XSSFRow row = reportSheet.getSheet().createRow(row_id);
		
		List<Object> data = new ArrayList<>();
		
		// Main data
		data.addAll(main_data());
		// Top sellers
		for(int i = 0; i < 5; i++) {
			data.addAll(top_seller(i));
		}
		// Top earners
		for(int i = 0; i < 5; i++) {
			data.addAll(top_earner(i));
		}
		
		int col = 0;
		// Rellenado de fila
		for(Object o : data) {
			Cell cell = row.createCell(col);
			cell.setCellValue(o.toString());
			if(o instanceof BigDecimal)
				cell.setCellStyle(reportSheet.getStyleCurrencyFormat()); // Puede haber error aqui
		}
		
	}
	
	private List<Object> main_data(){
		if(report != null) {
			return List.of(
					// Datos principales
					mes,
					report.getTotal_ingresos(),
					report.getTotal_ingresos().subtract(report.getTotal_ganancias()),
					report.getTotal_ganancias(),
					report.getNumero_ventas(),
					report.getItems_vendidos()
				);
		} else {
			return List.of(
					// Datos principales
					mes,
					BigDecimal.ZERO,
					BigDecimal.ZERO,
					BigDecimal.ZERO,
					0,
					0
				);
		}
	}
	
	private List<Object> top_seller(Integer index){
		if(report != null && report.getTop_sellers().get(index) != null) {
			ItemReporteDTOForList item = report.getTop_sellers().get(index);
			return List.of(
					item.getId_item(),
					item.getNombre_item(),
					item.getVentas()
				);
		} else {
			return List.of(
					"",
					"",
					BigDecimal.ZERO
				);
		}
	}
	
	private List<Object> top_earner(Integer index){

		if(report != null && report.getTop_sellers().get(index) != null) {
			ItemReporteDTOForList item = report.getTop_earners().get(index);
			return List.of(
					item.getId_item(),
					item.getNombre_item(),
					item.getGanancias()
				);
		} else {
			return List.of(
					"",
					"",
					BigDecimal.ZERO
				);
		}
	}

}
