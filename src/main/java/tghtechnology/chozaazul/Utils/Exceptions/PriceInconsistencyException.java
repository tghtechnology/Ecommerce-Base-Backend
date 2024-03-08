package tghtechnology.chozaazul.Utils.Exceptions;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import lombok.Getter;

public class PriceInconsistencyException extends NoSuchElementException{

	private static final long serialVersionUID = 1L;
	
	@Getter
	private final BigDecimal received;
	@Getter
	private final BigDecimal expected;
	
	public PriceInconsistencyException(BigDecimal expected, BigDecimal received) {
		super("El precio recibido no se corresponde con el esperado");
		this.received = received;
		this.expected = expected;
		
	}
	
	public void printWarn() {
		System.err.printf("%s:\n- Esperado: %s\n- Recibido: %s\n", getMessage(), expected, received);
		Double dif = expected.doubleValue() -  received.doubleValue();
		
		if(dif > 0) {
		System.err.printf("- Diferencia: S/.%s ¿Posible intento de estafa?\n", dif);
		}
	}

}
