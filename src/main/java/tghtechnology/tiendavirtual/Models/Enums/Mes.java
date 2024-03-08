package tghtechnology.tiendavirtual.Models.Enums;

public enum Mes {
	
	ENERO,
	FEBRERO,
	MARZO,
	ABRIL,
	MAYO,
	JUNIO,
	JULIO,
	AGOSTO,
	SETIEMBRE,
	OCTUBRE,
	NOVIEMBRE,
	DICIEMBRE
	;
	
	public String nombre() {
		String n = this.name();
		return n.substring(0,1) + n.substring(1).toLowerCase();
	}

	
}
