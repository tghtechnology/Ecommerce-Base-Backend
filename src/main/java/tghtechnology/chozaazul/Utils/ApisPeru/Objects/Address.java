package tghtechnology.chozaazul.Utils.ApisPeru.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * Objeto que representa una dirección para ApisPeru.
 *
 */
public class Address {

	private String ubigueo;
	//private String codigoPais;
	private String departamento;
	private String provincia;
	private String distrito;
	private String urbanizacion;	//opcional
	private String direccion;
	private String codLocal;		//opcional
	

	/**
	 * Constructor para una dirección
	 * @param ubigueo Código de Ubigeo
	 * @param departamento Departamento / Región del Perú donde se encuentra
	 * @param provincia Provincia de la dirección
	 * @param distrito Distrito de la dirección
	 * @param urbanizacion Urbanización de la dirección (OPCIONAL)
	 * @param direccion Dirección en texto de la localización exacta.
	 * @param codLocal Código de local (OPCIONAL)
	 */
	public Address(String ubigueo,
					String departamento,
					String provincia,
					String distrito,
					String urbanizacion,
					String direccion,
					String codLocal) {
		this.ubigueo = ubigueo;
		this.departamento = departamento;
		this.provincia = provincia;
		this.distrito = distrito;
		this.urbanizacion = urbanizacion;
		this.direccion = direccion;
		this.codLocal = codLocal;
	}

	/**
	 * Constructor simplificado para una dirección
	 * @param ubigueo Código de Ubigeo
	 * @param departamento Departamento / Región del Perú donde se encuentra
	 * @param provincia Provincia de la dirección
	 * @param distrito Distrito de la dirección
	 * @param direccion Dirección en texto de la localización exacta.
	 */
	public Address(String ubigueo,
					String departamento,
					String provincia,
					String distrito,
					String direccion) {
		this.ubigueo = ubigueo;
		this.departamento = departamento;
		this.provincia = provincia;
		this.distrito = distrito;
		this.direccion = direccion;
		this.urbanizacion = null;
		this.codLocal = null;
	}
	
	
	
}
