package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detalle_pedido")
@Getter
@Setter
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_detalle;

    @Column(nullable = false)
    private Integer cantidad;
    @Column(nullable = false)
    private BigDecimal sub_total;
    
    @ManyToOne
    @JoinColumn(name = "id_item" )
    private Item item;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}
