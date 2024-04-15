package tghtechnology.tiendavirtual.Models;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;

@Entity
@Table(name = "variacion")
@Getter
@Setter
public class Variacion implements Comparable<Variacion>{

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_variacion;
    
    @Column(nullable = false)
    private Integer correlativo;
    
    @Column(nullable = false)
    private String nombre_variacion;

    @Column(nullable = false)
    private DisponibilidadItem disponibilidad;
    
    @Column(nullable = false)
    private Integer stock;
    
    @Column(nullable = false)
    private Boolean aplicarDescuento;

    @Column(nullable = false)
    private Boolean estado;
    
    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;
    
    @OneToOne
    @JoinColumn(name = "id_imagen", nullable = false)
    private Imagen imagen;
    
    @OneToMany(mappedBy = "variacion")
    private Set<Especificacion> especificaciones = new HashSet<>();

	@Override
	public int compareTo(Variacion o) {
		return this.id_variacion.compareTo(o.getId_variacion());
	}
	
    public String composite_text_id() {
		String var_text_id = this.nombre_variacion.strip()	// sin espacios al inicio o final
				.replace(' ', '_')			// espacios y guiones por _
				.replace('-', '_')
				.replaceAll("(\\+|,|'\\{\\})+","")// simbolos por vacio
				.toLowerCase();				// minusculas
		
		
		return String.format("%s_%s", item.getText_id(), var_text_id);
	}

}
