package tghtechnology.chozaazul.dto.Cliente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.chozaazul.Models.Cliente;
import tghtechnology.chozaazul.Utils.ApisPeru.Enums.TipoDocIdentidad;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTOForList {

	private Integer id;
	private String nombre;
	private TipoDocIdentidad tipo_documento;
	private String numero_documento;
	private String telefono;
	private String correo;
	
	private String distrito;
	private String direccion;
	private String referencia;
	
	public ClienteDTOForList(Cliente cliente) {
		this.id = cliente.getId();
		this.nombre = cliente.getNombre();
		this.tipo_documento = cliente.getTipo_documento();
		this.numero_documento = cliente.getNumero_documento();
		this.telefono = cliente.getTelefono();
		this.correo = cliente.getCorreo();
		this.distrito = cliente.getDistrito();
		this.direccion = cliente.getDireccion();
		this.referencia = cliente.getReferencia();
	}
	
}
