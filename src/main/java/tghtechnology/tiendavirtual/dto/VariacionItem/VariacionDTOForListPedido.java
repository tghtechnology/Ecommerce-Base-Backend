package tghtechnology.tiendavirtual.dto.VariacionItem;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Enums.TipoVariacion;
import tghtechnology.tiendavirtual.Models.Variacion;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class VariacionDTOForListPedido implements DTOForList<Variacion>{

	private Integer id_item;
	private String url;
	private String nombre;
	
	private Integer id_variacion;
	private TipoVariacion tipo_variacion;
	private String valor_variacion;
	private BigDecimal precio;
	private DisponibilidadItem disponibilidad;
	private Integer stock;
	private Boolean aplicar_descuento;
	
	@Override
	public VariacionDTOForListPedido from(Variacion var) {
		this.id_item = var.getItem().getId_item();
		this.url = var.getItem().getText_id();
		this.nombre = var.getItem().getNombre();
		
		this.id_variacion = var.getId_variacion();
		this.tipo_variacion = var.getTipo_variacion();
		this.valor_variacion = var.getValor_variacion();
		this.precio = var.getPrecio();
		this.disponibilidad = var.getDisponibilidad();
		return this;
	}

}
