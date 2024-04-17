package tghtechnology.tiendavirtual.dto.Rebaja;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Rebaja;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class RebajaDTOForList implements DTOForList<Rebaja>{

    private Integer id_rebaja;
	private LocalDate fecha_inicio;
	private LocalDate fecha_fin;
	
	private Boolean es_descuento;
	private Integer valor_descuento;
	
	private Boolean es_evento;
	private Boolean activo;
	
	
	@Override
	public RebajaDTOForList from(Rebaja reb) {

		id_rebaja = reb.getId_rebaja();
		fecha_inicio = reb.getFecha_inicio();
		fecha_fin = reb.getFecha_fin();
		es_descuento = reb.getEs_descuento();
		valor_descuento = reb.getValor_descuento();
		es_evento = reb.getEs_descuento();
		activo = reb.getActivo();
		
		return this;
	}
	
}
