package tghtechnology.tiendavirtual.Security.Interfaces;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Indica que el método posee la autilización de tipo TipoCargo.GERENTE. <br>
 * Puede ser utilizado por: <br>
 * - GERENTE <br>
 * - ADMIN
 */
@PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Gerente {}
