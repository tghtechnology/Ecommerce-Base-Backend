package tghtechnology.chozaazul.Models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Enums.EstadoReserva;

@Getter
@Setter
@Entity
@Table(name = "tbl_reserva")
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_reserva;
	
	@Column(nullable = false)
	private BigDecimal pago_reserva;
	
	@Column(nullable = false)
	private LocalDate fecha_reserva;
	
	@Column(nullable = false)
	private LocalTime hora_inicio;
	
	@Column(nullable = false)
	private EstadoReserva estado_reserva;
	
	// Usuario
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	//Cliente
	@OneToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	
}
