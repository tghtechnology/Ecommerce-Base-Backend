package tghtechnology.tiendavirtual.Models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "marca")
@Getter
@Setter
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_marca;
    
    @Column(unique = true, nullable = false, length = 50)
    private String text_id;
    
    @Column(nullable = false, length = 50)
    private String nombre;
    
    @Column(nullable = false, length = 300)
    private String descripcion;
    
    @Column(nullable = false)
    private LocalDateTime fecha_creacion;
    
    @Column(nullable = false)
    private Boolean estado;

    @OneToMany(mappedBy = "marca")
    private Set<Item> items = new HashSet<>();
    
    @OneToOne
    @JoinColumn(name = "id_imagen")
    private Imagen logo;
    
    public static String transform_id(String desc) {
    	return desc.strip().replace(' ', '_').toLowerCase();
    }
    
}
