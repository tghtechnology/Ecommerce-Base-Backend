package tghtechnology.tiendavirtual.Utils.ApisPeru.Exceptions;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response.ApisPeruError;

/**
 * Excepcion para representar un fallo en
 * la respuesta de ApisPeru
 */

@Getter
public class ApisPeruResponseException extends Exception{

	private static final long serialVersionUID = 1L;
	
	protected final HttpStatusCode statusCode;
	protected final ApisPeruError error;
	
	/**
	 * @param statusCode el codigo de estatus de la respuesta HTTP
	 * @param error El body del error en forma de ApisPeruError
	 */
	
	public ApisPeruResponseException(HttpStatusCode statusCode, ApisPeruError error) {
		super("La solicitud a ApisPeru tuvo un fallo inesperado\n");
		this.statusCode = statusCode;
		this.error = error;
	}

}
