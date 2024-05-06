package tghtechnology.tiendavirtual.Utils.Culqi.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auth3DS {

	private String xid;
	private String cavv;
	private String directoryServerTransactionId;
	private String eci;
	private String protocolVersion;
	
	/**
	 * Constructor de una autenticación para la protección 3-D Secure (3DS)
	 * utilizada por Visa y MasterCard.
	 * @param xid Cadena utilizada por Visa y Mastercard que identifica 
	 * una transacción específica en los servidores de directorio. 
	 * Este valor de cadena debe permanecer constante a lo largo de un 
	 * historial de transacciones.
	 * @param cavv Valor de verificación de autenticación del titular de la tarjeta.
	 * @param directoryServerTransactionId El ID de transacción del servidor 
	 * de directorio es generado por el servidor de directorio de Mastercard 
	 * durante la transacción de autenticación y devuelto al comerciante con 
	 * los resultados de la autenticación.
	 * @param eci El indicador de comercio numérico que indica al banco el 
	 * grado de cambio de responsabilidad logrado durante el proceso de 
	 * autenticación del pagador.
	 * @param protocolVersion Este campo contiene la versión de 3-D Secure 
	 * que se utilizó para procesar la transacción. Por ejemplo, 1.0.2 o 2.0.0.
	 */
	public Auth3DS(String xid, 
				   String cavv,
				   String directoryServerTransactionId,
				   String eci,
				   String protocolVersion) {
		this.xid = xid;
		this.cavv = cavv;
		this.directoryServerTransactionId = directoryServerTransactionId;
		this.eci = eci;
		this.protocolVersion = protocolVersion;
	}
	
	
	
}
