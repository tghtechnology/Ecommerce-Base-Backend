package tghtechnology.tiendavirtual.dto.Usuario;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.TipoUsuario;
import tghtechnology.tiendavirtual.Models.Usuario;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForList {

    private Integer id_usuario;
    private String username;
    private TipoUsuario cargo;
    
	private LocalDateTime fecha_creacion;

    public UsuarioDTOForList (Usuario user){
        this.setId_usuario(user.getId_usuario());
        this.setUsername(user.getUsername());
        this.setCargo(user.getCargo());
        this.setFecha_creacion(user.getFechaCreacion());

    }
    

}
