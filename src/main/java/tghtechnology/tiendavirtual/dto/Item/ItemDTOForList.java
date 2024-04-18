package tghtechnology.tiendavirtual.dto.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

@Getter
@Setter
@NoArgsConstructor
public class ItemDTOForList implements DTOForList<Item>{

	private Integer id_item;
	private String url;
	private String nombre;
    private DisponibilidadItem disponibilidad;
    
    private String thumbnail;
	private BigDecimal precio;
	private BigDecimal costo;
	private Integer stock;
    private DescuentoDTOForListMinimal descuento;
    private BigDecimal estrellas;
    private Integer valoraciones;
    private CategoriaDTOForList categoria;
    private MarcaDTOForListMinimal marca;
    
	@Override
	public ItemDTOForList from(Item item) {
		return from(item, false);
	}
	
	public ItemDTOForList from(Item item, Boolean extendedPermission) {
		this.id_item = item.getId_item();
		this.url = item.getText_id();
		this.nombre = item.getNombre();
		this.disponibilidad = item.getDisponibilidad();
		this.categoria = new CategoriaDTOForList().from(item.getCategoria());
		this.marca = new MarcaDTOForListMinimal().from(item.getMarca());
		this.precio = item.getPrecio();
		this.stock = item.getStock();
		if(extendedPermission) {
			this.costo = item.getCosto();
		}
		this.valoraciones = item.getValoraciones();
		this.estrellas = BigDecimal.valueOf(item.getEstrellas()).setScale(2, RoundingMode.HALF_UP);
		this.descuento = item.getDescuento() == null ? null : new DescuentoDTOForListMinimal().from(item.getDescuento());
		
		return this;
	}
	
	public ItemDTOForList from(Item item, List<Imagen> imagenes,Boolean extendedPermission){
		from(item, extendedPermission);
		
		imagenes.forEach(img -> {
			if(img.get_index() == 1) {
				this.thumbnail = img.getMiniatura();
			}
		});
		
		return this;
	}
    
    
   
}
