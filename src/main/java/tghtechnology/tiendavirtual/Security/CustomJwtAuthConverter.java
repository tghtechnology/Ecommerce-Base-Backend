package tghtechnology.tiendavirtual.Security;

import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.util.Assert;

/**
 * Convierte el token de formato {@link Jwt} a formato {@link CustomJwtAuthToken}.<br>
 * También posee la función {@link #setJwtGrantedAuthoritiesConverter(Converter)} para customizar las autoridades (roles).
 */
public class CustomJwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken>{
	
	private Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
	
	@Override
	public final AbstractAuthenticationToken convert(Jwt jwt) {
		Collection<GrantedAuthority> authorities = this.jwtGrantedAuthoritiesConverter.convert(jwt);

		String principalClaimValue = jwt.getClaimAsString(JwtClaimNames.SUB);
		TokenDetails dets = TokenDetails.getDetails(jwt);
		
		return new CustomJwtAuthToken(jwt, authorities, principalClaimValue, dets.getAction(), dets.getVerificado());
	}
	
	public void setJwtGrantedAuthoritiesConverter(
			Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter) {
		Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
		this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
	}
}


