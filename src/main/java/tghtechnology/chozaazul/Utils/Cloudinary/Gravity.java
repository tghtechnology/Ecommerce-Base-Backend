package tghtechnology.chozaazul.Utils.Cloudinary;

import lombok.Getter;


/**
 * Tipos de gravedad que se pueden utilizar al recortar
 * imagenes en Cloudinary
 */
public enum Gravity {

	/**
	 * Posicion Central (Default)
	 */
	CENTER("center"),
	/**
	 * Cardinal Norte
	 */
	NORTH("north"),
	/**
	 * Cardinal Este
	 */
	EAST("east"),
	/**
	 * Cardinal Sur
	 */
	SOUTH("south"),
	/**
	 * Cardinal Oeste
	 */
	WEST("west"),
	/**
	 * Cardinal Noreste
	 */
	NORTHEAST("north_east"),
	/**
	 * Cardinal Sureste
	 */
	SOUTHEAST("south_east"),
	/**
	 * Cardinal Noroeste
	 */
	NORTHWEST("north_west"),
	/**
	 * Cardinal Suroeste
	 */
	SOUTHWEST("south_west"),
	/**
	 * Especial: Centra el recorte en los rostros (personas)
	 */
	FACE("face"),
	/**
	 * Especial: Centra el recorte automáticamente según el contexto de la imagen (paisajes)
	 * <p>
	 * <dt><span class="strong">Nota:</dt><dd>Solo se puede utilizar en conjunto con el recorte "c_scale"</dd>
	 */
	LIQUID("liquid"),
	/**
	 * Especial: Centra el recorte automáticamente según el contenido (fondo no importante)
	 */
	AUTO("auto")
	;
	
	
	@Getter
	private String label;
	
	Gravity(String label) {
		this.label = label;
	}
}
