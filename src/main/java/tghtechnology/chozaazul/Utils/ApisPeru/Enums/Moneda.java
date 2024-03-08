package tghtechnology.chozaazul.Utils.ApisPeru.Enums;

import lombok.Getter;

/**
 * Tipos de moneda que se pueden utilizar.
 */
public enum Moneda {

	/**
	 * Moneda Soles
	 */
	PEN("PEN"),
	/**
	 * Moneda Dólares
	 */
	USD("USD");
	
	
	@Getter
	private String label;
	
	Moneda(String label) {
		this.label = label;
	}
}
