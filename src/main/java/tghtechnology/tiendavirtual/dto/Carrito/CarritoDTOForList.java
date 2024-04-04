package tghtechnology.tiendavirtual.dto.Carrito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Carrito;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;
import tghtechnology.tiendavirtual.dto.Carrito.DetalleCarrito.DetalleCarritoDTOForList;

@Getter
@Setter
@NoArgsConstructor
public class CarritoDTOForList implements DTOForList<Carrito>{

	private Integer id_usuario;
	private BigDecimal precio_total = BigDecimal.ZERO;
	
	private List<DetalleCarritoDTOForList> detalles = new ArrayList<>();
	
	
	@Override
	public CarritoDTOForList from(Carrito car) {
		if(car == null) return null;
		
		this.id_usuario = car.getId_usuario();
		
		car.getDetalles().stream().sorted().forEach(det -> {
			DetalleCarritoDTOForList dc = new DetalleCarritoDTOForList().from(det);
			detalles.add(dc);
			precio_total = precio_total.add(dc.getSubtotal());
		});
		
		return this;
	}

}
