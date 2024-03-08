package tghtechnology.chozaazul.dto.Promocion.PromocionPlato;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Composite.PromocionPlato;
import tghtechnology.chozaazul.dto.Plato.PlatoDTOMinimal;

@Getter
@Setter
@NoArgsConstructor
public class PromocionPlatoDTOForList {
	
	private Integer cantidad;
	private List<PlatoDTOMinimal> opciones = new ArrayList<>();
	
	public PromocionPlatoDTOForList(PromocionPlato prpl) {
		this.cantidad = prpl.getCantidad();
		this.opciones.add(new PlatoDTOMinimal(prpl.getPlato()));
		if(prpl.getPlato2() != null) this.opciones.add(new PlatoDTOMinimal(prpl.getPlato2()));
		if(prpl.getPlato3() != null) this.opciones.add(new PlatoDTOMinimal(prpl.getPlato3()));
	}
	
}
