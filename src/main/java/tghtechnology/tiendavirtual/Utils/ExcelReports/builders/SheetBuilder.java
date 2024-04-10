package tghtechnology.tiendavirtual.Utils.ExcelReports.builders;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public interface SheetBuilder {

	/**
	 * Obtiene la hoja sobre la que se está trabajando.
	 * @return La hoja de excel sobre la que se va a construir.
	 */
	public XSSFSheet getSheet();
	
	/**
	 * Construye la hoja
	 */
	public void build();
}
