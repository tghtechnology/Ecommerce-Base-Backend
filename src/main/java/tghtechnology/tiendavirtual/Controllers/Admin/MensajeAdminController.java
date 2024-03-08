package tghtechnology.tiendavirtual.Controllers.Admin;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import tghtechnology.tiendavirtual.Services.NotificacionService;
import tghtechnology.tiendavirtual.Utils.Security.Interfaces.Empleado;
import tghtechnology.tiendavirtual.dto.NotificacionDTOForList;

@Slf4j
@RestController
@Controller
@AllArgsConstructor
@RequestMapping("/admin/mensaje")
public class MensajeAdminController {

	@Autowired
	private NotificacionService notiService2;
	
	@Empleado
    @GetMapping("/obtener")
    public Flux<ServerSentEvent<List<NotificacionDTOForList>>> obtenerNotificaciones() {
		UUID uuid = UUID.randomUUID();
        log.info("Subscribiendo a {" + uuid + "} para leer notificaciones");
        return notiService2.obtenerNotificaciones(uuid);
        
    }
	
}
