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
			Cell cell = row.createCell(col++);
			if(o instanceof BigDecimal) {
				//cell.setCellStyle(reportSheet.getStyleCurrencyFormat()); // Puede haber error aqui
				cell.setCellValue(((BigDecimal)o).doubleValue());
			}else if(o instanceof Integer){
				cell.setCellValue((Integer)o);
			} else
				cell.setCellValue(o.toString());
		}
		
	}
	
	private List<Object> main_data(){
		if(report != null) {
			return List.of(
					// Datos principales
					mes,
					report.getTotal_ingresos(),
					report.getTotal_egresos(),
					report.getTotal_impuestos(),
					report.getTotal_ingresos().subtract(report.getTotal_egresos().add(report.getTotal_impuestos())),
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
					item.getIngresos().subtract(item.getEgresos().add(item.getImpuestos()))
				);
		} else {
			return List.of(
					"",
					"",
					BigDecimal.ZERO
				);
		}
	}

	public static void addColumnNames(ReportSheet sheet, Integer row_index) {
		final List<String> columnNames = new ArrayList<>();
		columnNames.add("Mes");
		columnNames.add("Ingresos");
		columnNames.add("Egresos");
		columnNames.add("Impuestos");
		columnNames.add("Ganancias");
		columnNames.add("Ventas");
		columnNames.add("Items_vendidos");

		final List<String> sellers = new ArrayList<>();
		final List<String> earners = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			sellers.add(String.format("ID_Top_Seller_%d", i));
			sellers.add(String.format("TS_%d_Nombre", i));
			sellers.add(String.format("TS_%d_Ventas", i));
			
			earners.add(String.format("ID_Top_Earner_%d", i));
			earners.add(String.format("TE_%d_Nombre", i));
			earners.add(String.format("TE_%d_Ganancias", i));
		}
		
		columnNames.addAll(sellers);
		columnNames.addAll(earners);
		
		XSSFRow row = sheet.getSheet().createRow(row_index);
		
		int col = 0;
		// Rellenado de fila
		for(String cn : columnNames) {
			Cell cell = row.createCell(col++);
			cell.setCellValue(cn);
		}
		
	}

}
