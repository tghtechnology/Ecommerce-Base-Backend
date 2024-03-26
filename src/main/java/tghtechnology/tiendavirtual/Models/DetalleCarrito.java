package tghtechnology.tiendavirtual.Models;

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
@Table(name = "detalle_carrito")
@Getter
@Setter
public class DetalleCarrito implements Comparable<DetalleCarrito>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_detalle;
    
    @Column(nullable = false)
    private Integer correlativo;

    @Column(nullable = false)
    private Integer cantidad;
    
    @ManyToOne
    @JoinColumn(name = "id_variacion" )
    private Variacion variacion;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

	@Override
	public int compareTo(DetalleCarrito o) {
		return this.correlativo.compareTo(o.getCorrelativo());
	}
    
}
