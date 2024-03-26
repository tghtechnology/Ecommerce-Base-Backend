package tghtechnology.tiendavirtual.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Notificacion;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Functions.ApisPeruUtils;

@Getter
@Setter
@NoArgsConstructor
public class NotificacionDTOForList {
	
	private String hora;
	private Integer id_pedido;
	
	public NotificacionDTOForList(Notificacion noti) {
		this.hora = ApisPeruUtils.dateFormat(noti.getHora());
		this.id_pedido = noti.getPedido().getId_usuario();
	}
	
}
	
