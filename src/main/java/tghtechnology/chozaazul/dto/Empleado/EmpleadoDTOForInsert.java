package tghtechnology.chozaazul.dto.Empleado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class EmpleadoDTOForInsert {
    
    @NotNull(message = "El campo no debe estar vacío")
    @NotBlank(message = "El campo no debe estar vacío")
    @Size(min = 3, message = "el nombre debe ser mayor a 3 caracteres")
    private String nombres;

    @NotNull(message = "El campo no debe estar vacío")
    @NotBlank(message = "El campo no debe estar vacío")
    private String apellidos;

	@NotEmpty(message = "El campo no puede estar vacío")
	@Pattern(regexp = "[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z0-9\\.]+", message = "El correo es invalido")
    @Size(min = 10, max = 80, message = "El correo debe tener entre 10 y 80 caracteres")
    private String correo;

	@NotEmpty(message = "El campo no puede estar vacío")
	@Pattern(regexp = "^[0-9+]*$", message = "El teléfono no puede contener letras o simbolos")
    @Size(min = 7, max = 12, message = "El teléfono debe tener entre 7 y 12 caracteres")
    private String telefono;

	@NotNull(message = "No puede ser nulo")
    private UsuarioDTOForInsert usuario;

}


