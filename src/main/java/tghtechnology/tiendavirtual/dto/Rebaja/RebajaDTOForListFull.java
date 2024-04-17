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
    private String url;
    private String nombre;
    private String descripcion;
    
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

		this.id_rebaja = reb.getId_rebaja();
		this.url = reb.getText_id();
		this.nombre = reb.getNombre();
		this.descripcion = reb.getDescripcion();
		
		this.fecha_inicio = reb.getFecha_inicio();
		this.fecha_fin = reb.getFecha_fin();
		
		this.es_descuento = reb.getEs_descuento();
		this.valor_descuento = reb.getValor_descuento();
		
		this.es_evento = reb.getEs_descuento();
		this.activo = reb.getActivo();
		
		this.categorias.addAll(reb.getCategorias().stream().map(cc -> new CategoriaDTOForList().from(cc)).collect(Collectors.toList()));
		this.items.addAll(reb.getItems().stream().map(ii -> new ItemDTOForListMinimal().from(ii)).collect(Collectors.toList()));
		
		return this;
	}
	
}
