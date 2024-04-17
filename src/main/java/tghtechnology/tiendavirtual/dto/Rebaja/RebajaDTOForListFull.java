package tghtechnology.tiendavirtual.dto.Rebaja;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Rebaja;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;
import tghtechnology.tiendavirtual.dto.Item.ItemDTOForListMinimal;

@Getter
@Setter
@NoArgsConstructor
public class RebajaDTOForListFull implements DTOForList<Rebaja>{

    private Integer id_rebaja;
	private LocalDate fecha_inicio;
	private LocalDate fecha_fin;
	
	private Boolean es_descuento;
	private Integer valor_descuento;
	
	private Boolean es_evento;
	private Boolean activo;
	
	private List<CategoriaDTOForList> categorias = new ArrayList<>();
	private List<ItemDTOForListMinimal> items = new ArrayList<>();
	
	
	@Override
	public RebajaDTOForListFull from(Rebaja reb) {

		id_rebaja = reb.getId_rebaja();
		fecha_inicio = reb.getFecha_inicio();
		fecha_fin = reb.getFecha_fin();
		es_descuento = reb.getEs_descuento();
		valor_descuento = reb.getValor_descuento();
		es_evento = reb.getEs_descuento();
		activo = reb.getActivo();
		
		categorias.addAll(reb.getCategorias().stream().map(cc -> new CategoriaDTOForList().from(cc)).collect(Collectors.toList()));
		items.addAll(reb.getItems().stream().map(ii -> new ItemDTOForListMinimal().from(ii)).collect(Collectors.toList()));
		
		return this;
	}
	
}
