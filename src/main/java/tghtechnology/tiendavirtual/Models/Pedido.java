package tghtechnology.tiendavirtual.Models;

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

@Entity
@Table(name = "pedido")
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_pedido;
    
    @Column(nullable = false)
    private boolean completado;
    
    @Column(nullable = false)
    private boolean estado;

    @OneToMany(mappedBy = "pedido")
    private Set<DetallePedido> detallePedido = new HashSet<>();

  	//Cliente
  	@ManyToOne
  	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
  	
  	@OneToOne(mappedBy = "pedido")
 	private Venta venta;
  	
  	@OneToOne(mappedBy = "carrito")
 	private Cliente clienteCarrito;
  	
}
