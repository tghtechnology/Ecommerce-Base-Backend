package tghtechnology.tiendavirtual.Enums;

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

}
