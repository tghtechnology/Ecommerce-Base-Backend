package tghtechnology.tiendavirtual.dto.Usuario;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Models.Enums.TipoCargo;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTOForList {

    private Integer id_usuario;
    private String username;
    private TipoCargo cargo;
    
	private LocalDateTime fecha_creacion;

    public UsuarioDTOForList (Usuario user){
        this.setId_usuario(user.getId_usuario());
        this.setUsername(user.getUsername());
        this.setCargo(user.getCargo());
        this.setFecha_creacion(user.getFechaCreacion());

    }
    

}
