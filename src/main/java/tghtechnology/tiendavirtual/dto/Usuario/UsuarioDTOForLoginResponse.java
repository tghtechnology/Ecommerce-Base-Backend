package tghtechnology.tiendavirtual.dto.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoCargo;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForLoginResponse {
    
	private int id;
    private String username;
    private TipoCargo cargo;
    private String token;
    
}
