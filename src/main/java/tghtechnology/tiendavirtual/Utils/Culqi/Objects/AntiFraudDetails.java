package tghtechnology.tiendavirtual.Utils.Culqi.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AntiFraudDetails {

	private String address;
	private String address_city;
	private String country_code;
	private String first_name;
	private String last_name;
	private String phone_number;
	
	/**
	 * Constructor de detalles anti-fraude de Culqi
	 * @param address Dirección del cliente
	 * @param address_city Ciudad de residencia del cliente
	 * @param country_code Código de país del cliente (PE para Perú)
	 * @param first_name Nombres del cliente
	 * @param last_name Apellidos del cliente
	 * @param phone_number Número de teléfono del cliente
	 */
	public AntiFraudDetails(String address, 
							String address_city,
							String country_code,
							String first_name,
							String last_name,
							String phone_number) {
		super();
		this.address = address;
		this.address_city = address_city;
		this.country_code = country_code;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone_number = phone_number;
	}
	
	
	
}
