package tghtechnology.tiendavirtual.dto.Item;

import java.util.ArrayList;
import java.util.List;

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
import tghtechnology.tiendavirtual.dto.VariacionItem.VariacionDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTOForList implements DTOForList<Item>{

	private Integer id_item;
	private String url;
	private String nombre;
    private DisponibilidadItem disponibilidad;
    
    private String thumbnail;
    private DescuentoDTOForListMinimal descuento;
    private CategoriaDTOForList categoria;
    private MarcaDTOForListMinimal marca;
    
    private List<VariacionDTOForList> variaciones = new ArrayList<>();
    
	@Override
	public ItemDTOForList from(Item item) {
		this.id_item = item.getId_item();
		this.url = item.getText_id();
		this.nombre = item.getNombre();
		this.disponibilidad = item.getDisponibilidad();
		this.categoria = new CategoriaDTOForList().from(item.getCategoria());
		this.marca = new MarcaDTOForListMinimal().from(item.getMarca());
		this.descuento = item.getDescuento() == null ? null : new DescuentoDTOForListMinimal().from(item.getDescuento());
		
		item.getVariaciones().forEach(var -> variaciones.add(new VariacionDTOForList().from(var)));
		
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
