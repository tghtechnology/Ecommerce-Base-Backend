package tghtechnology.chozaazul.Utils.ApisPeru.Objects;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.chozaazul.Utils.ApisPeru.Enums.TipoDocIdentidad;

@Getter
@Setter
public class Client {

	private String tipoDoc;
	private String numDoc;
	private String rznSocial;
	private Address address;
	private String email;
	private String telephone;
	
	public Client(TipoDocIdentidad tipoDoc,
					String numDoc,
					String rznSocial,
					Address address,
					String email,
					String telephone) {
		
		this.tipoDoc = tipoDoc.getLabel();
		this.numDoc = numDoc;
		this.rznSocial = rznSocial;
		this.address = address;
		this.email = email;
		this.telephone = telephone;
	}
	
}
