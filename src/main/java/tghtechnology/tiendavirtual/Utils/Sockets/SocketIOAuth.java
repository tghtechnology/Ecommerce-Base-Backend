package tghtechnology.tiendavirtual.Utils.Sockets;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;

@Component
public class SocketIOAuth {

	final private Map<UUID, LocalDateTime> expectedIDs = new HashMap<>();
	
	public AuthorizationResult getAuthorizationResult(HandshakeData data) {
		AuthorizationResult result = AuthorizationResult.FAILED_AUTHORIZATION;
		String sid = data.getSingleUrlParam("uid");
		
		if(sid != null) {
			UUID uid = UUID.fromString(sid.replace('_', '-'));
			
			LocalDateTime expiration = expectedIDs.get(uid);
			if(expiration != null && LocalDateTime.now().isBefore(expiration)) {
				result =  AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
				expectedIDs.remove(uid);
			}
		}
		cleanMap();
		return result;
	}
	
	public UUID add() {
		UUID uid = UUID.randomUUID();
		LocalDateTime expiration = LocalDateTime.now().plus(5L, ChronoUnit.MINUTES);
		expectedIDs.put(uid, expiration);
		return uid;
	}
	
	/**
	 * Limpia el mapa de las entradas expiradas
	 */
	private void cleanMap() {
		LocalDateTime now = LocalDateTime.now();
		
		expectedIDs.entrySet().forEach(entry -> {
			if(now.isAfter(entry.getValue()))
				expectedIDs.remove(entry.getKey());
		});
	}
	
}
