package tghtechnology.tiendavirtual.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;
import tghtechnology.tiendavirtual.dto.Descuento.DescuentoDTOForListMinimal;
import tghtechnology.tiendavirtual.dto.Marca.MarcaDTOForListMinimal;
import tghtechnology.tiendavirtual.dto.VariacionItem.VariacionDTOForListMinimal;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTOForList implements DTOForList<Item>{

	private Integer id_item;
	private String url;
	private String nombre;
    private DisponibilidadItem disponibilidad;
    
    private BigDecimal precio;
    private BigDecimal costo;
    
    private String thumbnail;
    private DescuentoDTOForListMinimal descuento;
    private CategoriaDTOForList categoria;
    private MarcaDTOForListMinimal marca;
    
    private List<VariacionDTOForListMinimal> variaciones = new ArrayList<>();
    
	@Override
	public ItemDTOForList from(Item item) {
		return from(item, false);
	}
	
	public ItemDTOForList from(Item item, Boolean extendedPermission) {
		this.id_item = item.getId_item();
		this.url = item.getText_id();
		this.nombre = item.getNombre();
		this.thumbnail = item.getImagen().getMiniatura();
		this.disponibilidad = item.getDisponibilidad();
		this.categoria = new CategoriaDTOForList().from(item.getCategoria());
		this.marca = new MarcaDTOForListMinimal().from(item.getMarca());
		this.descuento = item.getDescuento() == null ? null : new DescuentoDTOForListMinimal().from(item.getDescuento());
		
		this.precio = item.getPrecio();
		if(extendedPermission)
			this.setCosto(item.getCosto());
		
		item.getVariaciones()
			.stream()
			.filter(v -> v.getEstado())
			.sorted()
			.forEach(var -> {
				variaciones.add(new VariacionDTOForListMinimal().from(var));
			});
		
		return this;
	}    
   
}
