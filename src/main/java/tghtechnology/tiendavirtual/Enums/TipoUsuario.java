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
	
	/**
	 * Compara Si un rol proporcionado es superior o igual a otro
	 * @param authorities Roles de un usuario como se obtiene de un Authentication
	 * @param rolMinimo Nivel mínimo de rol que permite
	 * @return <strong>True</strong> si el rol obtenido es superior al rol mínimo, <strong>False</strong> en el caso opuesto.
	 */
	public static boolean checkRole(Collection<? extends GrantedAuthority> authorities, TipoUsuario rolMinimo) {
		TipoUsuario rol = fromAuthority(authorities.stream().toList().get(0).toString());
		return rol.ordinal() <= rolMinimo.ordinal();
	}

}
