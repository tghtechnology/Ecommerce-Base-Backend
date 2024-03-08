package tghtechnology.tiendavirtual.Utils.Emails;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.io.ByteArrayResource;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseEmail {

	/**Destinatario del correo*/
	@Getter @Setter protected String destinatario;
	
	/**Asunto del correo*/
	@Getter @Setter protected String asunto;
	
	/**
	 * Formato base del correo.<br>
	 * Se pueden utilizar placeholders con el formato ${placeholder},<br>
	 * estos placholders serán reemplazados por los valores del campo {@link #emailData}
	 * al momento de construir el correo.<br>
	 * <br>
	 * En caso se quiera añadir el contenido del email por separado, utilizar el placeholder
	 * ${CONTENIDO-EMAIL}, que será reeplazado por el valor de la variable {@link #formato}.
	 */
	@Getter protected String formato;
	
	/**
	 * Contenido interior del correo.<br>
	 * Se pueden utilizar placeholders con el formato ${placeholder},<br>
	 * estos placholders serán reemplazados por los valores del campo {@link #emailData} al momento de construir el correo.
	 */
	@Getter protected String contenido = "";
	
	/**
	 * Datos variables del correo.<br>
	 * Reemplazan los placeholders de {@link #formato} al momento de construir el correo.<br>
	 * Para insertar con el formato correcto, utilizar la función {@link #addPlaceholder(String, Object)}
	 */
	@Getter protected final Map<String, Object> emailData = new HashMap<>();
	
	/**
	 * Archivos adjuntos del correo.<br>
	 * El archivo debe encontrarse en formato de ByteArray (byte[]) y en forma de un ByteArrayResource.
	 * El formato del archivo depende del nombre asignado.
	 * Para insertar con el formato correcto, utilizar la función {}
	 */
	@Getter protected final Map<String, ByteArrayResource> adjuntos = new HashMap<>();
	
	/**
	 * Constructor de un BaseEmail
	 * @param destinatario La dirección de correo a la cual será destinado el correo
	 * @param asunto El asunto del correo
	 */
	public BaseEmail(String destinatario, String asunto) {
		this.destinatario = destinatario;
		this.asunto = asunto;
	}
	
	/**
	 * Constructor vacío para un BaseEmail
	 */
	public BaseEmail() {}
	
	/**
	 * Añade un placeholder para reemplazar al momento de construir el correo.<br>
	 * El placeholder no debe tener formato y únicamente debe ser el nombre que se le quiere asignar.
	 * Ejemplo: <br>
	 * - addPlaceholder("valor", objeto);<br>
	 * en vez de: <br>
	 * - addPlaceholder("${valor}, objeto")
	 * @param placeholder El nombre del placeholder
	 * @param valor El valor por el cual reemplazar el placeholder al construir el correo.
	 */
	public void addPlaceholder(String placeholder, Object valor) {
		if(placeholder.equals("CONTENIDO-EMAIL")) throw new IllegalArgumentException("No se puede añadir un placeholder con ese nombre");
		this.emailData.put(placeholder, valor);
	}
	
	/**
	 * Añade un archivo adjunto al correo.<br>
	 * El formato del archivo se define en el nombre asignado.<br>
	 * Ejemplo:<br>
	 * - addAdjunto("archivo.pdf", archivo)
	 * @param nombre Nombre del archivo. Define el formato que tomará al ser adjuntado.
	 * @param archivo Archivo a adjuntar en formato ByteArrayResource.
	 */
	public void addAdjunto(String nombre, ByteArrayResource archivo) {
		this.adjuntos.put(nombre, archivo);
	}
	
	/**
	 * Construye el Email utilizando el formato y reemplazando los placeholders.
	 * @return El email construido en formato String.
	 */
	public String buildEmail() {
		emailData.put("CONTENIDO-EMAIL", this.contenido);
		return StringSubstitutor.replace(formato, emailData, "${", "}");
	}
	
}
