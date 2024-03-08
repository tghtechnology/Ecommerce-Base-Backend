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
@Table(name = "tbl_tipo_plato")
@Getter
@Setter
public class TipoDePlato {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_tipo_plato;
    
    @Column(nullable = false, unique = true, length = 30)
	private String textId;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha_creacion;

    @Column(nullable = false)
    private Boolean estado;

    @OneToMany(mappedBy = "tipoPlato")
    private Set<Item> plato = new HashSet<>();
}
