package tghtechnology.tiendavirtual.dto.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Imagen;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;
import tghtechnology.tiendavirtual.dto.Descuento.DescuentoDTOForListMinimal;
import tghtechnology.tiendavirtual.dto.Marca.MarcaDTOForListMinimal;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTOForListFull implements DTOForList<Item>{

	private Integer id_item;
	private String url;
	private String nombre;
    private String descripcion;
    private DisponibilidadItem disponibilidad;
    private LocalDateTime fecha_creacion;
    private LocalDateTime fecha_modificacion;
    private BigDecimal estrellas;
    private Integer valoraciones;
    
	private BigDecimal precio;
	private BigDecimal costo;
	private Integer stock;
    
    private final SortedMap<Integer, String> imagenes = new TreeMap<>();
    
    private CategoriaDTOForList categoria;
    private MarcaDTOForListMinimal marca;
    private DescuentoDTOForListMinimal descuento;
    
    
	@Override
	public ItemDTOForListFull from(Item item) {
		this.id_item = item.getId_item();
		this.url = item.getText_id();
		this.nombre = item.getNombre();
		this.disponibilidad = item.getDisponibilidad();
		this.descripcion = item.getDescripcion();
		this.fecha_creacion = item.getFecha_creacion();
		this.fecha_modificacion = item.getFecha_modificacion();
		
		this.precio = item.getPrecio();
		this.costo = item.getCosto();
		this.stock = item.getStock();
		
		this.descuento = item.getDescuento() == null ? null : new DescuentoDTOForListMinimal().from(item.getDescuento());
		this.categoria = new CategoriaDTOForList().from(item.getCategoria());
		this.marca = new MarcaDTOForListMinimal().from(item.getMarca());
		this.valoraciones = item.getValoraciones();
		this.estrellas = BigDecimal.valueOf(item.getEstrellas()).setScale(2, RoundingMode.HALF_UP);
		return this;
	}
	
	public ItemDTOForListFull from(Item item, List<Imagen> imgs){
		from(item);
		
		imgs.forEach(img -> {
			imagenes.put(img.get_index(), img.getImagen()); 
		});
		
		return this;
	}
	
	public ItemDTOForListFull from(Item item, List<Imagen> imgs, Boolean extendedPermission){
		from(item, imgs);
		if(!extendedPermission) {
			this.fecha_creacion = null;
			this.fecha_modificacion = null;
			this.costo = null;
		}
		return this;
	}
    
    
   
}
