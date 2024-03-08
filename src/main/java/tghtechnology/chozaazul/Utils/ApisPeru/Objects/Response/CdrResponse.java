package tghtechnology.chozaazul.Utils.ApisPeru.Objects.Response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
/**
 * Respuesta de una solicitur satrisfactoria a la SUNAT.<br>
 * Su presencia NO es prueba de que la solicitud se aprobó.
 *
 */
public class CdrResponse {

	/**
	 * Si la solicitud fue aceptada o no.
	 */
	private Boolean accepted;
	/**
	 * ID de la solicitud
	 */
	private String id;
	/**
	 * Código de respuesta
	 */
	private String code;
	/**
	 * Descripción de la respuesta
	 */
	private String description;
	/**
	 * Notas extra sobre recomendaciones que pueden afectar 
	 */
	private Set<String> notes;
	
}
