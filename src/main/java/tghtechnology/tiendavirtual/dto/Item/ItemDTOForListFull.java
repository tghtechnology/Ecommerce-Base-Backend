package tghtechnology.tiendavirtual.dto.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.DisponibilidadItem;
import tghtechnology.tiendavirtual.Models.Item;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Categoria.CategoriaDTOForList;
import tghtechnology.tiendavirtual.dto.Descuento.DescuentoDTOForListMinimal;
import tghtechnology.tiendavirtual.dto.Marca.MarcaDTOForListMinimal;
import tghtechnology.tiendavirtual.dto.VariacionItem.VariacionDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTOForListFull implements DTOForList<Item>{

	private Integer id_item;
	private String url;
	private String nombre;
    private String descripcion;
    private BigDecimal estrellas;
    private Integer valoraciones;
    private DisponibilidadItem disponibilidad;
    private LocalDateTime fecha_creacion;
    private LocalDateTime fecha_modificacion;
    
    private BigDecimal precio;
    private BigDecimal costo;
    
    private List<VariacionDTOForList> modelos = new ArrayList<>();
    
    private CategoriaDTOForList categoria;
    private MarcaDTOForListMinimal marca;
    private DescuentoDTOForListMinimal descuento;
    
    private BigDecimal estrellas;
    private Integer valoraciones;

	public ItemDTOForListFull from(Item item, boolean extendedPermission) {
		this.id_item = item.getId_item();
		this.url = item.getText_id();
		this.nombre = item.getNombre();
		this.disponibilidad = item.getDisponibilidad();
		this.descripcion = item.getDescripcion();
		this.fecha_creacion = !extendedPermission ? null : item.getFecha_creacion();
		this.fecha_modificacion = !extendedPermission ? null : item.getFecha_modificacion();
		
		this.valoraciones = item.getValoraciones();
		this.estrellas = BigDecimal.valueOf(item.getEstrellas()).setScale(2, RoundingMode.HALF_UP);
		
		this.precio = item.getPrecio();
		this.costo = !extendedPermission ? null : item.getCosto();
		
		
		this.valoraciones = item.getValoraciones();
		this.estrellas = BigDecimal.valueOf(item.getEstrellas()).setScale(2, RoundingMode.HALF_UP);
		this.descuento = item.getDescuento() == null ? null : new DescuentoDTOForListMinimal().from(item.getDescuento());
		item.getVariaciones().forEach(var -> modelos.add(new VariacionDTOForList().from(var)));
		this.categoria = new CategoriaDTOForList().from(item.getCategoria());
		this.marca = new MarcaDTOForListMinimal().from(item.getMarca());
		return this;
	}



	@Override
	public DTOForList<Item> from(Item item) {
		return from(item, false);
	}
   
}
