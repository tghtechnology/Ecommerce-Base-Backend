package tghtechnology.tiendavirtual.dto.Venta;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tghtechnology.tiendavirtual.Enums.EstadoPedido;
import tghtechnology.tiendavirtual.Models.Venta;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoComprobante;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;
import tghtechnology.tiendavirtual.Utils.DTOInterfaces.DTOForInsert;

@Getter
@Setter
@NoArgsConstructor
public class VentaDTOForInsert implements DTOForInsert<Venta>{
	
	// Datos de la venta
	@NotNull(message = "No puede ser nulo")
	private TipoComprobante tipo_comprobante;
	
	// Datos del cliente
	@NotNull(message = "El campo no puede estar vacío")
	private TipoDocIdentidad tipo_documento;
	
	@NotEmpty(message = "El campo no puede estar vacío")
	@Size(min = 8, max = 15, message = "El documento tener entre 8 y 15 caracteres")
	private String numero_documento;
	
	@NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, max = 80, message = "El nombre debe tener menos de 80 caracteres")
	private String nombres;
	
    @Size(min = 1, max = 80, message = "El nombre debe tener menos de 80 caracteres")
	private String apellidos;
	
	@Pattern(regexp = "^[0-9+]*$", message = "El teléfono no puede contener letras o simbolos")
    @Size(min = 7, max = 12, message = "El teléfono debe tener entre 7 y 12 caracteres")
	private String telefono;
	
	@NotEmpty(message = "El campo no puede estar vacío")
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El correo es invalido")
    @Size(min = 10, max = 80, message = "El correo debe tener entre 10 y 80 caracteres")
	private String correo;
	
	// Direccion
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
	
	@Size(max = 250, message = "La observacion debe tener un maximo de 250 caracteres")
	private String observacion;

	@Size(min = 1, message = "No puede estar vacío")
	private List<DetalleVentaDTOForInsert> carrito;
	
	@Override
	public Venta toModel() {
		Venta ven = new Venta();
		
		ven.setTipo_comprobante(tipo_comprobante);
		ven.setFecha(LocalDateTime.now());
		ven.setEstado_pedido(EstadoPedido.PENDIENTE);
		ven.setEstado(true);
		
		ven.setTipo_documento(tipo_documento);
		ven.setNumero_documento(numero_documento);
		ven.setRazon_social(nombres + (apellidos == null || apellidos.isBlank() ? "" : " " + apellidos));
		ven.setTelefono(telefono);
		ven.setCorreo(correo);
		
		ven.setRegion(region);
		ven.setProvincia(provincia);
		ven.setDistrito(distrito);
		ven.setDireccion(direccion);
		ven.setReferencia(referencia);
		
		return ven;
	}

	@Override
	public Venta updateModel(Venta modelToUpdate) {
		throw new UnsupportedOperationException();
	}
}
