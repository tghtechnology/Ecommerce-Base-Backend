package tghtechnology.chozaazul.dto.Empleado;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Empleado;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class EmpleadoDTOForList {
    private Integer id_empleado;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private LocalDateTime fecha_creacion;
    private UsuarioDTOForList user;

    public EmpleadoDTOForList ( Empleado emp){
        this.id_empleado = emp.getId_empleado();
        this.nombres = emp.getNombres();
        this.apellidos = emp.getApellidos();
        this.correo = emp.getCorreo();
        this.telefono = emp.getTelefono();
        this.fecha_creacion = emp.getFecha_creacion();
        this.user = new UsuarioDTOForList(emp.getUsuario());
    }
}
