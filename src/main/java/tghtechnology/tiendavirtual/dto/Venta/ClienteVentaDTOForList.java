package tghtechnology.tiendavirtual.dto.Venta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoComprobante;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ClienteVentaDTOForList implements DTOForList<Venta>{

	// Datos del cliente
	private TipoDocIdentidad tipo_documento;
	private String numero_documento;
	private String razon_social;
	private String nombres;
	private String apellidos;
	private String telefono;
	private String correo;
    
	// Datos de direccion
	private String region;
	private String provincia;
	private String distrito;
	private String direccion;
	private String referencia;
	private Double latitud;
	private Double longitud;
	
	
	@Override
	public ClienteVentaDTOForList from(Venta ven) {
		this.tipo_documento = ven.getTipo_documento();
		this.numero_documento = ven.getNumero_documento();
		
		if(ven.getTipo_comprobante() == TipoComprobante.FACTURA) {
			this.razon_social = ven.getRazonSocial();
		} else {
			this.nombres = ven.getNombres();
			this.apellidos = ven.getApellidos();
		}
		this.telefono = ven.getTelefono();
		this.correo = ven.getCorreo();
		this.region = ven.getRegion();
		this.provincia = ven.getProvincia();
		this.distrito = ven.getDistrito();
		this.direccion = ven.getDireccion();
		this.referencia = ven.getReferencia();
		this.latitud = ven.getLatitud();
		this.longitud = ven.getLongitud();
		return this;
	}

}
