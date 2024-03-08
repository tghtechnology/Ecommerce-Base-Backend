package tghtechnology.chozaazul.Utils.ApisPeru.Enums;

import lombok.Getter;

/**
 * Tipos de documento de identidad con el que se pueden identificar los clientes
 */
public enum TipoDocIdentidad {

	/**
	 * Documento sin RUC para exportación
	 */
	DOC_TRIB_NO_DOM_SIN_RUC("0"),
	/**
	 * Documento Nacional de Identidad
	 */
	DNI("1"),
	/**
	 * Carnet de Extranjería
	 */
	CARNET_EXTRANJERIA("4"),
	/**
	 * Registro único de Contribuyentes
	 */
	RUC("6"),
	PASAPORTE("7"),
	CEDULA_DIPLOMATICA_DE_IDENTIDAD("A"),
	DOC_IDENTIDAD_PAIS_RESIDENCIA_NO_D("B"),
	TIN("C"),
	IN("D")
	;
	
	
	@Getter
	private String label;
	
	TipoDocIdentidad(String label) {
		this.label = label;
	}
}
