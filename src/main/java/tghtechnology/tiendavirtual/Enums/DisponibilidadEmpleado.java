package tghtechnology.tiendavirtual.Enums;

import lombok.Getter;

public enum DisponibilidadEmpleado {
	
	DISPONIBLE("Disponible"),
	NO_DISPONIBLE("No Disponible"),
	VACACIONES("En vacaciones"),
	DESCANSO_MEDICO("Descanso medico"),
	SUSPENDIDO("Suspendido");
	
	@Getter
	private String descripcion;
	
	private DisponibilidadEmpleado(String descripcion) {
		this.descripcion = descripcion;
	}
}