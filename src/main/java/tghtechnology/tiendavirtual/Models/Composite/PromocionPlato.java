package tghtechnology.tiendavirtual.Models.Composite;

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
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Models.Promocion;


@Getter
@Setter
@Entity
@Table(name = "tbl_promocion_plato")
public class PromocionPlato {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false)
	private Integer cantidad;
	
	@Column(nullable = false)
	private boolean estado;
	
	@ManyToOne
	@JoinColumn(name = "id_promocion", nullable = false)
	private Promocion promocion;
	
	@ManyToOne
	@JoinColumn(name = "id_plato", nullable = false)
	private Item plato;
	
	@ManyToOne
	@JoinColumn(name = "id_plato2", nullable = true)
	private Item plato2;
	
	@ManyToOne
	@JoinColumn(name = "id_plato3", nullable = true)
	private Item plato3;
	
}
