package tghtechnology.tiendavirtual.dto.Item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class PlatoDTOForList {

	private Integer id_plato;
	private String url;
	private String nombre_plato;
    private String descripcion;
    private String imagen_primaria;
    private String imagen_secundaria;
    private BigDecimal precio;
    private String disponibilidad;
    private LocalDateTime fecha_creacion;
    private LocalDateTime fecha_modificacion;
    
    private String tipoPlato;
    private CategoriaDTOForList categoria;
    
    
    /**Reestructuración para listar plato*/ 
    public PlatoDTOForList(Item plato){
        this.id_plato = plato.getId_plato();
        this.url = plato.getText_id();
        this.nombre_plato = plato.getNombre_plato();       
        this.descripcion = plato.getDescripcion();
        this.imagen_primaria = plato.getImagen_primaria();
        this.imagen_secundaria = plato.getImagen_secundaria();        
        this.precio = plato.getPrecio();
        this.disponibilidad = plato.getDisponibilidad();
        this.fecha_creacion = plato.getFecha_creacion();
        this.fecha_modificacion = plato.getFecha_modificacion();
        this.tipoPlato = plato.getTipoPlato().getLabel();
        this.categoria = new CategoriaDTOForList(plato.getCategoria());
    }
}
