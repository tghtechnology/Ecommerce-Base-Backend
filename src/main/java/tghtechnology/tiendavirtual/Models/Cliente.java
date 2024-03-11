package tghtechnology.tiendavirtual.Models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;


@Getter
@Setter
@Entity
@Table(name = "tbl_cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false)
	private TipoDocIdentidad tipo_documento;
	
	@Column(nullable = false, length = 15)
	private String numero_documento;
	
	@Column(nullable = false, length = 80)
	private String nombre;

	@Column(nullable = false, length = 15)
	private String telefono;
	
	@Column(nullable = false, length = 80)
	private String correo;
	
	@Column(nullable = false, length = 20)
	private String distrito;
	
	@Column(nullable = false, length = 150)
	private String direccion;
	
	@Column(nullable = true, length = 150)
	private String referencia;
	
	@Column(nullable = false)
	private boolean estado;
	
	
	@OneToMany(mappedBy = "cliente")
	private Set<Pedido> pedidos = new HashSet<>();
	 
	
	
}
