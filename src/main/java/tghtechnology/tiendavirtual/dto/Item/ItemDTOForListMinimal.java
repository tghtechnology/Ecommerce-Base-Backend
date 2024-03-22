package tghtechnology.tiendavirtual.dto.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTOForListMinimal implements DTOForList<Item>{

	private Integer id_item;
	private String nombre;
    private DisponibilidadItem disponibilidad;
    
	@Override
	public ItemDTOForListMinimal from(Item item) {
		this.id_item = item.getId_item();
		this.nombre = item.getNombre();
		this.disponibilidad = item.getDisponibilidad();
		return this;
	}
    
    
   
}
