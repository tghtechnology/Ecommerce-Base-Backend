package tghtechnology.tiendavirtual.Security.Interfaces;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Indica que el método posee la autorización de tipo TipoCargo.CLIENTE. <br>
 * Puede ser utilizado por: <br>
 * - CLIENTE <br>
 * - EMPLEADO <br>
 * - GERENTE <br>
 * - ADMIN <br>
 * Además, el sujeto debe estar verificado. Para clientes no verificados, utilizar 
 * {@link package tghtechnology.tiendavirtual.Security.Interfaces.ClienteNoVerificacion ClienteNoVerificacion}.<br>
 * Y la acción debe ser de tipo LOGIN.
 */
@PreAuthorize("isAuthenticated() "
		+ "&& hasAnyRole('ADMIN','GERENTE','EMPLEADO','CLIENTE') "
		+ "&& authentication.getVerified() == true "
		+ "&& authentication.getAction().is('LOGIN')")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Cliente {}
