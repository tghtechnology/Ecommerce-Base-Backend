package tghtechnology.tiendavirtual.dto.Usuario;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Enums.TipoCargo;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForInsert {
    
    @NotNull(message = "El usuario no debe estar vacío")
    @NotBlank(message = "El campo no debe estar vacío")
    @Length(min = 6, message = "El usuario debe ser mayor a 6 caracteres")
    private String username;

    @NotNull(message = "El campo no debe estar vacío")
    private PasswordDTO password;

    @NotNull(message = "El campo no debe estar vacío")
    private TipoCargo cargo;
}
