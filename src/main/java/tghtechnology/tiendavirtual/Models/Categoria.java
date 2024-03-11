package tghtechnology.tiendavirtual.Models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_categoria")
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

    @OneToMany(mappedBy = "categoria")
    private Set<Item> item = new HashSet<>();
    
    public static String transform_id(String desc) {
    	return desc.strip().replace(' ', '_');
    }
    
}
