package tghtechnology.tiendavirtual.Utils.Sockets;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocketSession {

	private LocalDateTime expiration;
	private String username;
	
}
