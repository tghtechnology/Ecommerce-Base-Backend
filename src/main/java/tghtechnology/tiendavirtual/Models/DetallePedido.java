package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;

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
@Table(name = "tbl_detalle_pedido")
@Getter
@Setter
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_detalle;

    @ManyToOne
    @JoinColumn(name = "id_plato" )
    private Item plato;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
    private Integer cantidad;
    private BigDecimal sub_total;
}
