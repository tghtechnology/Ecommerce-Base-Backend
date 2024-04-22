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
		this.formato = "";
	}
	
}
