package tghtechnology.tiendavirtual.Utils.Security.Interfaces;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Indica que el método posee la autilización de tipo TipoCargo.EMPLEADO. <br>
 * Puede ser utilizado por: <br>
 * - EMPLEADO <br>
 * - GERENTE
 */
@PreAuthorize("hasAnyRole('GERENTE','EMPLEADO')")
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Empleado {}
