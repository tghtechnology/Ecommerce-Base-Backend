package tghtechnology.tiendavirtual.Services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Repository.UsuarioRepository;
import tghtechnology.tiendavirtual.Security.TokenGenerator;
import tghtechnology.tiendavirtual.Security.Models.IpLog;
import tghtechnology.tiendavirtual.Security.Models.UserLog;
import tghtechnology.tiendavirtual.Security.Repository.IpLogRepository;
import tghtechnology.tiendavirtual.Security.Repository.UserLogRepository;
import tghtechnology.tiendavirtual.dto.Usuario.UserLogin;

@Service
public class AuthService {
		
	private final AuthenticationManager authManager;
	private final IpLogRepository ipRepository;
	private final UserLogRepository ulRepository;
	private final UsuarioRepository usRepository;
	
	private final SettingsService settings;
	private final TokenGenerator tokens;
	
	private final Map<String, Integer> userAttempts = new HashMap<>();

	public AuthService(
			AuthenticationManager authManager,
			IpLogRepository ipRepository,
			UserLogRepository ulRepository,
			SettingsService settings,
			TokenGenerator tokens,
			UsuarioRepository usRepository) {
		super();
		this.authManager = authManager;
		this.ipRepository = ipRepository;
		this.ulRepository = ulRepository;
		this.settings = settings;
		this.tokens = tokens;
		this.usRepository = usRepository;
	}
	
	public String autenticar(UserLogin login, Map<String, String> headers) {
		
		String ip;
		
		Integer max_ip  = settings.getInt("seguridad.attempts_ip");
		Integer lock_ip = settings.getInt("seguridad.lockout_ip");
		
		Integer max_user  = settings.getInt("seguridad.attempts_user");
		Integer lock_user = settings.getInt("seguridad.lockout_user");
		
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
		if(ipLog.getFailedAttempts() != null && ipLog.getFailedAttempts() >= max_ip) {
			// Demasiados intentos fallidos
			// Probar que el último intento fallido haya sido hace más del tiempo determinado
			if(now.isAfter(ipLog.getLastAttempt().plusMinutes(lock_ip))) {
				// Reiniciar intentos
				ipLog.setFailedAttempts((short)0);
			} else {
				// Bloquear intento
				throw new BadCredentialsException("Demasiados intentos fallidos. Vuelve a intentar en " + lock_ip + " minutos.");
			}
		}
		if(userLog.getLastAttempt() != null && now.isBefore(userLog.getLastAttempt().plusMinutes(lock_user))) {
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
			
			Usuario user = usRepository.listarPorUserName(login.getUsername()).orElseThrow();
			
			return tokens.loginToken(auth, user.isAutenticado());
		} catch(BadCredentialsException ex) {
			// Guardar intento fallido
			Integer intentosUser = userAttempts.getOrDefault(userLog.getUsername(), 0) + 1;
			if(userAttempts.size() > 500) {
				userAttempts.clear();
			}
			userAttempts.put(userLog.getUsername(), intentosUser);
			
			if(intentosUser >= max_user) {
				userLog.setFailedAttempts(max_user.shortValue());
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
