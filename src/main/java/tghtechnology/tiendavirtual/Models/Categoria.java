package tghtechnology.tiendavirtual.Models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categoria")
@Getter
@Setter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_categoria;
    
    @Column(unique = true, nullable = false, length = 50)
    private String text_id;
    
    @Column(nullable = false, length = 50)
    private String descripcion;
    
    @Column(nullable = false)
    private LocalDateTime fecha_creacion;
    
    @Column(nullable = false)
    private Boolean estado;

    private Integer id_rebaja;
    
    @OneToMany(mappedBy = "categoria")
    private Set<Item> item = new HashSet<>();
    
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "categorias")
    private Set<Rebaja> rebajas = new HashSet<>();
    
    public static String transform_id(String desc) {
    	return desc.strip().replace(' ', '_').toLowerCase();
    }
    
}
