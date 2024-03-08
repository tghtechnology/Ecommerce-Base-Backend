package tghtechnology.tiendavirtual.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Notificacion")
public class Notificacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false)
	private Boolean visto;
	@Column(nullable = false)
	private LocalDateTime hora;
	@Column(nullable = false)
	private Boolean activo;
	
	// Pedido que notifica
	@OneToOne
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;
	
}
