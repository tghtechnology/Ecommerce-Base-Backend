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
    private String url;
    private String nombre;
    private String descripcion;
    
	private LocalDate fecha_inicio;
	private LocalDate fecha_fin;
	
	private Boolean es_descuento;
	private Integer valor_descuento;
	
	private Boolean activo;
	
	
	@Override
	public RebajaDTOForList from(Rebaja reb) {

		this.id_rebaja = reb.getId_rebaja();
		this.url = reb.getText_id();
		this.nombre = reb.getNombre();
		this.descripcion = reb.getDescripcion();
		
		this.fecha_inicio = reb.getFecha_inicio();
		this.fecha_fin = reb.getFecha_fin();
		
		this.es_descuento = reb.getEs_descuento();
		this.valor_descuento = reb.getValor_descuento();
		this.activo = reb.getActivo();
		
		return this;
	}
	
}
