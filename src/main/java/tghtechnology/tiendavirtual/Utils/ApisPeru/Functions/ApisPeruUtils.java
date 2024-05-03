package tghtechnology.tiendavirtual.Utils.ApisPeru.Functions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;

import tghtechnology.tiendavirtual.Models.DetalleVenta;
import tghtechnology.tiendavirtual.Utils.TghUtils;


public class ApisPeruUtils {
	
	public static String dateFormat(LocalDateTime datetime) {
		return datetime.atOffset(ZoneOffset.UTC).format(new DateTimeFormatterBuilder()
		        .append(TghUtils.formatter) // use the existing formatter for date time
		        .appendOffset("+HH:MM", "+05:00") // set 'noOffsetText' to desired '+00:00'
		        .toFormatter());
		
		//return datetime.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}
	
	public static BigDecimal calcularPrecioUnitario(BigDecimal precioUnitario, Integer igv, Boolean antes_de_igv) {
		return antes_de_igv
				// Antes de igv, el precio es el mismo y el IGV se cuenta normalmente
				? precioUnitario
				// Después de igv, el precio se reduce a aproximadamente 84.7458%
				// (4 decimales para que no hayan errores de redondeo hasta los 1000 productos aprox.)
				: precioUnitario.divide(BigDecimal.ONE.setScale(2).add(new BigDecimal(igv/100.0)), 4, RoundingMode.UP)
				;
	}
	
}
