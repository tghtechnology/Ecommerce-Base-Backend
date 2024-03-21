package tghtechnology.tiendavirtual.dto.Usuario;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;
import tghtechnology.tiendavirtual.dto.Persona.PersonaDTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForInsert implements DTOForInsert<Usuario>{
    
    @NotNull(message = "El usuario no debe ser nulo")
    @NotBlank(message = "El campo no debe estar vacío")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El correo es invalido")
    @Size(min = 10, max = 80, message = "El correo debe tener entre 10 y 80 caracteres")
    private String email;

    @NotNull(message = "El usuario no debe estar vacío")
    @NotBlank(message = "El campo no debe estar vacío")
    @Length(min = 8, max = 24, message = "La contraseña debe tener entre 8 y 24 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,24}$", message = "Contraseña invalida")
	private String password;

    @NotNull(message = "El campo no debe estar vacío")
    private TipoUsuario cargo;
    
    @Valid
    private PersonaDTOForInsert persona; //TODO quitar
    private Integer id_persona;

	@Override
	public Usuario toModel() {
		
		LocalDateTime now = LocalDateTime.now();
		
		Usuario user = new Usuario();
		user.setUsername(email);
		//password not included
		user.setCargo(cargo);
		user.setAutenticado(false);
		
		user.setFecha_creacion(now);
		user.setFecha_modificacion(now);
		user.setEstado(true);
		
		return user;
	}

	@Override
	public Usuario updateModel(Usuario user) {
		user.setUsername(email);
		//password not included
		user.setCargo(cargo);
		
		user.setFecha_modificacion(LocalDateTime.now());
		
		return user;
	}
}
