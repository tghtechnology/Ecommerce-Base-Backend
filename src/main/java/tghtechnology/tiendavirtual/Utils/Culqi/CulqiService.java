package tghtechnology.tiendavirtual.Utils.Culqi;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.culqi.Culqi;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Services.SettingsService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Moneda;
import tghtechnology.tiendavirtual.Utils.Culqi.Objects.AntiFraudDetails;
import tghtechnology.tiendavirtual.Utils.Culqi.Objects.CulqiCharge;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForInsert;

@Service
public class CulqiService {
	
	@Autowired
	private SettingsService settings;
	
	@Value("${culqi.secret_key}")
	@Getter @Setter
	private String secret_key;
	
	@Value("${culqi.public_key}")
	@Getter @Setter
	private String public_key;
	@Getter @Setter
	private String rsa_key;
	@Getter @Setter
	private String rsa_id;
	
	private Boolean init = false;
	
	private final Culqi culqi = new Culqi();
	
	/**
	 * Obtiene la instancia de Culqi del servicio.<br>
	 * También inicializa el servicio con las llaves pública
	 * y privada si es que no lo estuviera ya
	 * @return La instancia de Culqi del servicio.
	 */
	public Culqi getCulqi() {
		if(!init) initialize();
		return culqi;
	}
	
	/**
	 * Inicializa la librería de Culqi con la llave pública
	 * y privada.
	 */
	private void initialize() {
		Culqi.public_key = public_key;
		Culqi.secret_key = secret_key;
		init = true;
	}
	
	public CulqiCharge toCargo(final VentaDTOForInsert ven, BigDecimal total) {
		return new CulqiCharge(
				ven.getSource_id(),
				total.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).intValue(),
				Moneda.PEN,
				ven.getCorreo(),
				settings.getString("culqi.nombre_cargo"),
				toAntiFraud(ven),
				null
			);
	}

	public AntiFraudDetails toAntiFraud(VentaDTOForInsert ven) {
		return new AntiFraudDetails(
				ven.getDireccion_facturacion(),
				ven.getProvincia(),
				"PE",
				ven.getNombres(),
				ven.getApellidos(),
				ven.getTelefono()
				);
	}
	
}
