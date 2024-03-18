package tghtechnology.tiendavirtual.dto.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForLoginResponse implements DTOForList<Usuario>{
    
	private int id;
    private String email;
    private TipoUsuario cargo;
    private String token;
    
	@Override
	public UsuarioDTOForLoginResponse from(Usuario user) {
		this.id = user.getId_usuario();
		this.email = user.getEmail();
		this.cargo = user.getCargo();
		return this;
	}
	
	public UsuarioDTOForLoginResponse from(Usuario user, String token) {
		from(user);
		this.token = token;
		return this;
	}
    
}
