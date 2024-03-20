package tghtechnology.tiendavirtual.Enums;

import lombok.Getter;

public enum DisponibilidadItem {
	
	DISPONIBLE("Disponible"),
	NO_DISPONIBLE("No Disponible");
	
	@Getter
	private String descripcion;
	
	private DisponibilidadItem(String descripcion) {
		this.descripcion = descripcion;
	}
}