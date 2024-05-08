package tghtechnology.tiendavirtual.Utils.izipay.Exceptions;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.Response.IzipayError;

@Getter
public class IzipayResponseException extends Exception{

	private static final long serialVersionUID = 1L;
	
	protected final HttpStatusCode statusCode;
	protected final IzipayError error;
	
	/**
	 * @param statusCode el codigo de status de la respuesta HTTP
	 * @param error El body del error en forma de IzipayError
	 */
	
	public IzipayResponseException(HttpStatusCode statusCode, IzipayError error) {
		super("La solicitud a IziPay tuvo un fallo inesperado\n");
		this.statusCode = statusCode;
		this.error = error;
	}

	
}
