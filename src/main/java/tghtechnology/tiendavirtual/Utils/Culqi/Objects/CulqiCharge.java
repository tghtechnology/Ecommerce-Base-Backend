package tghtechnology.tiendavirtual.Utils.Culqi.Objects;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Moneda;

@Getter
@Setter
public class CulqiCharge {

	private String source_id;
	
	private Integer amount;
	private Moneda currency_code;
	private String email;
	private String description;
	private AntiFraudDetails anti_fraud;
	private final Integer installments = 0;
	private final Map<String, Object> metadata = new HashMap<>();
	private Auth3DS authentication_3DS;
	
	/**
	 * Constructor de un cargo de Culqi.
	 * @param source_id ID del objeto token, objeto Yape u objeto tarjeta que se va usar para realizar el cargo.
	 * @param amount Monto del cargo en centavos. No se admiten décimas. (Ejm: S/.108.99 -> 10899). Minimo 100 centavos.
	 * @param currency_code Código de la moneda en tres letras (Formato ISO 4217).
	 * @param email Correo electrónico del cliente. Máximo 50 caracteres.
	 * @param description Descripción del cargo a realizarse. Máximo 80 caracteres.
	 * @param anti_fraud Datos de la persona que realiza la compra, para detectar posibles fraudes.
	 * @param authentication_3ds Parámetros para la autenticación 3-D Secure
	 */
	public CulqiCharge(String source_id,
					   Integer amount,
					   Moneda currency_code,
					   String email,
					   String description,
					   AntiFraudDetails anti_fraud,
					   Auth3DS authentication_3ds) {
		this.source_id = source_id;
		this.amount = amount;
		this.currency_code = currency_code;
		this.email = email;
		this.description = description;
		this.anti_fraud = anti_fraud;
		authentication_3DS = authentication_3ds;
	}
	
	public Map<String, Object> toMap(){
		return new ObjectMapper()
				.convertValue(this, new TypeReference<Map<String, Object>>() {});
	}
	
}
