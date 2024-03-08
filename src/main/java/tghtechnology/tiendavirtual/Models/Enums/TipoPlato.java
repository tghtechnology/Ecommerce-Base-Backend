package tghtechnology.tiendavirtual.Models.Enums;

import lombok.Getter;

public enum TipoPlato {

	NINGUNO("Sin Tipo"),
	PLATO_COMUN("Platos"),
	MENU_DEL_DIA("Menú del día"),
	PIQUEOS_Y_BEBIDAS("Piqueos y Bebidas"),
	MENU_CUMPLE("Menú de Cumpleaños")
	;
	
	
	@Getter
	private String label;
	
	TipoPlato(String label) {
		this.label = label;
	}

}
