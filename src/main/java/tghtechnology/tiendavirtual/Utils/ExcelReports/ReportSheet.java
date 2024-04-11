package tghtechnology.tiendavirtual.Utils.ExcelReports;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import tghtechnology.tiendavirtual.Enums.Mes;
import tghtechnology.tiendavirtual.Utils.ExcelReports.builders.SheetBuilder;
import tghtechnology.tiendavirtual.dto.Reporte.ReporteDTOForList;


public class ReportSheet implements SheetBuilder{

	private final XSSFSheet sheet;
	private final Map<Mes, ReporteDTOForList> reportes;
	
//	@Getter
//	private final XSSFCellStyle styleCurrencyFormat;
	
	public ReportSheet(XSSFWorkbook book, Map<Mes, ReporteDTOForList> reps) {
		sheet = book.createSheet("Reporte");
		reportes = reps;
		
//		Short format = new XSSFCreationHelper(book).createDataFormat().getFormat("_-S/* #,##0.00_-;-S/* #,##0.00_-;_-S/* \"-\"??_-;_-@_-");
//		
//		styleCurrencyFormat = book.createCellStyle();
//		styleCurrencyFormat.setDataFormat(format);
		
	}
	
	@Override
	public XSSFSheet getSheet() { return sheet; }


	@Override
	public void build() {
		AtomicInteger row_index = new AtomicInteger(0);
		ReportRow.addColumnNames(this, row_index.getAndIncrement());
		reportes.entrySet().forEach(entry -> {
			new ReportRow(entry.getKey(), entry.getValue()).buildAndWrite(this, row_index.getAndIncrement());
		});
		XSSFRow row = sheet.createRow(row_index.getAndIncrement());
		row.createCell(0).setCellValue("TOTAL");
		row.createCell(1).setCellFormula("SUM(B2:B13)");
		row.createCell(2).setCellFormula("SUM(C2:C13)");
		row.createCell(3).setCellFormula("SUM(D2:D13)");
		row.createCell(4).setCellFormula("SUM(E2:E13)");
		row.createCell(5).setCellFormula("SUM(F2:F13)");
		row.createCell(6).setCellFormula("SUM(G2:G13)");
	}

}
