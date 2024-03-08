package tghtechnology.tiendavirtual.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Enums.TipoImagen;

@Getter
@Setter
@Entity
@Table(name = "Imagen")
public class Imagen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 250)
	private String Imagen;
	@Column(nullable = true, length = 250)
	private String public_id_Imagen;
	
	@Column(nullable = true, length = 250)
	private String Miniatura;
	@Column(nullable = true, length = 250)
	private String public_id_Miniatura;
	
	// Objeto al que pertenece
	@Column(nullable = false)
	private TipoImagen tipo;
	@Column(nullable = true)
	private Integer id_owner;
	
}
