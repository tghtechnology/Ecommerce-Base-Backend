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
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;

@Entity
@Table(name = "venta" )
@Getter
@Setter
public class Venta implements Comparable<Venta>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_venta;
    
    // Datos de venta
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
    
    // Datos del cliente
    @Column(nullable = false)
	private TipoDocIdentidad tipo_documento;
	
	@Column(nullable = false, length = 15)
	private String numero_documento;
	
	@Column(nullable = false, length = 190)
	private String razon_social;

	@Column(nullable = false, length = 15)
	private String telefono;
	
	@Column(nullable = false, length = 80)
	private String correo;
    
	// Datos de direccion
	@Column(nullable = false, length = 30)
	private String region;
	
	@Column(nullable = false, length = 30)
	private String provincia;
	
	@Column(nullable = false, length = 30)
	private String distrito;
	
	@Column(nullable = false, length = 150)
	private String direccion;
	
	@Column(nullable = true, length = 150)
	private String referencia;
	
	@Column(nullable = true)
	private Double latitud;
	
	@Column(nullable = true)
	private Double longitud;
	
	@Column(nullable = true, length = 250)
	private String observacion;
	
	
    // Datos de entidad
    @Column(nullable = false)
    private boolean estado;
    
  	//Cliente
  	@ManyToOne
  	@JoinColumn(name = "id_cliente", nullable = true)
	private Cliente cliente;
  	
  	@OneToMany(mappedBy = "venta")
  	private Set<DetalleVenta> detalles = new HashSet<>();

	@Override
	public int compareTo(Venta o) {
		return this.fecha_venta.compareTo(o.getFecha_venta());
	}
  	
}
