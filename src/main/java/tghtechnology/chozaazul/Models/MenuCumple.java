package tghtechnology.chozaazul.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Enums.Mes;


@Getter
@Setter
@Entity
@Table(name = "tbl_menu_cumple")
public class MenuCumple {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_menu;
	
	@Column(nullable = false)
	private Mes mes;
	
	@Column(nullable = false)
	private boolean estado;
	
	
	// Plato
	@OneToOne
	@JoinColumn(name = "id_plato")
	private Plato plato;
	
}
