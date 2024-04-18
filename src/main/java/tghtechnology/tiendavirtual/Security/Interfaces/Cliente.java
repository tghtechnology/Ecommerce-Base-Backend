package tghtechnology.tiendavirtual.Security.Interfaces;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Indica que el método posee la autilización de tipo TipoCargo.CLIENTE. <br>
 * Puede ser utilizado por: <br>
 * - CLIENTE <br>
 * - GERENTE <br>
 * - ADMIN
 */
@PreAuthorize("isAuthenticated()")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Cliente {}
