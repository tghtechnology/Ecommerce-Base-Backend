package tghtechnology.tiendavirtual.Utils.izipay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;
import tghtechnology.tiendavirtual.Services.SettingsService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Moneda;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.IzipayBillingDetails;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.IzipayCharge;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.IzipayCustomer;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.IzipaySecurity;
import tghtechnology.tiendavirtual.dto.Venta.ClienteVentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForList;

@Service
public class IzipayTranslatorService {

	@Value("${tgh.version}")
	@Getter private String tvirtual_version;
	
	@Autowired
	private SettingsService settings;
	
	public IzipayCharge toCharge(VentaDTOForList ven) {
		return new IzipayCharge(
					String.format("TiendaVirtual-TGH %s", tvirtual_version),
					ven.getPrecio_total().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP).intValue(),
					Moneda.PEN,
					String.format("PED%06d", ven.getId_venta()),
					toCustomer(ven.getCliente()),
					toSecurityData(ven),
					new HashMap<>()
				);
	}
	
	public IzipayCustomer toCustomer(ClienteVentaDTOForList cli) {
		return new IzipayCustomer(
					cli.getCorreo(),
					toBillingDetails(cli)
				);
	}
	
	public IzipayBillingDetails toBillingDetails(ClienteVentaDTOForList cli) {
		return new IzipayBillingDetails(
					cli.getDireccion(),
					cli.getTelefono(),
					"PE",
					cli.getRazon_social(),
					cli.getNombres(),
					cli.getApellidos(),
					cli.getTipo_documento(),
					cli.getNumero_documento()
				);
	}
	
	public IzipaySecurity toSecurityData(VentaDTOForList ven) {
		return new IzipaySecurity(
					ven.getCliente().getCorreo(),
					ven.getCliente().getTelefono(),
					ven.getCliente().getTipo_documento(),
					ven.getCliente().getNumero_documento(),
					false,
					null,
					settings.getString("company.nombre_comercial"),
					false,
					null
				);
	}
	
}
