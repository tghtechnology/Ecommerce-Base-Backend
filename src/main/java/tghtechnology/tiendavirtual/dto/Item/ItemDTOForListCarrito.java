package tghtechnology.tiendavirtual.dto.Item;

import java.math.BigDecimal;

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
public class ItemDTOForListCarrito implements DTOForList<Item>{

	private Integer id_item;
	private String url;
	private String nombre;
    private DisponibilidadItem disponibilidad;
    
    private String thumbnail;
	private BigDecimal precio;
	private Integer stock;
    private BigDecimal descuento;
    private CategoriaDTOForList categoria;
    private MarcaDTOForListMinimal marca;
    
	@Override
	public ItemDTOForListCarrito from(Item item) {
		return from(item, null);
	}

	public ItemDTOForListCarrito from(Item item, Imagen img) {
		this.id_item = item.getId_item();
		this.url = item.getText_id();
		this.nombre = item.getNombre();
		this.disponibilidad = item.getDisponibilidad();
		this.thumbnail = img == null ? null : img.getMiniatura();
		this.categoria = new CategoriaDTOForList().from(item.getCategoria());
		this.marca = new MarcaDTOForListMinimal().from(item.getMarca());
		this.precio = item.getPrecio();
		this.stock = item.getStock();
		this.descuento = (item.getDescuento() != null)
						? precio.multiply(new BigDecimal(item.getDescuento().getPorcentaje()).divide(new BigDecimal(100)))
						: BigDecimal.ZERO;
		
		return this;
	}
	
}
