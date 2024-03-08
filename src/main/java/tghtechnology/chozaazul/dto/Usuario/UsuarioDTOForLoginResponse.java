package tghtechnology.chozaazul.dto.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Enums.TipoCargo;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForLoginResponse {
    
	private int id;
    private String username;
    private TipoCargo cargo;
    private String token;
    
}
