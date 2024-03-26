package tghtechnology.tiendavirtual.dto.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoComprobante;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@AllArgsConstructor
public class VentaDTOForList implements DTOForList<Venta>{

	private Integer id_venta;
	private TipoComprobante tipo_comprobante;
	private String num_comprobante;
	private LocalDateTime fecha_realizacion;
	private EstadoPedido estado_pedido;
	
	private List<DetalleVentaDTOForList> detalles = new ArrayList<>();
	
	private BigDecimal antes_de_igv = BigDecimal.ZERO;
	private Integer porcentaje_igv;
	private BigDecimal igv;
	private BigDecimal precio_total;
	
	@Override
	public VentaDTOForList from(Venta ven) {
		this.id_venta = ven.getId_venta();
		this.tipo_comprobante = ven.getTipo_comprobante();
		this.num_comprobante = ven.getNum_comprobante();
		this.fecha_realizacion = ven.getFecha_venta();
		this.estado_pedido = ven.getEstado_pedido();
		this.porcentaje_igv = ven.getPorcentaje_igv();
		
		ven.getDetalles().forEach(dv -> {
			DetalleVentaDTOForList det = new DetalleVentaDTOForList().from(dv);
			detalles.add(det);
			antes_de_igv = antes_de_igv.add(det.getSubtotal());
		});
		
		this.igv = new BigDecimal(this.porcentaje_igv).multiply(this.antes_de_igv);
		
		this.precio_total = this.antes_de_igv.add(this.igv);
		
		return this;
	}

}
