package tghtechnology.chozaazul.Utils.ApisPeru.Enums;

import lombok.Getter;

/**
 * Unidades de medida para los productos
 */
public enum UnidadMedida {

	BOLSA("BG"),
	CAJA("BX"),
	DOCENA("DZN"),
	DOCENA_POR_10_6("DZP"),
	GRAMO("GRM"),
	JUEGO("SET"),
	KILOGRAMO("KGM"),
	PIEZAS("C62"),
	UNIDAD_BIENES("NIU"),
	SERVICIO("ZZ")
	;
	
	
	@Getter
	private String label;
	
	UnidadMedida(String label) {
		this.label = label;
	}
}
