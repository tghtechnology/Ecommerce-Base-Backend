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
 * Respuesta de error de la SUNAT. Contiene el código de error y el mensaje especificando el origen del error.
 *
 */
public class SunatError {

	private String code;
	private String message;
	
}
