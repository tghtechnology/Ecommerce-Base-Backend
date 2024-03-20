package tghtechnology.tiendavirtual.dto.Empleado;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadEmpleado;
import tghtechnology.tiendavirtual.Models.Empleado;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Persona.PersonaDTOForList;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForListMinimal;

@Getter
@Setter
@NoArgsConstructor
public class EmpleadoDTOForList implements DTOForList<Empleado>{

	private PersonaDTOForList persona;
	
	private DisponibilidadEmpleado disponibilidad;
	
	private UsuarioDTOForListMinimal usuario;
	
    private LocalDateTime fecha_creacion;
    private LocalDateTime fecha_modificacion;

	@Override
	public EmpleadoDTOForList from(Empleado emp) {
		this.persona = new PersonaDTOForList().from(emp.getPersona());
		this.disponibilidad = emp.getDisponibilidad();
        this.fecha_creacion = emp.getFecha_creacion();
        this.fecha_modificacion = emp.getFecha_modificacion();
        this.usuario = new UsuarioDTOForListMinimal().from(emp.getUsuario());
        return this;
	}
}
