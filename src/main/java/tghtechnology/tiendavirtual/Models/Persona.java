package tghtechnology.tiendavirtual.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;


@Getter
@Setter
@Entity
@Table(name = "persona")
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_persona;
	
	@Column(nullable = false)
	private TipoDocIdentidad tipo_documento;
	
	@Column(nullable = false, length = 15)
	private String numero_documento;
	
	@Column(nullable = false, length = 80)
	private String nombres;
	
	@Column(nullable = false, length = 80)
	private String apellidos;

	@Column(nullable = false, length = 15)
	private String telefono;
	
	@Column(nullable = false, length = 80)
	private String correo_personal;
	
	@Column(nullable = false)
	private LocalDateTime fecha_creacion;
	
	@Column(nullable = false)
	private LocalDateTime fecha_modificacion;
	
	@Column(nullable = false)
	private boolean estado;
	
	// Usuario ligado
	@OneToOne(mappedBy = "persona")
	private Usuario usuario;
	
	// Empleado ligado
	@OneToOne(mappedBy = "persona")
	private Empleado empleado;
	
	
}
