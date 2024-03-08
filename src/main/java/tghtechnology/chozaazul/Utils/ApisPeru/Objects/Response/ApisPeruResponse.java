package tghtechnology.chozaazul.Utils.ApisPeru.Objects.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Respuesta saisfactoria de ApisPeru
 */
public class ApisPeruResponse {

	/**
	 * xml generado para el envío a la sunat
	 */
	private String xml;
	/**
	 * Hash de la boleta/factura enviada
	 */
	private String hash;
	/**
	 * Respuesta de la sunat
	 */
	private BillResult sunatResponse;
	
}
