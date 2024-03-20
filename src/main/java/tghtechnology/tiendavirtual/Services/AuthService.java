package tghtechnology.tiendavirtual.Services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import tghtechnology.tiendavirtual.Security.SecurityConfig;
import tghtechnology.tiendavirtual.Security.Models.IpLog;
import tghtechnology.tiendavirtual.Security.Models.UserLog;
import tghtechnology.tiendavirtual.Security.Repository.IpLogRepository;
import tghtechnology.tiendavirtual.Security.Repository.UserLogRepository;
import tghtechnology.tiendavirtual.dto.UserLogin;

@Service
public class AuthService {
		
	private final JwtEncoder encoder;
	private final AuthenticationManager authManager;
	private final IpLogRepository ipRepository;
	private final UserLogRepository ulRepository;
	private final SecurityConfig config;
	
	private final Map<String, Integer> userAttempts = new HashMap<>();

	public AuthService(JwtEncoder encoder,
			AuthenticationManager authManager,
			IpLogRepository ipRepository,
			UserLogRepository ulRepository,
			SecurityConfig config) {
		super();
		this.encoder = encoder;
		this.authManager = authManager;
		this.ipRepository = ipRepository;
		this.ulRepository = ulRepository;
		this.config = config;
	}

	public String generateToken(Authentication authentication) {
		Instant now = Instant.now();
		List<String> scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				.expiresAt(now.plus(config.getTokenDuration(), ChronoUnit.HOURS))
				.subject(authentication.getName())
				.claim("rol", scope)
				.build();
		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		
	}
	
	public String autenticar(UserLogin login, Map<String, String> headers) {
		
		String ip;
		
		// Validar que existe el header X-Forwarded-For
		if(headers.containsKey("x-forwarded-for")) {
			final String header = headers.get("x-forwarded-for");
			if(!header.isBlank() && !header.isEmpty()) {
				ip = header.split(",")[0];
			} else 
				throw new BadCredentialsException("Header de IP invalido");
		} else 
			throw new BadCredentialsException("Header de IP no encontrado");
		
		LocalDateTime now = LocalDateTime.now();
		//Probar que la IP o usuario no tenga demasiados intentos fallidos
		IpLog ipLog = ipRepository.findById(ip).orElse(new IpLog());
		UserLog userLog = ulRepository.findById(login.getUsername()).orElse(new UserLog());
		if(ipLog.getFailedAttempts() != null && ipLog.getFailedAttempts() >= config.getMaxAttemptsIp()) {
			// Demasiados intentos fallidos
			// Probar que el último intento fallido haya sido hace más del tiempo determinado
			if(now.isAfter(ipLog.getLastAttempt().plusMinutes(config.getLockoutTimeIp()))) {
				// Reiniciar intentos
				ipLog.setFailedAttempts((short)0);
			} else {
				// Bloquear intento
				throw new BadCredentialsException("Demasiados intentos fallidos. Vuelve a intentar en " + config.getLockoutTimeIp() + " minutos.");
			}
		}
		if(userLog.getLastAttempt() != null && now.isBefore(userLog.getLastAttempt().plusMinutes(config.getLockoutTimeUser()))) {
			throw new BadCredentialsException("Demasiados intentos fallidos");
		}
		
		// Autenticar usuario
		userLog.setUsername(login.getUsername());
		userLog.setLastAttempt(now);		
		ipLog.setIp(ip);
		ipLog.setLastAttempt(now);
		ipLog.setUsername(login.getUsername());
		try {
			// Autenticar correctamente
			final Authentication auth = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
			ipLog.setSuccessful(true);
			ipLog.setFailedAttempts((short)0);
			ipRepository.save(ipLog);
			return generateToken(auth);
		} catch(BadCredentialsException ex) {
			// Guardar intento fallido
			Integer intentosUser = userAttempts.getOrDefault(userLog.getUsername(), 0) + 1;
			if(userAttempts.size() > 500) {
				userAttempts.clear();
			}
			userAttempts.put(userLog.getUsername(), intentosUser);
			
			if(intentosUser >= config.getMaxAttemptsUser()) {
				userLog.setFailedAttempts(config.getMaxAttemptsUser());
				userLog.setSuccessful(false);
				ulRepository.save(userLog);
			}
			
			ipLog.setSuccessful(false);
			ipLog.setFailedAttempts((short)(ipLog.getFailedAttempts()+1));
			ipRepository.save(ipLog);
			throw ex;
		}
	}
	
}
