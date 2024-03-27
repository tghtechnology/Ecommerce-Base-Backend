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
import tghtechnology.tiendavirtual.Enums.TipoVariacion;

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
	private Integer id_variacion;
	
	@Column(nullable = false)
	private TipoVariacion tipo_variacion;
	
	@Column(nullable = false, length = 10)
	private String valor_variacion;
	
	@Column(nullable = false)
	private BigDecimal precio_unitario;
	
	@Column(nullable = false)
	private Integer porcentaje_descuento;
	
	@Column(nullable = false)
	private Short cantidad;
	
	@ManyToOne
	@JoinColumn(name = "id_venta")
	private Venta venta;
	
}
