package tghtechnology.tiendavirtual.Security.Interfaces;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Indica que el método posee la autorización de tipo TipoCargo.ADMIN. <br>
 * Puede ser utilizado por: <br>
 * - ADMIN <br>
 * Además, el sujeto requiere estar verificado, y la acción debe ser de tipo LOGIN.
 */
@PreAuthorize("isAuthenticated() "
		+ "&& hasRole('ADMIN') "
		+ "&& #auth.getVerified() == true "
		+ "&& #auth.getAction().is('LOGIN')")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Admin {}
