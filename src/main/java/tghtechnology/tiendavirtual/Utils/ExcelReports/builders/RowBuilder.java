package tghtechnology.tiendavirtual.Utils.ExcelReports.builders;

public interface RowBuilder<T extends SheetBuilder> {

	/**
	 * Convierte los datos contenidos en el {@link tghtechnology.tiendavirtual.Utils.ExcelReports.builders.RowBuilder RowBuilder}
	 * en forma de {@link org.apache.poi.xssf.usermodel.XSSFRow XSSFRow}
	 * y los inserta en {@link org.apache.poi.xssf.usermodel.XSSFSheet XSSFSheet}
	 * en la posición indicada.
	 * @param sheet La hoja en donde se insertará la fila.
	 * @param row_id La ID de la fila en la que se insertará.
	 */
	public void buildAndWrite(T sheetBuilder, Integer row_id);
	
}
