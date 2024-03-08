package tghtechnology.chozaazul.Models;

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
@Table(name = "tbl_empleado")
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_empleado;
	
	@Column(nullable = false)
	private String nombres;
	
	@Column(nullable = false)
	private String apellidos;

	@Column(nullable = false)
	private String telefono;
	
	@Column(nullable = false)
	private String correo;
	
	@Column(nullable = false)
	private LocalDateTime fecha_creacion;
	
	@Column(nullable = false)
	private boolean estado;
	
	
	// Usuario
	@OneToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
}
