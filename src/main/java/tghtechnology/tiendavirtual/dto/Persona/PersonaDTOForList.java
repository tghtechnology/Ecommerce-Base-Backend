package tghtechnology.tiendavirtual.dto.Persona;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Persona;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForList;

@Getter
@Setter
@NoArgsConstructor
public class PersonaDTOForList implements DTOForList<Persona>{

	private Integer id;
	private String nombres;
	private String apellidos;
	private TipoDocIdentidad tipo_documento;
	private String numero_documento;
	private String telefono;
	private String correo;

	@Override
	public PersonaDTOForList from(Persona per) {
		this.id = per.getId_persona();
		this.nombres = per.getNombres();
		this.apellidos = per.getApellidos();
		this.tipo_documento = per.getTipo_documento();
		this.numero_documento = per.getNumero_documento();
		this.telefono = per.getTelefono();
		this.correo = per.getCorreo_personal();
		return this;
	}
	
}
