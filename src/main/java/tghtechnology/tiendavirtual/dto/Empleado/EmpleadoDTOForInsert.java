package tghtechnology.tiendavirtual.dto.Empleado;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadEmpleado;
import tghtechnology.tiendavirtual.Models.Empleado;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;
import tghtechnology.tiendavirtual.dto.Persona.PersonaDTOForInsert;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class EmpleadoDTOForInsert implements DTOForInsert<Empleado>{
    
    @Valid
    private PersonaDTOForInsert persona;
    private Integer id_persona;
    
    @Valid
    @NotNull(message = "El usuario no puede ser nulo")
    private UsuarioDTOForInsert usuario;
    
    private DisponibilidadEmpleado disponibilidad;

	@Override
	public Empleado toModel() {
		Empleado emp = new Empleado();
		
		emp.setDisponibilidad(DisponibilidadEmpleado.DISPONIBLE);
		
		LocalDateTime now = LocalDateTime.now();
        emp.setFecha_creacion(now);
        emp.setFecha_modificacion(now);
        emp.setEstado(true);
        return emp;
	}

	@Override
	public Empleado updateModel(Empleado emp) {
		emp.setPersona(persona.updateModel(emp.getPersona()));
		if(disponibilidad != null) emp.setDisponibilidad(disponibilidad);
		
		LocalDateTime now = LocalDateTime.now();
        emp.setFecha_modificacion(now);
        return emp;
	}

}


