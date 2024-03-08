package tghtechnology.chozaazul.Models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Composite.PromocionPlato;

@Getter
@Setter
@Entity
@Table(name = "tbl_promocion")
public class Promocion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_promocion;
	
	@Column(nullable = false)
	private BigDecimal descuento;
	
	@Column(nullable = false)
	private boolean repetible;
	
	@Column(nullable = false)
	private LocalDate fecha_inicio;
	@Column(nullable = false)
	private LocalDate fecha_finalizacion;
	
	
	@Column(nullable = false)
	private Boolean estado;
	
	// Productos que componen la promocion
	@OneToMany(mappedBy = "promocion")
	private Set<PromocionPlato> platos = new HashSet<>();
	
}
