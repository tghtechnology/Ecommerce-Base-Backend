package tghtechnology.tiendavirtual.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForPassRequest {
    
	@NotNull(message = "El usuario no debe ser nulo")
    @NotBlank(message = "El campo no debe estar vacío")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El correo es invalido")
    @Size(min = 10, max = 80, message = "El correo debe tener entre 10 y 80 caracteres")
    private String email;
    
}
