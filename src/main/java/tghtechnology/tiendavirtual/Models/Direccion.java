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


@Getter
@Setter
@Entity
@Table(name = "direccion")
public class Direccion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_direccion;
	
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
	
	@Column(nullable = false)
	private boolean estado;
	
	// Cliente al que pertenece
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	
}
