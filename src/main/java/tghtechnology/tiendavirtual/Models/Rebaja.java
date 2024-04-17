package tghtechnology.tiendavirtual.Models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rebaja")
@Getter
@Setter
public class Rebaja {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_rebaja;
	
	@Column(nullable = false, unique = true, length = 50)
	private String text_id;
	
	@Column(nullable = false, length = 50)
	private String nombre;
	
	@Column(nullable = false, length = 400)
	private String descripcion;
	
	@Column(nullable = true)
	private LocalDate fecha_inicio;
	
	@Column(nullable = true)
	private LocalDate fecha_fin;
	
	@Column(nullable = false)
	private Boolean es_descuento;
	
	@Column(nullable = false)
	private Integer valor_descuento;
	
	@Column(nullable = false)
	private Boolean es_evento;
	
	@Column(nullable = false)
	private Boolean activo;

	@Column(nullable = false)
	private Boolean estado;
	
	@ManyToMany
	@JoinTable(name = "id_item",
			joinColumns = @JoinColumn(name = "id_rebaja", referencedColumnName = "id_rebaja"),
			inverseJoinColumns = @JoinColumn(name = "id_item", referencedColumnName = "id_item"))
	private Set<Item> items = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "id_categoria",
			joinColumns = @JoinColumn(name = "id_rebaja",referencedColumnName = "id_rebaja"),
			inverseJoinColumns = @JoinColumn(name = "id_categoria",referencedColumnName = "id_categoria"))
	private Set<Categoria> categorias = new HashSet<>();
	
    public static String transform_id(String desc) {
    	return desc.strip().replace(' ', '_').toLowerCase();
    }
	
}
