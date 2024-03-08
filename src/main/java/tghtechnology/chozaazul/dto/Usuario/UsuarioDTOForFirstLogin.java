package tghtechnology.chozaazul.dto.Usuario;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForFirstLogin {
    
    @NotNull(message = "El usuario no debe estar vacío")
    @NotBlank(message = "El campo no debe estar vacío")
    @Length(min = 6, message = "El usuario debe ser mayor a 6 caracteres")
    private String username;

    @NotNull(message = "El campo no debe estar vacío")
    private PasswordDTO password;
    
}
