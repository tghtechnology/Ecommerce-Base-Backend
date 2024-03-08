package tghtechnology.chozaazul.Utils.ApisPeru;

import org.springframework.beans.factory.annotation.Value;

import tghtechnology.chozaazul.Utils.ApisPeru.Objects.Address;
import tghtechnology.chozaazul.Utils.ApisPeru.Objects.Company;

public class CompanyData {

	@Value("${apisperu.company.ruc}")
	private String ruc;
	@Value("${apisperu.company.razon-social}")
	private String razonSocial;
	@Value("${apisperu.company.nombre-comercial}")
	private String nombreComercial;
	@Value("${apisperu.company.email}")
	private String email;
	@Value("${apisperu.company.telephone}")
	private String telephone;
	
	@Value("${apisperu.company.address.ubigeo}")
	private String ubigueo;
	@Value("${apisperu.company.address.departamento}")
	private String departamento;
	@Value("${apisperu.company.address.provincia}")
	private String provincia;
	@Value("${apisperu.company.address.distrito}")
	private String distrito;
	@Value("${apisperu.company.address.direccion}")
	private String direccion;
	//@Value("${apisperu.company.address.cod-local}")
	//private String codLocal;
	
	public Company getCompanyData() {
		return new Company(
					ruc,
					razonSocial,
					nombreComercial,
					new Address(
							ubigueo,
							departamento,
							provincia,
							distrito,
							direccion
							),
					email,
					telephone
				);
	}
	
}
