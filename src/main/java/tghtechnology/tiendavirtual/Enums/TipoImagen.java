package tghtechnology.tiendavirtual.Enums;

import lombok.Getter;

public enum TipoImagen {
	
	ANUNCIO("Anuncio"),
	MARCA("Marca"),
	PRODUCTO("Producto"),
	VARIACION("Variacion"),
	RED_SOCIAL("RedSocial");
	
	@Getter
	private String tabla;
	
	private TipoImagen(String tabla) {
		this.tabla = tabla;
	}
}