package tghtechnology.chozaazul.dto.Promocion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Promocion;
import tghtechnology.chozaazul.dto.Promocion.PromocionPlato.PromocionPlatoDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class PromocionDTOForList {
	private Integer id_promocion;
	private BigDecimal descuento;
	private boolean repetible;
	private LocalDate fecha_inicio;
	private LocalDate fecha_finalizacion;
	
	private List<PromocionPlatoDTOForList> platos = new ArrayList<>();
	
	
	public PromocionDTOForList(Promocion prom) {
		this.id_promocion = prom.getId_promocion();
		this.descuento = prom.getDescuento();
		this.repetible = prom.isRepetible();
		this.fecha_inicio = prom.getFecha_inicio();
		this.fecha_finalizacion = prom.getFecha_finalizacion();
		
		prom.getPlatos().forEach(pl -> platos.add(new PromocionPlatoDTOForList(pl)));
	}
	
}
