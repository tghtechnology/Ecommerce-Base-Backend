package tghtechnology.chozaazul.dto.Plato;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Plato;
import tghtechnology.chozaazul.dto.Categoria.CategoriaDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class PlatoDTOMinimal {

	private Integer id_plato;
	private String url;
	private String nombre_plato;
    private String imagen_primaria;
    private String imagen_secundaria;
    private BigDecimal precio;
    
    private String tipoPlato;
    private CategoriaDTOForList categoria;
    
    
    /**Reestructuración para listar plato*/ 
    public PlatoDTOMinimal(Plato plato){
        this.id_plato = plato.getId_plato();
        this.url = plato.getText_id();
        this.nombre_plato = plato.getNombre_plato();       
        this.imagen_primaria = plato.getImagen_primaria();
        this.imagen_secundaria = plato.getImagen_secundaria();        
        this.precio = plato.getPrecio();
        this.tipoPlato = plato.getTipoPlato().getLabel();
        this.categoria = new CategoriaDTOForList(plato.getCategoria());
    }
}
