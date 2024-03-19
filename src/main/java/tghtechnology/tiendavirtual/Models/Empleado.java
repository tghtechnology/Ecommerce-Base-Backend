package tghtechnology.tiendavirtual.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadEmpleado;

@Getter
@Setter
@Entity
@Table(name = "empleado")
public class Empleado {

	@Id
	private Integer id_persona;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Persona persona;
	
	@Column(nullable = false)
	private DisponibilidadEmpleado disponibilidad;
	
	@Column(nullable = false)
	private LocalDateTime fecha_creacion;
	
	@Column(nullable = false)
	private LocalDateTime fecha_modificacion;
	
	@Column(nullable = false)
	private boolean estado;
	
	// Usuario
	@OneToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;
	
}
