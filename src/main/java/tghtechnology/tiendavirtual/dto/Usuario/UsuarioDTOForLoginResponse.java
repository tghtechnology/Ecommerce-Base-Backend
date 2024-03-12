package tghtechnology.tiendavirtual.dto.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForLoginResponse {
    
	private int id;
    private String username;
    private TipoUsuario cargo;
    private String token;
    
}
