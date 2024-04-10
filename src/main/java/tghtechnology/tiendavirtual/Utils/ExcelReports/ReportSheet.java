package tghtechnology.tiendavirtual.Utils.ExcelReports;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.Getter;
import tghtechnology.tiendavirtual.Enums.Mes;
import tghtechnology.tiendavirtual.Utils.ExcelReports.builders.SheetBuilder;
import tghtechnology.tiendavirtual.dto.Reporte.ReporteDTOForList;


public class ReportSheet implements SheetBuilder{

	private final XSSFSheet sheet;
	private final Map<Mes, ReporteDTOForList> reportes;
	
	@Getter
	private final XSSFCellStyle styleCurrencyFormat;
	
	public ReportSheet(XSSFWorkbook book, Map<Mes, ReporteDTOForList> reps) {
		sheet = book.createSheet("Reporte");
		reportes = reps;
		
		Short format = new XSSFCreationHelper(book).createDataFormat().getFormat("_-S/* #,##0.00_-;-S/* #,##0.00_-;_-S/* \"-\"??_-;_-@_-");
		
		styleCurrencyFormat = book.createCellStyle();
		styleCurrencyFormat.setDataFormat(format);
		
	}
	
	@Override
	public XSSFSheet getSheet() { return sheet; }


	@Override
	public void build() {
		AtomicInteger row_index = new AtomicInteger(0);
		reportes.entrySet().forEach(entry -> {
			new ReportRow(entry.getKey(), entry.getValue()).buildAndWrite(this, row_index.getAndIncrement());
		});
	}

}
