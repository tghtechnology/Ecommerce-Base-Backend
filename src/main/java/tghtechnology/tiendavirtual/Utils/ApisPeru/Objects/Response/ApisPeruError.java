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
 * Respuesta de error de ApisPeru
 */
public class ApisPeruError {

	private String message;
	private String field;
	
}
