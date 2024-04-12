package tghtechnology.tiendavirtual.dto.VariacionItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class VariacionDTOForList implements DTOForList<Variacion>{

	private Integer id_variacion;
	private Integer correlativo;
	private String nombre_variacion;
	private DisponibilidadItem disponibilidad;
	private Integer stock;
	private Boolean aplicar_descuento;
	private String imagen;
	
	@Override
	public VariacionDTOForList from(Variacion var) {
		this.id_variacion = var.getId_variacion();
		this.correlativo = var.getCorrelativo();
		this.nombre_variacion = var.getNombre_variacion();
		this.disponibilidad = var.getDisponibilidad();
		this.stock = var.getStock();
		this.aplicar_descuento = var.getAplicarDescuento();
		this.imagen = var.getImagen().getImagen();
		return this;
	}

}
