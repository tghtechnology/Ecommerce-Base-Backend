package tghtechnology.tiendavirtual.Security.Interfaces;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Indica que el método posee la autorización de tipo TipoCargo.CLIENTE 
 * y que el método tiene el propósito de cambiar la contraseña de un usuario.
 * Puede ser utilizado por: <br>
 * - CLIENTE <br>
 * - EMPLEADO <br>
 * - GERENTE <br>
 * - ADMIN <br>
 * Además, la acción debe ser de tipo CHANGE_PASSWORD.
 */
@PreAuthorize("isAuthenticated() "
		+ "&& hasAnyRole('ADMIN','GERENTE','EMPLEADO','CLIENTE') "
		+ "&& #auth.getAction().is('CHANGE_PASSWORD')")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CambiarPassUsuario {}
