package tghtechnology.tiendavirtual.dto.Usuario;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForModify implements DTOForInsert<Usuario>{
    
	@Size(min = 10, max = 80, message = "El correo debe tener entre 10 y 80 caracteres")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El correo es invalido")
    private String email;

    @Size(min = 8, max = 24, message = "La contraseña debe tener entre 8 y 24 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,24}$", message = "Contraseña invalida")
	private String new_password;
    
    private String old_password;

	@Override
	public Usuario toModel() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Usuario updateModel(Usuario user) {
		if(email != null) user.setUsername(email);
		
		user.setFecha_modificacion(LocalDateTime.now());
		
		return user;
	}
}
