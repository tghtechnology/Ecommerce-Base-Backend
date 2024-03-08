package tghtechnology.chozaazul.Utils.ApisPeru.Objects;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.chozaazul.Utils.ApisPeru.Enums.Moneda;

@Getter
@Setter
public class PaymentTerms {

	private String moneda;
	private String tipo;
	private float monto;
	
	public PaymentTerms(Moneda moneda, String tipo, float monto) {
		this.moneda = moneda.getLabel();
		this.tipo = tipo;
		this.monto = monto;
	}
	
	public PaymentTerms(Moneda moneda, String tipo) {
		this.moneda = moneda.getLabel();
		this.tipo = tipo;
	}
	
	public PaymentTerms(Moneda moneda) {
		this.moneda = moneda.getLabel();
		this.tipo = "Contado";
	}
	
	public PaymentTerms() {
		this.moneda = Moneda.PEN.getLabel();
		this.tipo = "Contado";
	}
	
}
