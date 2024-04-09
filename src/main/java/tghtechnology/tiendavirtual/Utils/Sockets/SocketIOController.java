package tghtechnology.tiendavirtual.Utils.Sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

@Component
public class SocketIOController {

	@Autowired
	SocketIOServer server;
	@Autowired
	SocketIOService socketService;
	
	
	public SocketIOController(SocketIOServer server) {
		
		this.server = server;
		
		this.server.addConnectListener(onUserConnect);
		this.server.addDisconnectListener(onUserDisconnect);
	}
	
	private ConnectListener onUserConnect = new ConnectListener() {
		@Override
		public void onConnect(SocketIOClient client) {
			socketService.onConnect(client);
		}
	};
	
	private DisconnectListener onUserDisconnect = new DisconnectListener() {
		@Override
		public void onDisconnect(SocketIOClient client) {
			socketService.onDisconnect(client);
		}
	};
	
}
