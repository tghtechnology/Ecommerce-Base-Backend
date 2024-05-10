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
 * Además, el sujeto NO requiere estar verificado, y la acción debe ser de tipo LOGIN.
 */
@PreAuthorize("isAuthenticated() && hasAnyRole('ADMIN','GERENTE','EMPLEADO','CLIENTE') && authentication.getAction().is('LOGIN')")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ClienteNoVerificacion {}