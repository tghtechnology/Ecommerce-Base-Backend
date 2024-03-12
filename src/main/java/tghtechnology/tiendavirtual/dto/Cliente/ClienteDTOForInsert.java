package tghtechnology.tiendavirtual.dto.Cliente;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Models.Cliente;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTOForInsert implements DTOForInsert<Cliente>{
	
	@NotNull(message = "El campo no puede estar vacío")
	private TipoDocIdentidad tipo_documento;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 8, max = 15, message = "El documento tener entre 8 y 15 caracteres")
	private String numero_documento;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 80, message = "El nombre debe tener menos de 80 caracteres")
	private String nombre;
	
	@NotEmpty(message = "El campo no puede estar vacío")
	@Pattern(regexp = "^[0-9+]*$", message = "El teléfono no puede contener letras o simbolos")
    @Size(min = 7, max = 12, message = "El teléfono debe tener entre 7 y 12 caracteres")
	private String telefono;
	
	@NotEmpty(message = "El campo no puede estar vacío")
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El correo es invalido")
    @Size(min = 10, max = 80, message = "El correo debe tener entre 10 y 80 caracteres")
	private String email;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, max = 30, message = "la región debe tener entre 3 y 30 caracteres")
	private String region;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, max = 30, message = "La provincia debe tener entre 3 y 30 caracteres")
	private String provincia;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, max = 30, message = "El distrito debe tener entre 3 y 30 caracteres")
	private String distrito;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 150, message = "La dirección no debe estar vacía y debe tener un maximo de 150 caracteres")
	private String direccion;
	
    @Size(max = 150, message = "La referencia debe tener un maximo de 150 caracteres")
	private String referencia;
	
    private Double latitud;
    
    private Double longitud;

	@Override
	public Cliente toModel() {
		Cliente cli = new Cliente();
		cli.setTipo_documento(tipo_documento);
		cli.setNumero_documento(numero_documento);
		cli.setNombre(nombre);
		cli.setTelefono(telefono);
		cli.setCorreo(email);
		cli.setRegion(region);
		cli.setProvincia(provincia);
		cli.setDistrito(distrito);
		cli.setDireccion(direccion);
		cli.setReferencia(referencia);
		cli.setLatitud(latitud);
		cli.setLongitud(longitud);
		cli.setEstado(true);
		
		return cli;
	}

	@Override
	public Cliente updateModel(Cliente cli) {
		cli.setTipo_documento(tipo_documento);
		cli.setNumero_documento(numero_documento);
		cli.setNombre(nombre);
		cli.setTelefono(telefono);
		cli.setCorreo(email);
		cli.setRegion(region);
		cli.setProvincia(provincia);
		cli.setDistrito(distrito);
		cli.setDireccion(direccion);
		cli.setReferencia(referencia);
		cli.setLatitud(latitud);
		cli.setLongitud(longitud);
		
		return cli;
	}
    
	
}
