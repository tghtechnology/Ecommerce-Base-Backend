package tghtechnology.tiendavirtual.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tghtechnology.tiendavirtual.Services.UsuarioService;

@Slf4j
@Configuration
@EnableScheduling
@AllArgsConstructor
public class ScheduledTasks {

	private final UsuarioService userService;
	
	/**
	 * Limpia el cache de solicitudes de cambio de contraseña del
	 * servicio de usuarios para evitar llenar la memoria.
	 */
	@Scheduled(fixedDelay = 1*60*60*1000) // 1 hora
	private void limpiarCacheUsuario() {
		final LocalDateTime now = LocalDateTime.now();
		final AtomicInteger modified = new AtomicInteger(0);
		final List<String> toRemove = new ArrayList<>();
		
		userService.getPassChangeRequestCache().entrySet().forEach(entry -> {
			if(entry.getValue().isAfter(now)) {
				toRemove.add(entry.getKey());
				modified.incrementAndGet();
			}
		});
		toRemove.forEach(x -> userService.getPassChangeRequestCache().remove(x));
		
		log.debug(String.format("Removidas [%d] entradas del cache de usuarios.", modified.get()));
	}
	
}
