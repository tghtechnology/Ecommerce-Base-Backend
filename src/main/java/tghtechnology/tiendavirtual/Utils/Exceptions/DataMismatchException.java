package tghtechnology.tiendavirtual.Utils.Exceptions;

import java.util.NoSuchElementException;

import lombok.Getter;

/**
 * Excepcion para representar un fallo en
 * un dato introducido por el usuario
 *
 */

@Getter
public class DataMismatchException extends NoSuchElementException{

	private static final long serialVersionUID = 1L;
	
	protected final String var;
	protected final String msg;
	
	/**
	 * Construye un DataMismatchException para rpresentar un fallo en validación de datos
	 * 
	 * @param Variable La variable a validar
	 * @param Mensaje El mensaje de error
	 */
	
	public DataMismatchException(String var, String msg) {
		super("Error al validar un dato\n");
		this.var = var;
		this.msg = msg;
	}
	
	public void printWarn() {
		System.err.printf("[%s]: %s\n", var, msg);
	}

}
