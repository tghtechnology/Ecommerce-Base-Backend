package tghtechnology.tiendavirtual.Utils.Validation.Constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import tghtechnology.tiendavirtual.Utils.Validation.Validators.DateValidator;

/**
 * El elemento anotado debe encontrarse dentro de un rango de fechas definido (exclusivo).
 * <p>
 *  * Tipo soportado:
 * <ul>
 *     <li>{@code LocalDate}</li>
 * </ul>
 * <p>
 * {@code null} se considera como válido.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface InDateRange {

	String message() default "La fecha no se encuentra en el rango definido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String before() default "2099-12-31";

    String after() default "1990-01-01";
	
}
