package tghtechnology.tiendavirtual.dto.Venta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class VentaDTOForListMinimal implements DTOForList<Venta>{

	private Integer id_venta;
	private String num_comprobante;
	private LocalDateTime fecha_realizacion;
	private EstadoPedido estado_pedido;
	private BigDecimal precio_total;
	
	@Override
	public VentaDTOForListMinimal from(Venta ven) {
		
		AtomicReference<BigDecimal> antes_de_igv = new AtomicReference<>(BigDecimal.ZERO);
		BigDecimal igv;
		
		this.id_venta = ven.getId_venta();
		this.num_comprobante = ven.getNum_comprobante();
		this.fecha_realizacion = ven.getFecha();
		this.estado_pedido = ven.getEstado_pedido();
		
		ven.getDetalles().forEach(dv -> {
			DetalleVentaDTOForList det = new DetalleVentaDTOForList().from(dv);
			antes_de_igv.set(antes_de_igv.get().add(det.getSubtotal()));
		});
		
		igv = new BigDecimal(ven.getPorcentaje_igv()).multiply(antes_de_igv.get());
		
		this.precio_total = antes_de_igv.get().add(igv);
		
		return this;
	}

}
