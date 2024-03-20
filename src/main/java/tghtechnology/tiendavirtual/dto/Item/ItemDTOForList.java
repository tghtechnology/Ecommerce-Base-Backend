package tghtechnology.tiendavirtual.dto.Item;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Imagen;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;
import tghtechnology.tiendavirtual.dto.Marca.MarcaDTOForListMinimal;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTOForList implements DTOForList<Item>{

	private Integer id_item;
	private String url;
	private String nombre;
    private BigDecimal precio;
    private DisponibilidadItem disponibilidad;
    private Integer stock;
    
    private String thumbnail;
    private CategoriaDTOForList categoria;
    private MarcaDTOForListMinimal marca;
    
	@Override
	public ItemDTOForList from(Item item) {
		this.id_item = item.getId_item();
		this.url = item.getText_id();
		this.nombre = item.getNombre();
		this.precio = item.getPrecio();
		this.disponibilidad = item.getDisponibilidad();
		this.stock = item.getStock();
		this.categoria = new CategoriaDTOForList().from(item.getCategoria());
		this.marca = new MarcaDTOForListMinimal().from(item.getMarca());
		return this;
	}
	
	public ItemDTOForList from(Item item, List<Imagen> imagenes){
		from(item);
		
		imagenes.forEach(img -> {
			if(img.get_index() == 1) {
				this.thumbnail = img.getPublic_id_Miniatura();
			}
		});
		
		return this;
	}
    
    
   
}
