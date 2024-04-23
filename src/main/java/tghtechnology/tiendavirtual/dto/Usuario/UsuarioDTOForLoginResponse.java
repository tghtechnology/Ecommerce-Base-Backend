package tghtechnology.tiendavirtual.dto.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private Boolean verificado;
    private Map<String, String> payload = new HashMap<>();
    
	@Override
	public UsuarioDTOForLoginResponse from(Usuario user) {
		this.id = user.getPersona().getId_persona();
		this.email = user.getUsername();
		this.cargo = user.getCargo();
		this.verificado = user.isAutenticado();
		return this;
	}
	
	public UsuarioDTOForLoginResponse from(Usuario user, String token, UUID uid) {
		from(user);
		this.payload.put("token", token);
		if(uid != null) this.payload.put("socket_uid", uid.toString().replace('-', '_'));
		return this;
	}
    
}
