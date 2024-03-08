package tghtechnology.chozaazul.Utils.Security.Interfaces;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Indica que el método posee la autilización de tipo TipoCargo.GERENTE. <br>
 * Puede ser utilizado por: <br>
 * - GERENTE
 */
@PreAuthorize("hasRole('GERENTE')")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Gerente {}
