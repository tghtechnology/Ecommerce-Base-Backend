package tghtechnology.tiendavirtual.Utils.Sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PreDestroy;

@CrossOrigin
@Component
public class SocketIOConfig {

	@Autowired
	private SocketIOAuth socketAuth;
	
	@Value("${socket.host}")
	private String SOCKETHOST;
	@Value("${socket.port}")
	private int SOCKETPORT;
	private SocketIOServer server;
	
	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setHostname(SOCKETHOST);
		config.setPort(SOCKETPORT);
		config.setAuthorizationListener(onAuth);
		server = new SocketIOServer(config);
		server.start();
		return server;
	}
	
	private AuthorizationListener onAuth = new AuthorizationListener() {
		@Override
		public AuthorizationResult getAuthorizationResult(HandshakeData data) {
			return socketAuth.getAuthorizationResult(data);
		}
		
	};
	
	@PreDestroy
	public void stopSocketIOServer() {
		this.server.stop();
	}
	
}
