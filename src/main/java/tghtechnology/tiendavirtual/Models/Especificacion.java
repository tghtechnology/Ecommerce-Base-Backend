package tghtechnology.tiendavirtual.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;

@Entity
@Table(name = "especificacion")
@Getter
@Setter
public class Especificacion implements Comparable<Especificacion>{

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_especificacion;
    
    @Column(nullable = false)
    private Integer correlativo;
    
    @Column(nullable = false)
    private String nombre_especificacion;

    @Column(nullable = false)
    private DisponibilidadItem disponibilidad;

    @Column(nullable = false)
    private Boolean estado;
    
    @ManyToOne
    @JoinColumn(name = "id_variacion", nullable = false)
    private Variacion variacion;

	@Override
	public int compareTo(Especificacion o) {
		return this.correlativo.compareTo(o.getCorrelativo());
	}
	
    public String composite_text_id() {
		String var_text_id = this.getNombre_especificacion().strip() // sin espacios al inicio o final
				.replace(' ', '_')			// espacios y guiones por _
				.replace('-', '_')
				.replaceAll("(\\+|,|')+","")// simbolos por vacio
				.toLowerCase();				// minusculas
		
		
		return String.format("%s (%s)", variacion.composite_text_id(), var_text_id);
	}

}
