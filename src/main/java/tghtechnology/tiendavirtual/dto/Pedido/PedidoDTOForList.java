package tghtechnology.tiendavirtual.dto.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Pedido;
import tghtechnology.tiendavirtual.Models.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Models.Enums.TipoDelivery;
import tghtechnology.tiendavirtual.dto.Cliente.ClienteDTOForList;
import tghtechnology.tiendavirtual.dto.Pedido.DetallePedido.DetallePedidoDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class PedidoDTOForList {

	private Integer id_pedido;
	private ClienteDTOForList cliente;
    private LocalDateTime fecha_pedido;
    private EstadoPedido estado_pedido;
    private BigDecimal precio_total;
    private String num_comprobante;
    private Boolean antesDeIGV;
    private TipoDelivery tipo_delivery;
    private List<DetallePedidoDTOForList> detalles;
    
    /*Reestructuración para listar pedido*/ 
    public PedidoDTOForList(Pedido pedido){
        this.setId_pedido(pedido.getId_pedido());
        this.cliente = new ClienteDTOForList(pedido.getCliente());
        this.setFecha_pedido(pedido.getFecha_pedido());
        this.setEstado_pedido(pedido.getEstado_pedido());
        this.setPrecio_total(pedido.getPrecio_total());
        this.setNum_comprobante(pedido.getNum_comprobante());
        this.setAntesDeIGV(pedido.getAntesDeIGV());
        this.setTipo_delivery(pedido.getTipo_delivery());
        this.detalles = new ArrayList<>();
        pedido.getDetallePedido().forEach(dp -> detalles.add(new DetallePedidoDTOForList(dp)));
    }
}
