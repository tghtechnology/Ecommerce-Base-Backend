package tghtechnology.tiendavirtual.Utils.izipay.Objects.Enum;

import lombok.Getter;

public enum Banco {

	BCP(2),
	IBK_PRIVADA(3),
	IBK(5),
	CITI(7),
	SBP(9),
	LOY(10),
	BBVA(11),
	BN(18),
	COMERCIO(23),
	PICHINCHA(35),
	BIF(38),
	SBP2(41),
	CSF(43),
	MIBANCO(49)
	;
	
	@Getter
	private final Integer value;
	
	private Banco(Integer val) {
		this.value = val;
	}
}

