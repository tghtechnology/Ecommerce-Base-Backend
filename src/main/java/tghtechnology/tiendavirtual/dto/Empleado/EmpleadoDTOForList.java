package tghtechnology.tiendavirtual.dto.Empleado;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Empleado;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Usuario.UsuarioDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class EmpleadoDTOForList implements DTOForList<Empleado>{
    private Integer id_empleado;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private LocalDateTime fecha_creacion;
    private UsuarioDTOForList user;

	@Override
	public EmpleadoDTOForList from(Empleado emp) {
		this.id_empleado = emp.getId_empleado();
        this.nombres = emp.getNombres();
        this.apellidos = emp.getApellidos();
        this.correo = emp.getCorreo_personal();
        this.telefono = emp.getTelefono();
        this.fecha_creacion = emp.getFecha_creacion();
        this.user = new UsuarioDTOForList(emp.getUsuario());
        return this;
	}
}
