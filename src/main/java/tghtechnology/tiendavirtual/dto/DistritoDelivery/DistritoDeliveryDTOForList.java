package tghtechnology.tiendavirtual.dto.DistritoDelivery;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DistritoLima;
import tghtechnology.tiendavirtual.Models.DistritoDelivery;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class DistritoDeliveryDTOForList implements DTOForList<DistritoDelivery>, Comparable<DistritoDeliveryDTOForList>{

	private DistritoLima id_distrito;
	private String nombre;
	private BigDecimal precio_delivery;
	private Boolean activo;
	
	@Override
	public DistritoDeliveryDTOForList from(DistritoDelivery dd) {
		this.id_distrito = dd.getId_distrito();
		this.nombre = dd.getId_distrito().getNombre();
		this.precio_delivery = dd.getPrecio_delivery();
		this.activo = dd.getActivo();
		return this;
	}

	@Override
	public int compareTo(DistritoDeliveryDTOForList o) {
		return this.nombre.compareTo(o.getNombre());
	}
}
