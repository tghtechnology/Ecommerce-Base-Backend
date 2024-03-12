package tghtechnology.tiendavirtual.dto.Cliente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTOForList implements DTOForList<Cliente>{

	private Integer id;
	private String nombre;
	private TipoDocIdentidad tipo_documento;
	private String numero_documento;
	private String telefono;
	private String correo;
	
	private String region;
	private String provincia;
	private String distrito;
	private String direccion;
	private String referencia;

	@Override
	public ClienteDTOForList from(Cliente cliente) {
		this.id = cliente.getId();
		this.nombre = cliente.getNombre();
		this.tipo_documento = cliente.getTipo_documento();
		this.numero_documento = cliente.getNumero_documento();
		this.telefono = cliente.getTelefono();
		this.correo = cliente.getCorreo();
		this.region = cliente.getRegion();
		this.provincia = cliente.getProvincia();
		this.distrito = cliente.getDistrito();
		this.direccion = cliente.getDireccion();
		this.referencia = cliente.getReferencia();
		return this;
	}
	
}
