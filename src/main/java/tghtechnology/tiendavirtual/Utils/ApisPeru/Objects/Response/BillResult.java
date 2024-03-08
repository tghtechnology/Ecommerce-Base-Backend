package tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Respuesta de la SUNAT
 *
 */
public class BillResult {

	/**
	 * Si la solicitud duvo éxito o no.
	 */
	private Boolean success;
	/**
	 * El error si es que hubo uno. En formato SunatError
	 */
	private SunatError error;
	private String cdrZip;
	private CdrResponse cdrResponse;
	
}
