package tghtechnology.tiendavirtual.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tghtechnology.tiendavirtual.Enums.TokenActions;

@Getter
@AllArgsConstructor
public class TokenDetails {

	private final TokenActions action;
	private final Boolean verificado;
	
	public static final TokenDetails getDetails(Authentication auth) {
		return getDetails((Jwt)auth.getPrincipal());
	}
	
	public static final TokenDetails getDetails(Jwt jwt) {
		TokenActions action = TokenActions.valueOf(jwt.getClaimAsString("action"));
		Boolean verificado = jwt.getClaimAsBoolean("verified");
		
		return new TokenDetails(action,verificado);
	}
	
	public static final TokenDetails getDetails(CustomJwtAuthToken token) {
		return new TokenDetails(token.getAction(), token.getVerified());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		sb.append("Action: ").append(action);
		sb.append(", Verified: ").append(verificado);
		sb.append("]");
		return sb.toString();
	}
	
}
