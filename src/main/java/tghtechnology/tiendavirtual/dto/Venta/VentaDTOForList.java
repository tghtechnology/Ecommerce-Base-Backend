package tghtechnology.tiendavirtual.dto.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoComprobante;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class VentaDTOForList implements DTOForList<Venta>{

	private Integer id_venta;
	private TipoComprobante tipo_comprobante;
	private String num_comprobante;
	private LocalDateTime fecha_realizacion;
	private EstadoPedido estado_pedido;
	
	private List<DetalleVentaDTOForList> detalles = new ArrayList<>();
	
	private BigDecimal valor_antes_de_igv = BigDecimal.ZERO;
	private Integer porcentaje_igv;
	private BigDecimal igv;
	private BigDecimal precio_total;
	
	private String observacion;
	
	private ClienteVentaDTOForList cliente;
	
	@Override
	public VentaDTOForList from(Venta ven) {
		this.id_venta = ven.getId_venta();
		this.tipo_comprobante = ven.getTipo_comprobante();
		this.num_comprobante = ven.getNum_comprobante();
		this.fecha_realizacion = ven.getFecha();
		this.estado_pedido = ven.getEstado_pedido();
		this.observacion = ven.getObservacion();
		this.porcentaje_igv = ven.getPorcentaje_igv();
		
		ven.getDetalles().forEach(dv -> {
			DetalleVentaDTOForList det = new DetalleVentaDTOForList().from(dv,ven.getPorcentaje_igv(), ven.getAntes_de_igv());
			detalles.add(det);
			valor_antes_de_igv = valor_antes_de_igv.add(det.getSubtotal());
		});
		
		this.igv = new BigDecimal(this.porcentaje_igv).multiply(this.valor_antes_de_igv);
		
		this.precio_total = this.valor_antes_de_igv.add(this.igv);
		
		this.cliente = new ClienteVentaDTOForList().from(ven);
		
		return this;
	}

}
