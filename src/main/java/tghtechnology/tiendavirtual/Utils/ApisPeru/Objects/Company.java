package tghtechnology.tiendavirtual.Utils.ApisPeru.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Company {

	private String ruc;
	private String razonSocial;
	private String nombreComercial;
	private Address address;
	private String email;
	private String telephone;
	
	public Company(String ruc,
					String razonSocial,
					String nombreComercial,
					Address address,
					String email,
					String telephone) {
		this.ruc = ruc;
		this.razonSocial = razonSocial;
		this.nombreComercial = nombreComercial;
		this.address = address;
		this.email = email;
		this.telephone = telephone;
	}
	
	
}
