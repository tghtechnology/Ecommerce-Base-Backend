package tghtechnology.chozaazul.Models;

import java.math.BigDecimal;
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
import tghtechnology.chozaazul.Models.Enums.TipoPlato;

@Entity
@Table(name = "tbl_plato")
@Getter
@Setter
public class Plato {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_plato;
    
    @Column(nullable = false, unique = true, length = 100)
	private String text_id;

    @Column(nullable = false)
    private String nombre_plato;

    @Column(nullable = false, length = 150)
    private String descripcion;

    @Column(nullable = false)
    private String imagen_primaria;

    @Column(nullable = false)
    private String imagen_secundaria;

    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private String disponibilidad;

    @Column(nullable = false)
    private LocalDateTime fecha_creacion;

    @Column(nullable = false)
    private LocalDateTime fecha_modificacion;

    @Column(nullable = false)
    private Boolean estado;

    @Column(nullable = false)
    private TipoPlato tipoPlato;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "plato")
    private Set<DetallePedido> detalle_pedido = new HashSet<>();

    public static String transform_id(String nombre_plato) {
		return nombre_plato.strip()				// sin espacios al inicio o final
				.replace(' ', '_')			// espacios y guiones por _
				.replace('-', '_')
				.replaceAll("(\\+|,|')+","")// simbolos por vacio
				.toLowerCase();				// minusculas
	}

}
