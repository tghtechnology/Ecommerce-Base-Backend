package tghtechnology.chozaazul.Utils.ApisPeru.Enums;

import lombok.Getter;

/**
 * Formas en las que puede afectar el IGV a la venta de un producto
 */
public enum AfectacionIGV {

	/**
	 * Afectación gravada: <br>
	 * El IGV si afecta a la venta
	 */
	GRAVADO_OPERACION_ONEROSA("10"),
	/**
	 * Afectación exonerada: <br>
	 * El IGV no afecta a la venta
	 */
	EXONERADO_OPERACION_ONEROSA("20")
	;
	
	
	@Getter
	private String label;
	
	AfectacionIGV(String label) {
		this.label = label;
	}
}
