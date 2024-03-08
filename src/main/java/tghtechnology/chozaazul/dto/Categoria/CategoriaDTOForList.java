package tghtechnology.chozaazul.dto.Categoria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Categoria;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaDTOForList {

	private Integer id_categoria;
    private String descripcion;
    //private LocalDateTime fecha_creacion;
    
    /*Reestructuración para listar cargo*/ 
    public CategoriaDTOForList(Categoria categoria){
        this.setId_categoria(categoria.getId_categoria());
        this.setDescripcion(categoria.getDescripcion());
        //this.setFecha_creacion(categoria.getFecha_creacion());
    }
}
