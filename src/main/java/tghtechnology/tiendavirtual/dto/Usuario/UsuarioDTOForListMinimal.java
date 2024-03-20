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
public class UsuarioDTOForListMinimal implements DTOForList<Usuario>{

	private Integer id_usuario;
	
    private String username;
    private TipoUsuario cargo;
    private boolean autenticado;

	@Override
	public UsuarioDTOForListMinimal from(Usuario user) {
		this.id_usuario = user.getId_usuario();
        this.username = user.getUsername();
        this.cargo = user.getCargo();
        this.autenticado = user.isAutenticado();
        return this;
	}
    
}
