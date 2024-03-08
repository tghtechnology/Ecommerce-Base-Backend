package tghtechnology.tiendavirtual.Utils;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class Propiedades {

	@Value("${tgh.email.gerente}")
	@Getter private String emailGerente;
	
	@Value("${tgh.facturacion.igv}")
	@Getter private BigDecimal igv;
	
	@Value("${tgh.facturacion.antes_de_igv}")
	@Getter private Boolean antesDeIGV;
	
	@Value("${tgh.precios.delivery-corto}")
	@Getter private BigDecimal precioDeliveryCorto;
	
	@Value("${tgh.precios.delivery-largo}")
	@Getter private BigDecimal precioDeliveryLargo;
	
}
