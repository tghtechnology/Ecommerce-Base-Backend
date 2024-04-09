package tghtechnology.tiendavirtual.Utils.Sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SocketIOService {
	
	@Autowired
	SocketIOServer server;
	
	public void onConnect(SocketIOClient client) {
		log.info("Usuario conectado con ID: " + client.getSessionId());
	}
	
	public void onDisconnect(SocketIOClient client) {
		log.info("Usuario con ID: " + client.getSessionId() + " se ha desconectado.");
	}
	
	public void broadcast(String room, String mappedObject) {
		server.getBroadcastOperations().sendEvent(room, mappedObject);
	}
	
}
