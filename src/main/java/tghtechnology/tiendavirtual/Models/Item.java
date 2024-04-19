package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_item;
    
    @Column(nullable = false, unique = true, length = 100)
	private String text_id;
    
    @Column(nullable = false, unique = true, length = 20)
    private String codigo_item;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String descripcion;
    
    @Column(nullable = false)
    private BigDecimal costo;
    
    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private DisponibilidadItem disponibilidad;

    @Column(nullable = false)
    private LocalDateTime fecha_creacion;

    @Column(nullable = false)
    private LocalDateTime fecha_modificacion;
    
    @Column(nullable = false)
    private Double estrellas;
    
    @Column(nullable = false)
    private Integer valoraciones;

    @Column(nullable = false)
    private Boolean estado;
    
    private Integer id_rebaja;
    
    @OneToOne
    @JoinColumn(name = "id_imagen", nullable = false)
    private Imagen imagen;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
    
    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = true)
    private Marca marca;
    
    @OneToOne
    @JoinColumn(name = "id_descuento", nullable = true)
    private Descuento descuento;

    @OneToMany(mappedBy = "item")
    private Set<Variacion> variaciones = new HashSet<>();
    
    @OneToMany(mappedBy = "item")
    private Set<Descuento> descuentos = new HashSet<>();
    
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "items")
    @SQLRestriction("activo = true AND estado = true")
    private Set<Rebaja> rebajas = new HashSet<>();
    
    public static String transform_id(String nombre) {
		return nombre.strip()				// sin espacios al inicio o final
				.replace(' ', '_')			// espacios y guiones por _
				.replace('-', '_')
				.replaceAll("(\\+|,|\\<\\>')+","")// simbolos por vacio
				.toLowerCase();				// minusculas
	}

}
