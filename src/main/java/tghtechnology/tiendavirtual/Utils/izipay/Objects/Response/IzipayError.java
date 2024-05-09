package tghtechnology.tiendavirtual.Utils.izipay.Objects.Response;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa un error de respuesta de IziPay.<p>
 * Consiste en los campos:<p>
 * - <strong>errorCode</strong>: Código de error (con formato [PREFIJO]_[CÓDIGO]).<br>
 * - <strong>errorMessage</strong>: Mensaje de error.<br>
 * - <strong>detailedErrorCode</strong>: Código de error detallado (o nulo).<br>
 * - <strong>detailedErrorMessage</strong>: Mensaje detallado (o nulo).
 */
@Getter
@Setter
public class IzipayError {
	
	private String errorCode;
	private String errorMessage;
	private String detailedErrorCode;
	private String detailedErrorMessage;
	private String ticket;
	private String shopId;
	private String _type;

}
