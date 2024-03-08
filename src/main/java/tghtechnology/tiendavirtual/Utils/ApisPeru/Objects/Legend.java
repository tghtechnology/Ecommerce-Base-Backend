package tghtechnology.tiendavirtual.Utils.ApisPeru.Objects;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Leyenda;

@Getter
@Setter
public class Legend {

	private String code;
	private String value;
	
	public Legend(Leyenda leyenda) {
		this.code = leyenda.getCodigo();
		this.value = leyenda.getValor();
	}
	
	public Legend(Leyenda leyenda, String valor) {
		this.code = leyenda.getCodigo();
		this.value = valor;
	}
	
}
