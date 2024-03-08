package tghtechnology.tiendavirtual.Models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Enums.TipoCargo;

@Getter
@Setter
@Entity
@Table(name = "tbl_usuario")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_usuario;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(nullable = false, length = 70)
	private String password; 
	
	@Column(nullable = false)
	private LocalDateTime fechaCreacion; 
	
	@Column(nullable = false)
	TipoCargo cargo;
	
	@Column(nullable = false)
	private boolean estado;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(this.cargo);
	}

	@Override
	public String getPassword() {
		return password;
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


	// Relación con tabla empleado 1,1
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Empleado empleado;
	
	// Reserva
	@OneToMany(mappedBy = "usuario")
	private Set<Reserva> reserva = new HashSet<>();
	
}
