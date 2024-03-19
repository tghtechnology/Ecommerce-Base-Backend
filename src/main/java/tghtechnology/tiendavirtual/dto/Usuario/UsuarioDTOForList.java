package tghtechnology.tiendavirtual.dto.Usuario;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Persona.PersonaDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForList implements DTOForList<Usuario>{

    private PersonaDTOForList persona;
	
    private String username;
    private TipoUsuario cargo;
    private boolean autenticado;
    
	private LocalDateTime fecha_creacion;
	private LocalDateTime fecha_modificacion;

	@Override
	public UsuarioDTOForList from(Usuario user) {
		this.setPersona(new PersonaDTOForList().from(user.getPersona()));
        this.setUsername(user.getUsername());
        this.setCargo(user.getCargo());
        this.setAutenticado(user.isAutenticado());
        this.setFecha_creacion(user.getFecha_creacion());
        this.setFecha_modificacion(user.getFecha_modificacion());
        return this;
	}
    
}
