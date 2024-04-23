package tghtechnology.tiendavirtual.Emails;

import tghtechnology.tiendavirtual.Models.Usuario;
import tghtechnology.tiendavirtual.Utils.Emails.BaseEmail;

public class EmailVerificacion extends BaseEmail{
	
	public EmailVerificacion(Usuario user, String url, String token) {
		super(user.getUsername(), "Email de verificación.");
		
		setFormato();
		
		addPlaceholder("nombre-personal", user.getPersona().getNombres());
		addPlaceholder("url-validacion", url);
		addPlaceholder("token", token);
		
	}
	
	private void setFormato() {
		this.formato = "Hola, ${nombre-personal}!\n Este es un e-mail de verificación de cuenta. "
				+ "Haz click en el siguiente enlace para verificarte:\n\n"
				+ "<a href=\"${url-validacion}?token=${token}\">Verifica tu cuenta</a> <br>"
				+ "${url-validacion}?token=${token}";
	}
	
}
