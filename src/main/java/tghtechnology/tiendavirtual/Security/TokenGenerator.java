package tghtechnology.tiendavirtual.Security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.TokenActions;
import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Services.SettingsService;

@Service
@AllArgsConstructor
public class TokenGenerator {

	private final JwtEncoder encoder;
	private final SettingsService settings;
	
	public String loginToken(Authentication authentication, Boolean isVerified) {
		Instant now = Instant.now();
		List<String> scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plus(settings.getInt("seguridad.login_token_duration"), ChronoUnit.HOURS))
				.subject(authentication.getName())
				.claim("rol", scope)
				.claim("action", TokenActions.LOGIN)
				.claim("verified", isVerified)
				.build();
		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		
	}
	
	public String verificationToken(Usuario user) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plus(settings.getInt("seguridad.valid_token_duration"), ChronoUnit.HOURS))
				.subject(user.getUsername())
				.claim("action", TokenActions.VERIFY)
				.build();
		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
	public String changePassToken(Usuario user) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plus(settings.getInt("seguridad.chpass_token_duration"), ChronoUnit.HOURS))
				.subject(user.getUsername())
				.claim("action", TokenActions.CHANGE_PASSWORD)
				.build();
		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}
	
}
