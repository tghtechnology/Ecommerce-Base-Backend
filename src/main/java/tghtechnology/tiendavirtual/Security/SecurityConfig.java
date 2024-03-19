package tghtechnology.tiendavirtual.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * Propiedades de seguridad para los intentos de autorización.<br>
 * Todas las variables de esta clase pueden ser modificadas mediante propiedades:<br><br>
 * - tgh.security.lockout-time-minutes: Tiempo de bloqueo en minutos de IPs luego variso intentos fallidos (Default: 30)<br>
 * - tgh.security.max-attempts: Intentos máximos para login antes de que la IP sea bloqueada (Default: 5)<br>
 * <br>
 * - tgh.security.token-duration-hours: Duración en horas del token luego de un login exitoso (Default: 24)
 */
@Component
public class SecurityConfig {

	// Logging attempts
	
	@Value("${tgh.security.lockout-time-minutes-user:30}")
	@Getter private Long lockoutTimeUser;
	
	@Value("${tgh.security.lockout-time-minutes-ip:1440}")
	@Getter private Long lockoutTimeIp;
	
	@Value("${tgh.security.max-attempts-user:5}")
	@Getter private Short maxAttemptsUser;
	
	@Value("${tgh.security.max-attempts-ip:20}")
	@Getter private Short maxAttemptsIp;
	
	// Tokens
	
	@Value("${tgh.security.token-duration-hours:24}")
	@Getter private Integer tokenDuration;
	
}
