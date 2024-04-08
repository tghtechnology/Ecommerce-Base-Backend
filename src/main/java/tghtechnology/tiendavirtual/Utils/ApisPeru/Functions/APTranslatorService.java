package tghtechnology.tiendavirtual.Utils.ApisPeru.Functions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Services.SettingsService;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.AfectacionIGV;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.Leyenda;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoCargo;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoOperacion;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.UnidadMedida;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Address;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Boleta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Charge;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Client;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.Company;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Objects.SaleDetail;
import tghtechnology.tiendavirtual.dto.Venta.ClienteVentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.DetalleVentaDTOForList;
import tghtechnology.tiendavirtual.dto.Venta.VentaDTOForList;

@Service
@AllArgsConstructor
public class APTranslatorService {
	
	private SettingsService settings;
	
	public Boleta toBoleta(VentaDTOForList ven) {
		
		final Client client = toClient(ven.getCliente());
				
		final List<Leyenda> legends = new ArrayList<>();
		legends.add(Leyenda.MONTO_EN_LETRAS);
		
		final List<Charge> descuentos = new ArrayList<>();
		final List<Charge> cargos = new ArrayList<>();
		
		final List<SaleDetail> detalles = new ArrayList<>();
		ven.getDetalles().forEach(d -> detalles.add(toSaleDetail(d)));
		
		return new Boleta(
				TipoOperacion.VENTA_INTERNA,
				ven.getTipo_comprobante(),
				ven.getId_venta(), //TODO: Cambiar por correlativo de boletas/facturas
				ven.getFecha_realizacion(),
				client,
				getCompanyData(),
				BigDecimal.ZERO, // Suma de otros cargos
				ven.getAntes_de_igv(),
				detalles,
				legends,
				ven.getObservacion(),
				null,
				descuentos.isEmpty() ? null : descuentos,
				cargos.isEmpty() ? null : cargos
				);
	}

	public SaleDetail toSaleDetail(DetalleVentaDTOForList det) {
		List<Charge> descuentos = new ArrayList<>();
		List<Charge> cargos = new ArrayList<>();
		
		if(det.getPorcentaje_descuento() != null && det.getPorcentaje_descuento() != 0) {
			descuentos.add(toDescuento(det));
		}
		
		return new SaleDetail(
				UnidadMedida.UNIDAD_BIENES,
				det.getCantidad(),
				String.format("PROD-%04d%02d",det.getId_item(), det.getVar_correlativo()),
				String.format("%s | %s %s",
						det.getNombre_item(),
						det.getTipo_variacion().getDescripcion(),
						det.getValor_variacion()),
				det.getPrecio_unitario(),
				new BigDecimal(settings.getInt("facturacion.igv")).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP),
				AfectacionIGV.GRAVADO_OPERACION_ONEROSA,
				BigDecimal.ZERO,
				descuentos.isEmpty() ? null : descuentos,
				cargos.isEmpty() ? null : cargos
				);
	}
	
	public Client toClient(ClienteVentaDTOForList cli) {
		return new Client(
				cli.getTipo_documento(),
				cli.getNumero_documento(),
				cli.getRazon_social(),
				toAddress(cli),
				cli.getCorreo(),
				cli.getTelefono()
				);
	}
	
	public Address toAddress(ClienteVentaDTOForList cli) {
		return new Address(
				"", //TODO obtener ubigeo
				cli.getRegion(),
				cli.getProvincia(),
				cli.getDistrito(),
				cli.getDireccion()
				);
	}
	
	public Charge toDescuento(DetalleVentaDTOForList det) {
		final BigDecimal factor = new BigDecimal(det.getPorcentaje_descuento()).divide(new BigDecimal(100));
		final BigDecimal montoBase = det.getPrecio_unitario().multiply(new BigDecimal(det.getCantidad()));
		return new Charge(
				TipoCargo.DESC_AFECTA_BASE_IMPONIBLE_IGV_IVAP,
				factor,
				montoBase,
				montoBase.multiply(factor)
				);
	}
	
	public Company getCompanyData() {
		return new Company(
					settings.getString("company.ruc"),
					settings.getString("company.razon_social"),
					settings.getString("company.nombre_comercial"),
					new Address(
							settings.getString("company.ubigeo"),
							settings.getString("company.departamento"),
							settings.getString("company.provincia"),
							settings.getString("company.distrito"),
							settings.getString("company.direccion")
							),
					settings.getString("company.email"),
					settings.getString("company.telefono")
				);
	}
}
