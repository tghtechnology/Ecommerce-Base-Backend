package tghtechnology.tiendavirtual.Utils.ApisPeru.Functions;

import java.math.BigDecimal;

import lombok.Getter;

public class DineroALetras {

	/**
	 * Convertir un número de tipo BigDecimal a su representación en dinero. Con soporte hasta números de 9 dígitos.
	 * Números con una escala mayor a 2 serán truncados para mentener el formato de dinero.<br>
	 * Ejemplo:<br>
	 * - S/.102.50 -> SON CIENTO DOS CON 50/100 SOLES.
	 * @param cantidad Cantidad de dinero a convertir en texto.
	 * @return La cantidad proporcionada representada en letras.
	 */
	public static String convertirADinero(BigDecimal cantidad) {
		int enteros = cantidad.intValue();
		int centavos = cantidad.remainder(BigDecimal.ONE).multiply(new BigDecimal(100)).intValue();
		
		return "SON " + convertirAString(enteros, 0) + "CON " + centavos + "/100 SOLES";
		
	}
	
	
	private static String convertirAString(int num, int escalamiles) {
		int restante = num;
		String val= "";
		boolean solo = true;
		
		if(num > 99) {
			if(num > 999) {
				// Cuatro+ digitos
				val = val.concat(convertirAString(num/1000, escalamiles+1));
				restante = num%1000;
				solo = false;
			}
			if(restante > 0) {
				//Tres digitos
				int d3 = restante/100;
				restante = num%100;
				
				val += centenas(d3, restante);
			}
		}
		if(restante > 0) {
			// Dos digitos
			val += decenas(restante, escalamiles); 
		}
		if(solo || restante > 0) {
			switch(escalamiles) {
			case 1:
				val += "MIL "; break;
			case 2:
				val += (num == 1) ? "MILLON " : "MILLONES "; break;
			case 3:
				val += (num == 1) ? "MIL MILLON " : "MIL MILLONES "; break;
			case 4:
				val += (num == 1) ? "BILLON " : "BILLONES "; break;
			}
		}
		
		return val;
	}

	private enum Digito{
		
		CERO(0),
		UNO(1),
		DOS(2),
		TRES(3),
		CUATRO(4),
		CINCO(5),
		SEIS(6),
		SIETE(7),
		OCHO(8),
		NUEVE(9);
		
		@Getter
		private int numero;
		
		private Digito(int numero) {
			this.numero = numero;
		}
		
		public static Digito ValueOf(int num) {
			for(Digito d : values()) {
				if(num == d.getNumero())
					return d;
			}
			throw new IllegalArgumentException("Ningún valor concuerda con el número proporcionado.");
		}
		
	}
	
	private static String centenas(int digito, int restante) {
		
		String num;
		
		switch(digito) {
		case 1:
			num = (restante == 0) ? "CIEN" : "CIENTO";
			break;
		case 5:
			num = "QUINIENTOS";
			break;
		case 7:
			num = "SETECIENTOS";
			break;
		case 9:
			num = "NOVECIENTOS";
			break;
		default:
			num = Digito.ValueOf(digito).toString() + "CIENTOS";
			break;
		}
		return num + " ";
	}
	
	private static String decenas(int digitos, int escala) {
		
		if(digitos < 10) return unidades(digitos, escala, false);
		
		String num;
		int unidad = digitos%10;
		int decena = digitos/10;
		
		if(digitos < 20) 
			switch(digitos) {
			case 10:
				num = "DIEZ"; break;
			case 11:
				num = "ONCE"; break;
			case 12:
				num = "DOCE"; break;
			case 13:
				num = "TRECE"; break;
			case 14:
				num = "CATORCE"; break;
			case 15:
				num = "QUINCE"; break;
			default:
				num = "DIECI" + Digito.ValueOf(unidad).toString(); break;
			}
		else
			if(digitos < 30) 
				num = "VENTI" + Digito.ValueOf(unidad).toString();
			else {
				switch(decena) {
				case 3:
					num = "TREINTA"; break;
				case 4:
					num = "CUARENTA"; break;
				case 5:
					num = "CINCUENTA"; break;
				case 6:
					num = "SESENTA"; break;
				case 7:
					num = "SETENTA"; break;
				case 8:
					num = "OCHENTA"; break;
				case 9:
					num = "NOVENTA"; break;
				default:
					num = "";
				}
				num = num + unidades(unidad, 0, true);
			}
		return num + " ";
	}
	
	private static String unidades(int digito, int escala, boolean esDecena) {
		
		String num = "";
		
		if(digito == 0) {
			if(escala == 0)
				num += "CERO";
		} else if(digito == 1) {
			if(escala == 0)
				num += "UNO";
			else 
				if(escala > 1)
					num += "UN ";
		} else
			num += Digito.ValueOf(digito).toString();
		
		return (esDecena ? " Y " : "") + num;
	}
}
