package tghtechnology.tiendavirtual.Utils.izipay.Objects;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Moneda;

@Getter
public class IzipayCharge {

	private final String contrib;
	private final Integer amount;
	private final Moneda currency;
	private final String orderId;
	private final IzipayCustomer customer;
	private final Map<String, Object> metadata = new HashMap<>();
	
	/**
	 * Constructor para un cargo de IziPay.
	 * @param contrib El nombre de la solución de e-commerce y su número
	 *  de versión. (Puede ser {@code null})
	 * @param amount Monto del cargo en centavos. No se admiten décimas.
	 *  (Ejm: S/.108.99 -> 10899). Minimo 100 centavos.
	 * @param currency Código de la moneda en tres letras (Formato ISO 4217).
	 * @param orderId ID del pedido en formato String.
	 * @param customer Datos del cliente que solicitó la compra.
	 * @param securityData Datos de seguridad de la compra. Se deben
	 *  llenar todos los campos que se puedan para reducir el riesgo
	 *  de fraude y de que las solicitudes sean negadas.
	 * @param metadata Metadatos extra que se requieran para la compra.
	 */
	public IzipayCharge(String contrib,
						Integer amount,
						Moneda currency,
						String orderId,
						IzipayCustomer customer,
						IzipaySecurity securityData,
						Map<String, Object> metadata) {
		
		this.contrib = contrib;
		this.amount = amount;
		this.currency = currency;
		this.orderId = orderId;
		this.customer = customer;
		this.metadata.putAll(metadata);
		this.metadata.putAll(securityData.toMap());
	}
	
	
	
}
