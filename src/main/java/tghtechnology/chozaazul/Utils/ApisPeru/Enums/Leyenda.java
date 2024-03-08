package tghtechnology.chozaazul.Utils.ApisPeru.Enums;

import lombok.Getter;

/**
 * Elementos adicionales para la boleta/factura electrónica
 */
public enum Leyenda {

	/**
	 * Al rellenar los datos del pedido se le añade un campo con
	 * el precio total de la venta en números.<br>
	 * - Ejemplo: S/.120 -> SON CIENTO VEINTE CON 0/100 SOLES.
	 */
	MONTO_EN_LETRAS("1000", ""),
	/**
	 * Leyenda para la transferencia gratuita de un bien o servicio gratuito
	 */
	TRANSFERENCIA_GRATUITA_DE_UN_BIEN_O_SERVICIO("1002", "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE")
	;
	
	
	@Getter
	private String codigo;
	@Getter
	private String valor;
	
	Leyenda(String codigo, String valor) {
		this.codigo = codigo;
		this.valor = valor;
	}
}
