package tghtechnology.tiendavirtual.Utils.izipay.Objects;

import lombok.Getter;

@Getter
public class IzipayCustomer {

	private final String email;
	private final IzipayBillingDetails billingDetails;
	
	/**
	 * Constructor de un objeto Cliente de iziPay.
	 * @param email Correo electrónico del cliente.
	 * @param billingDetails Detalles de facturación del cliente.
	 */
	public IzipayCustomer(String email, 
						  IzipayBillingDetails billingDetails) {
		this.email = email;
		this.billingDetails = billingDetails;
	}
}
