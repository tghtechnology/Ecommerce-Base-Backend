package tghtechnology.tiendavirtual.dto.Usuario;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForInsert implements DTOForInsert<Usuario>{
    
    @NotNull(message = "El usuario no debe estar vacío")
    @NotBlank(message = "El campo no debe estar vacío")
    @Length(min = 6, message = "El usuario debe ser mayor a 6 caracteres")
    private String email;

    @NotNull(message = "El campo no debe estar vacío")
    private PasswordDTO password;

    @NotNull(message = "El campo no debe estar vacío")
    private TipoUsuario cargo;

	@Override
	public Usuario toModel() {
		Usuario user = new Usuario();
		user.setEmail(email);
		//password not included
		user.setCargo(cargo);
		return user;
	}

	@Override
	public Usuario updateModel(Usuario user) {
		user.setEmail(email);
		//password not included
		user.setCargo(cargo);
		return user;
	}
}
