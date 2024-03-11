package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Enums.TipoDelivery;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocumento;

@Entity
@Table(name = "tbl_venta" )
@Getter
@Setter
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_venta;

    @Column(nullable = false)
    private TipoDocumento tipo_comprobante;
    
    @Column(nullable = false)
    private LocalDateTime fecha_pedido;

    @Column(nullable = false)
    private EstadoPedido estado_pedido;
    
    @Column(nullable = false)
    private Boolean antesDeIGV;

    @Column(nullable = true)
    private String num_comprobante;
    
    @Column(nullable = false)
    private TipoDelivery tipo_delivery;
    
    @Column(nullable = true)
    private BigDecimal costo_delivery;
    
    @Column(nullable = false)
    private boolean estado;


    @OneToMany(mappedBy = "pedido")
    private Set<DetallePedido> detallePedido = new HashSet<>();

  	//Cliente
  	@ManyToOne
  	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
  	
  	// Pedido que presenta
  	@OneToOne
  	@JoinColumn(name = "id_pedido")
 	private Pedido pedido;
  	
}
