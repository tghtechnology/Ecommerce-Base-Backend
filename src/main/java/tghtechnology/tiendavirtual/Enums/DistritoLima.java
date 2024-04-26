package tghtechnology.tiendavirtual.Enums;

import lombok.Getter;

public enum DistritoLima {

	CALLAO("Callao", "070701"),
	BELLAVISTA("Bellavista", "070702"),
	CARMEN_DE_LA_LEGUA("Carmen de La Legua", "070703"),
	LA_PERLA("La Perla", "070704"),
	LA_PUNTA("La Punta", "070705"),
	MI_PERU("Mi Perú", "070706"),
	VENTANILLA("Ventanilla", "070707"),
	LIMA("Lima", "150101"),
	ANCON("Ancón", "150102"),
	ATE("Ate", "150103"),
	BARRANCO("Barranco", "150104"),
	BRENA("Breña", "150105"),
	CARABAYLLO("Carabayllo", "150106"),
	CIENEGUILLA("Cieneguilla", "150107"),
	CHACLACAYO("Chaclacayo", "150108"),
	CHORRILLOS("Chorrillos", "150109"),
	COMAS("Comas", "150110"),
	EL_AGUSTINO("El Agustino", "150111"),
	INDEPENDENCIA("Independencia", "150112"),
	JESUS_MARIA("Jesús María", "150113"),
	LA_MOLINA("La Molina", "150114"),
	LA_VICTORIA("La Victoria", "150115"),
	LINCE("Lince", "150116"),
	LOS_OLIVOS("Los Olivos", "150117"),
	LURIGANCHO("Lurigancho", "150118"),
	LURIN("Lurín", "150119"),
	MAGDALENA_DEL_MAR("Magdalena del Mar", "150120"),
	MIRAFLORES("Miraflores", "150121"),
	PACHACAMAC("Pachacámac", "150122"),
	PUCUSANA("Pucusana", "150123"),
	PUEBLO_LIBRE("Pueblo Libre", "150124"),
	PUENTE_PIEDRA("Puente Piedra", "150125"),
	PUNTA_HERMOSA("Punta Hermosa", "150126"),
	PUNTA_NEGRA("Punta Negra", "150127"),
	RIMAC("Rímac", "150128"),
	SAN_BARTOLO("San Bartolo", "150129"),
	SAN_BORJA("San Borja", "150130"),
	SAN_ISIDRO("San Isidro", "150131"),
	SAN_JUAN_DE_LURIGANCHO("San Juan de Lurigancho", "150132"),
	SAN_JUAN_DE_MIRAFLORES("San Juan de Mirflores", "150133"),
	SAN_LUIS("San Luis", "150134"),
	SAN_MARTIN_DE_PORRES("San Martín de Porres", "150135"),
	SAN_MIGUEL("San Miguel", "150136"),
	SANTA_ANITA("Santa Anita", "150137"),
	SANTA_MARIA_DEL_MAR("Santa María del Mar", "150138"),
	SANTA_ROSA("Santa Rosa", "150139"),
	SANTIAGO_DE_SURCO("Santiago de Surco", "150140"),
	SURQUILLO("Surquillo", "150141"),
	VILLA_EL_SALVADOR("Villa El Salvador", "150142"),
	VILLA_MARIA_DEL_TRIUNFO("Villa María del Triunfo", "150143");
	
	@Getter final String nombre;
	@Getter final String ubigeo;
	
	private DistritoLima(String nombre, String ubigeo) {
		this.nombre = nombre;
		this.ubigeo = ubigeo;
	}
	
}
