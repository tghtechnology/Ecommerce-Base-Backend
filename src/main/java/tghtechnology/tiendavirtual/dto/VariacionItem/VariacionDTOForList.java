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
public class VariacionDTOForList implements DTOForList<Variacion>{

	private Integer id_variacion;
	private Integer correlativo;
	private TipoVariacion tipo_variacion;
	private String valor_variacion;
	private BigDecimal precio;
	private DisponibilidadItem disponibilidad;
	private Integer stock;
	private Boolean aplicar_descuento;
	
	@Override
	public VariacionDTOForList from(Variacion var) {
		this.id_variacion = var.getId_variacion();
		this.correlativo = var.getCorrelativo();
		this.tipo_variacion = var.getTipo_variacion();
		this.valor_variacion = var.getValor_variacion();
		this.precio = var.getPrecio();
		this.disponibilidad = var.getDisponibilidad();
		this.stock = var.getStock();
		this.aplicar_descuento = var.getAplicarDescuento();
		return this;
	}

}
