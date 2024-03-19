package tghtechnology.tiendavirtual.Enums;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public enum TipoUsuario implements GrantedAuthority{
	
	ADMIN,
	GERENTE,
	EMPLEADO,
	CLIENTE
	;

	@Override
	public String getAuthority() {
		return "ROLE_" + 
					this.toString();
	}
	
	public static TipoUsuario fromAuthority(String authority) {
		return valueOf(authority.replaceAll("ROLE_", ""));
	}
	
	public static boolean checkRole(Collection<? extends GrantedAuthority> authorities, TipoUsuario permisoMinimo) {
		
		TipoUsuario rol = fromAuthority(authorities.stream().toList().get(0).toString());
		System.out.println(rol);
		return rol.ordinal() <= permisoMinimo.ordinal();
	}

}
