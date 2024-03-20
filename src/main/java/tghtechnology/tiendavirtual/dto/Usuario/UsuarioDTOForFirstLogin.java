package tghtechnology.tiendavirtual.dto.Usuario;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;
import tghtechnology.tiendavirtual.dto.Persona.PersonaDTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForFirstLogin implements DTOForInsert<Usuario>{
    
	@NotNull(message = "El usuario no debe ser nulo")
    @NotBlank(message = "El campo no debe estar vacío")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El correo es invalido")
    @Size(min = 10, max = 80, message = "El correo debe tener entre 10 y 80 caracteres")
    private String email;

    @NotNull(message = "El campo no debe estar vacío")
    private PasswordDTO password;

	@Override
	public Usuario toModel() {
		LocalDateTime now = LocalDateTime.now();
		
		Usuario user = new Usuario();
		user.setUsername(email);
		//password not included
		user.setCargo(TipoUsuario.ADMIN);
		user.setAutenticado(true);
		
		user.setFecha_creacion(now);
		user.setFecha_modificacion(now);
		user.setEstado(true);
		
		PersonaDTOForInsert persona = new PersonaDTOForInsert();
		persona.setNombres("Admin");
		persona.setApellidos("Admin");
		persona.setCorreo_personal(email);
		persona.setNumero_documento("00000000");
		persona.setTelefono("999999999");
		persona.setTipo_documento(TipoDocIdentidad.DNI);
		
		user.setPersona(persona.toModel());
		
		return user;
	}

	@Override
	public Usuario updateModel(Usuario modelToUpdate) {
		throw new UnsupportedOperationException();
	}
    
}
