package mr.demonid.config.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Валидация размеров линий (сетка, маркеры) таблиц.
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@DecimalMin("0.05")
@DecimalMax("7.0")
@Constraint(validatedBy = {})
public @interface LineRange {
    String message() default "Line size out of range 0.05...7";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
