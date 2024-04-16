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
@Table(name = "detalle_venta" )
@Getter
@Setter
public class DetalleVenta {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_detalle;
	
	@Column(nullable = false)
	private Integer id_item;
	
	@Column(nullable = false)
	private String nombre_item;
	
	@Column(nullable = false)
	private String codigo_item;
	
	@Column(nullable = false)
	private Integer id_variacion;
	
	@Column(nullable = false)
	private Integer variacion_correlativo;
	
	@Column(nullable = false, length = 40)
	private String nombre_variacion;
	
	@Column(nullable = true)
	private Integer id_especificacion;
	
	@Column(nullable = true, length = 40)
	private String nombre_especificacion;
	
	@Column(nullable = true)
	private Integer especificacion_correlativo;
	
	@Column(nullable = false)
	private BigDecimal precio_unitario;
	
	@Column(nullable = false)
	private BigDecimal costo_unitario;
	
	@Column(nullable = false)
	private Integer porcentaje_descuento;
	
	@Column(nullable = false)
	private Short cantidad;
	
	@ManyToOne
	@JoinColumn(name = "id_venta")
	private Venta venta;
	
}
