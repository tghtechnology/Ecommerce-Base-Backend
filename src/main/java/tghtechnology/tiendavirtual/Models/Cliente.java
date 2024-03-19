package tghtechnology.tiendavirtual.Models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	private Integer id_persona;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Persona persona;
	
//	@Column(nullable = false)
//	private TipoDocIdentidad tipo_documento;
//	
//	@Column(nullable = false, length = 15)
//	private String numero_documento;
//	
//	@Column(nullable = false, length = 80)
//	private String nombre;
//
//	@Column(nullable = false, length = 15)
//	private String telefono;
//	
//	@Column(nullable = false, length = 80)
//	private String correo;
	
	@Column(nullable = false, length = 30)
	private String region;
	
	@Column(nullable = false, length = 30)
	private String provincia;
	
	@Column(nullable = false, length = 30)
	private String distrito;
	
	@Column(nullable = false, length = 150)
	private String direccion;
	
	@Column(nullable = true, length = 150)
	private String referencia;
	
	@Column(nullable = true)
	private Double latitud;
	
	@Column(nullable = true)
	private Double longitud;
	
	@Column(nullable = false)
	private boolean estado;
	
	// Pedidos realizados
	@OneToMany(mappedBy = "cliente")
	private Set<Pedido> pedidos = new HashSet<>();
	
	@OneToOne
	@JoinColumn(name = "id_pedido", nullable = true)
	private Pedido carrito;
	 
	
	
//	// Usuario
//	@OneToOne
//	@JoinColumn(name = "id_usuario", nullable = true)
//	private Usuario usuario;
	
	
}
