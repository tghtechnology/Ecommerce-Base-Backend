package tghtechnology.tiendavirtual.Models;

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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoComprobante;

@Entity
@Table(name = "venta" )
@Getter
@Setter
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_venta;

    @Column(nullable = false)
    private TipoComprobante tipo_comprobante;
    
    @Column(nullable = true)
    private String num_comprobante;
    
    @Column(nullable = false)
    private LocalDateTime fecha_venta;

    @Column(nullable = false)
    private EstadoPedido estado_pedido;
    
    @Column(nullable = false)
    private Integer porcentaje_igv;
    
    @Column(nullable = false)
    private boolean estado;
    
  	//Cliente
  	@ManyToOne
  	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
  	
  	@OneToMany(mappedBy = "venta")
  	private Set<DetalleVenta> detalles = new HashSet<>();
  	
}
