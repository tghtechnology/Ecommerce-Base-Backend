package tghtechnology.tiendavirtual.Utils.Sockets;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;

import tghtechnology.tiendavirtual.Services.SettingsService;

@Component
public class SocketIOAuth {

	@Autowired
	private SettingsService settings;
	
	final private Map<UUID, SocketSession> expectedIDs = new HashMap<>();
	
	public AuthorizationResult getAuthorizationResult(HandshakeData data) {
		AuthorizationResult result = AuthorizationResult.FAILED_AUTHORIZATION;
		String sid = data.getSingleUrlParam("uid");
		
		if(sid != null) {
			UUID uid = UUID.fromString(sid.replace('_', '-'));
			
			SocketSession session = expectedIDs.get(uid);
			if(session != null && LocalDateTime.now().isBefore(session.getExpiration())) {
				result =  AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
			}
			cleanMap();
		}
		return result;
	}
	
	public UUID add(String username) {
		UUID uid = UUID.randomUUID();
		LocalDateTime expiration = LocalDateTime.now().plus(settings.getInt("seguridad.token_duration"), ChronoUnit.HOURS);
		expectedIDs.put(uid, new SocketSession(expiration, username));
		return uid;
	}
	
	/**
	 * Limpia el mapa de las entradas expiradas
	 */
	private void cleanMap() {
		LocalDateTime now = LocalDateTime.now();
		
		expectedIDs.entrySet().forEach(entry -> {
			if(now.isAfter(entry.getValue().getExpiration()))
				expectedIDs.remove(entry.getKey());
		});
	}
	
	/**
	 * Elimina a una uid de la lista de UIDs esperadas.<br>
	 * Usualmente se utilizaría en el método de LOGOUT.
	 * @param username Nombre del usuario a remover.
	 */
	public void remove(String username) {
		final List<UUID> ids = new ArrayList<>();
		expectedIDs.entrySet().forEach(entry -> {
			if(entry.getValue().getUsername().equals(username)) {
				ids.add(entry.getKey());
			}
		});
		ids.forEach(expectedIDs::remove);
	}
	
}
