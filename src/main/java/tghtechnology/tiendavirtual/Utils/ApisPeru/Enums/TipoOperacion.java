package tghtechnology.tiendavirtual.Utils.ApisPeru.Enums;

import lombok.Getter;
/**
 * Tipos de operaciones de venta
 */
public enum TipoOperacion {

	VENTA_INTERNA("0101"),
	VENTA_INTERNA_ANTICIPOS("0102"),
	VENTA_INTERNA_ITINERANTE("0103")
	;
	
	
	@Getter
	private String label;
	
	TipoOperacion(String label) {
		this.label = label;
	}
}
