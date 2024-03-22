package tghtechnology.tiendavirtual.Models;

import java.math.BigDecimal;
import java.time.LocalDate;

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

@Entity
@Table(name = "descuento")
@Getter
@Setter
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id_descuento;
    @Column(nullable = false)
    private Integer porcentaje;
    @Column(nullable = true)
	private LocalDate programacion_inicio;
	@Column(nullable = true)
	private LocalDate programacion_final;
    
	@Column(nullable = false)
	private Boolean activado;
	@Column(nullable = false)
	private Boolean estado;
	
    @ManyToOne
    @JoinColumn(name = "id_item" )
    private Item item;

}
