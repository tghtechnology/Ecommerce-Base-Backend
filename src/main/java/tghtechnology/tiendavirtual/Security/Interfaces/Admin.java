package tghtechnology.tiendavirtual.Security.Interfaces;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Indica que el método posee la autorización de tipo TipoCargo.ADMIN. <br>
 * Puede ser utilizado por: <br>
 * - ADMIN
 */
@PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Admin {}
