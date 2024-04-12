package tghtechnology.tiendavirtual.Enums;

import lombok.Getter;

public enum DisponibilidadItem {
	
	DISPONIBLE("Disponible"),
	NO_DISPONIBLE("No Disponible"),
	SIN_STOCK("Sin Stock");
	
	@Getter
	private String descripcion;
	
	private DisponibilidadItem(String descripcion) {
		this.descripcion = descripcion;
	}
}