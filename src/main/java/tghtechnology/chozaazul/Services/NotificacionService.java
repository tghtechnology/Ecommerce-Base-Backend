package tghtechnology.chozaazul.Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import tghtechnology.chozaazul.Models.Notificacion;
import tghtechnology.chozaazul.Models.Pedido;
import tghtechnology.chozaazul.Repository.NotificacionRepository;
import tghtechnology.chozaazul.dto.NotificacionDTOForList;

@Service
@Primary
public class NotificacionService {

	private final NotificacionRepository notiRepo;
	
	private final Set<UUID> listeners = new HashSet<>();
	
	private final Map<Integer, Set<UUID>> notis = new HashMap<>();
	
	@Value("${tgh.notification.interval}")
	private Integer interval;
	
	public NotificacionService(NotificacionRepository notificacionRepository) {
		this.notiRepo = notificacionRepository;
	}
	
	
	public Flux<ServerSentEvent<List<NotificacionDTOForList>>> obtenerNotificaciones(UUID uuid){
		listeners.add(uuid);
		return Flux.interval(Duration.ofSeconds(interval))
				.publishOn(Schedulers.boundedElastic())
				.map(sequence -> ServerSentEvent.<List<NotificacionDTOForList>>builder().id(String.valueOf(sequence))
						.data(getNotifs(uuid))
						.build());
	}
	
	private List<NotificacionDTOForList> getNotifs(UUID uuid){
		//log.info("Leyendo notificaciones");
		List<Notificacion> n = notiRepo.listarNoLeidos();
		final List<NotificacionDTOForList> notifs = new ArrayList<>();
		n.forEach(noti -> {
			Set<UUID> set = notis.get(noti.getId());
			if(set == null)
				notis.put(noti.getId(), new HashSet<>());
			else {
				if(!set.contains(uuid)) {
					set.add(uuid);
					notifs.add(new NotificacionDTOForList(noti));
					if(set.equals(listeners)){
						noti.setVisto(true);
					}
				}
			}
		});
		notiRepo.saveAll(n);
		return notifs;
	}
	
    
    public void registrarNotificacion(Pedido ped) {
		Notificacion noti = new Notificacion();
		noti.setHora(LocalDateTime.now());
		noti.setPedido(ped);
		noti.setVisto(false);
		noti.setActivo(true);
		noti = notiRepo.save(noti);
		
		notis.put(noti.getId(), new HashSet<>());
		
    }
	
}
