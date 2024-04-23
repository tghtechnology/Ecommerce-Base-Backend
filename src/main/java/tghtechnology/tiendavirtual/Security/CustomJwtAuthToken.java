package tghtechnology.tiendavirtual.Security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import lombok.Getter;
import tghtechnology.tiendavirtual.Enums.TokenActions;

@Getter
public class CustomJwtAuthToken  extends JwtAuthenticationToken{

	private static final long serialVersionUID = 1L;
	
	private final TokenActions action;
	private final Boolean verified;

	public CustomJwtAuthToken(Jwt jwt
								, Collection<? extends GrantedAuthority> authorities
								, String name
								, TokenActions action
								, Boolean verified) {
		super(jwt, authorities, name);
		this.action = action;
		this.verified = verified;
	}

	
	
	

}
