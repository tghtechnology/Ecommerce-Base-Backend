package tghtechnology.tiendavirtual.Models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Persona persona;
	
	@Column(nullable = false)
	private boolean recibe_correos;
	
	@Column(nullable = false)
	private boolean estado;
	
	
	@OneToMany(mappedBy = "cliente")
	private Set<Direccion> direcciones = new HashSet<>();
	 
	@OneToOne
	@JoinColumn(name = "id_usuario", nullable = false)
	private Usuario usuario;	
	
}
