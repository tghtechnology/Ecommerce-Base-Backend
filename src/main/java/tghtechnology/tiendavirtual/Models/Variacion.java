package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;

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
import tghtechnology.tiendavirtual.Enums.TipoVariacion;

@Entity
@Table(name = "variacion")
@Getter
@Setter
public class Variacion implements Comparable<Variacion>{

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_variacion;
    
    @Column(nullable = false)
    private TipoVariacion tipo_variacion;
    
    @Column(nullable = false)
    private String valor_variacion;
    
//    @Column(nullable = true)
//    private BigDecimal costo; //TODO: terminar
    
    @Column(nullable = false)
    private BigDecimal precio;

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
    
    public static String transform_id(String nombre) {
		return nombre.strip()				// sin espacios al inicio o final
				.replace(' ', '_')			// espacios y guiones por _
				.replace('-', '_')
				.replaceAll("(\\+|,|')+","")// simbolos por vacio
				.toLowerCase();				// minusculas
	}

	@Override
	public int compareTo(Variacion o) {
		return this.id_variacion.compareTo(o.getId_variacion());
	}

}
