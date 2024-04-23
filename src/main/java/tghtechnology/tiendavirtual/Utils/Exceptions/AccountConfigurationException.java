package tghtechnology.tiendavirtual.Utils.Exceptions;

import org.springframework.security.access.AccessDeniedException;

import lombok.Getter;

/**
 * Excepcion para representar un fallo al intentar
 * realizar cambios al usuario. Usualmente en métodos
 * dirigidos desde un e-mail.
 *
 */

@Getter
public class AccountConfigurationException extends AccessDeniedException{

	private static final long serialVersionUID = 1L;

	public AccountConfigurationException(String msg) {
		super(msg);
	}

}
