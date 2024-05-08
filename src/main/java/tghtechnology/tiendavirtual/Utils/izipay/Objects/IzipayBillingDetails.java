package tghtechnology.tiendavirtual.Utils.izipay.Objects;

import lombok.Getter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;

@Getter
public class IzipayBillingDetails {

	private final String address;
	private final String cellPhoneNumber;
	private final String country;
	private final String firstName;
	private final String lastname;
	private final String razon_social;
	private final String identityType;
	private final String identityCode;
	
	/**
	 * Constructor de un objeto BillingDetails para IziPay
	 * @param address Dirección del cliente.
	 * @param phoneNumber Número de teléfono del cliente.
	 * @param country País de origen del cliente según la norma ISO 3166-1 (PE para Perú).
	 * @param firstName Nombres del cliente.
	 * @param lastname Apellidos del cliente.
	 * @param identityType Tipo de documento de identidad del cliente (DNI-PER).
	 * @param identityCode Número de documento del cliente. (Número de DNI)
	 */
	public IzipayBillingDetails(String address,
								String cellPhoneNumber,
								String country,
								String firstName,
								String lastname,
								String razon_social,
								TipoDocIdentidad identityType,
								String identityCode) {
		this.address = address;
		this.cellPhoneNumber = cellPhoneNumber;
		this.country = country;
		this.firstName = firstName;
		this.lastname = lastname;
		this.razon_social = razon_social;
		this.identityType = identityType == TipoDocIdentidad.DNI ? "DNI_PER" : "NAN";
		this.identityCode = identityCode;
	}
	
}
