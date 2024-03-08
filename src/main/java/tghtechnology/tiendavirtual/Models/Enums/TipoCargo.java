package tghtechnology.tiendavirtual.Models.Enums;

import org.springframework.security.core.GrantedAuthority;

public enum TipoCargo implements GrantedAuthority{
	
	GERENTE,
	EMPLEADO;

	@Override
	public String getAuthority() {
		return "ROLE_" + 
					this.toString();
	}

}
