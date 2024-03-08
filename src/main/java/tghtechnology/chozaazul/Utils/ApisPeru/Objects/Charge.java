package tghtechnology.chozaazul.Utils.ApisPeru.Objects;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.chozaazul.Utils.ApisPeru.Enums.TipoCargo;

@Getter
@Setter
public class Charge {
	
	private String codTipo;
	private BigDecimal factor;
	private BigDecimal monto;
	private BigDecimal montoBase;
	
	public Charge(TipoCargo codTipo,
					BigDecimal factor,
					BigDecimal monto,
					BigDecimal montoBase) {
		
		this.codTipo = codTipo.getLabel();
		this.factor = factor;
		this.monto = monto;
		this.montoBase = montoBase;
	}
	
	public Charge(TipoCargo codTipo,
					BigDecimal factor,
					BigDecimal montoBase) {

		this.codTipo = codTipo.getLabel();
		this.factor = factor;
		this.montoBase = montoBase;
		this.monto = montoBase.multiply(factor);
	}
	
}
