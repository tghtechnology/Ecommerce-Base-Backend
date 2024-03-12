package tghtechnology.tiendavirtual.dto.Pedido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.DetallePedido;
import tghtechnology.tiendavirtual.Models.Pedido;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForList;
import tghtechnology.tiendavirtual.dto.Pedido.DetallePedido.DetallePedidoDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class PedidoDTOForList implements DTOForList<Pedido>{

	private Integer id_pedido;
    private BigDecimal precio_total;
    private ClienteDTOForList cliente;
    private List<DetallePedidoDTOForList> detalles;

	@Override
	public DTOForList<Pedido> from(Pedido pedido) {
		this.setId_pedido(pedido.getId_pedido());
        this.cliente = new ClienteDTOForList().from(pedido.getCliente());
        //this.setPrecio_total(pedido.getPrecio_total());
        BigDecimal precio_total = new BigDecimal(0);
        
        this.detalles = new ArrayList<>();
        for( DetallePedido dp : pedido.getDetallePedido()) {
        	detalles.add(new DetallePedidoDTOForList(dp));
        	precio_total = precio_total.add(dp.getSub_total());
        }
        
        pedido.getDetallePedido().forEach(dp -> detalles.add(new DetallePedidoDTOForList(dp)));
        return this;
	}
}
