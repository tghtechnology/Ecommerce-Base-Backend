package tghtechnology.tiendavirtual.Models;

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

@Getter
@Setter
@Entity
@Table(name = "publicidad")
public class Publicidad {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_publicidad;
	
	@Column(nullable = false, length = 40)
	private String nombre;
	@Column(nullable = false, length = 100)
	private String enlace;
	@Column(nullable = false)
	private Boolean mostrar;
	@Column(nullable = false)
	private Boolean estado;
	
	@OneToOne
    @JoinColumn(name = "id_imagen", nullable = true)
    private Imagen imagen;
	
	public String get_img_id() {
		return String.format("novedades_%08d", id_publicidad);
	}
	
}