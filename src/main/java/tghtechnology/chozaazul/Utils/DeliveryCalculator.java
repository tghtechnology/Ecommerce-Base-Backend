package tghtechnology.chozaazul.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
public class DeliveryCalculator {

	@Value("${tgh.location.latitud}")
	private Double latitud;
	@Value("${tgh.location.longitud}")
	private Double longitud;
	@Value("${tgh.location.tolerancia-metros}")
	private Double tolerancia;
	
	
	 private final int R = 6371; // Radius of the earth
	
	/**
	 * Calcula la distancia entre dos puntos según latitud y longitud
	 * (Punto 1 siempre será el local)
	 * 
	 * @Param lat Latitud del punto final
	 * @Param lon Longitud del punto final
	 * @returns Distancia en metros
	 */
	public double distance(double lat, double lon) {

	    double latDistance = Math.toRadians(lat - latitud);
	    double lonDistance = Math.toRadians(lon - longitud);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(latitud)) * Math.cos(Math.toRadians(lat))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    return Math.abs(distance);
	}
	
	
}
