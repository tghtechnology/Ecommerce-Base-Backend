package tghtechnology.tiendavirtual.dto.Usuario;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForPassChange {
    
	@NotNull(message = "El usuario no debe estar vacío")
    @NotBlank(message = "El campo no debe estar vacío")
    @Length(min = 8, message = "El usuario debe tener mas de 8 caracteres")
	private String password;
    
}
