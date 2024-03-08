package tghtechnology.chozaazul.dto.MenuCumple;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.MenuCumple;
import tghtechnology.chozaazul.Models.Enums.Mes;
import tghtechnology.chozaazul.dto.Plato.PlatoDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class MenuCumpleDTOForList {

	private Integer id_menu;
	private Mes mes;
	private PlatoDTOForList plato;
	
	public MenuCumpleDTOForList(MenuCumple mc) {
		this.id_menu = mc.getId_menu();
		this.mes = mc.getMes();
		this.plato = new PlatoDTOForList(mc.getPlato());
	}
	
	
}
