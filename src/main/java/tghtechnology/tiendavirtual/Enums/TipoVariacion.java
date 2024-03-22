package tghtechnology.tiendavirtual.Enums;

import lombok.Getter;

public enum TipoVariacion {
	
	COLOR("Color"),
	TALLA("Talla");
	
	@Getter
	private String descripcion;
	
	private TipoVariacion(String descripcion) {
		this.descripcion = descripcion;
	}
}