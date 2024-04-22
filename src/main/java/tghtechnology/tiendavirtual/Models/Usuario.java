package tghtechnology.tiendavirtual.Models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import tghtechnology.tiendavirtual.Enums.TipoUsuario;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_usuario;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false, length = 70)
	private String hashed_pass; 
	
	@Column(nullable = false)
	private boolean autenticado;
	
	@Column(nullable = false)
	private boolean cambio_de_pass;
	
	@Column(nullable = false)
	private LocalDateTime fecha_creacion;
	
	@Column(nullable = false)
	private LocalDateTime fecha_modificacion;
	
	@Column(nullable = true)
	private LocalDateTime ultimo_login;
	
	@Column(nullable = false)
	TipoUsuario cargo;
	
	@Column(nullable = false)
	private boolean estado;
	
	@OneToOne
	@JoinColumn(name = "id_persona")
	private Persona persona;
	
	@OneToOne(mappedBy = "usuario")
	private Carrito carrito;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(this.cargo);
	}

	@Override
	public String getPassword() {
		return hashed_pass;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return estado;
	}
	
}
