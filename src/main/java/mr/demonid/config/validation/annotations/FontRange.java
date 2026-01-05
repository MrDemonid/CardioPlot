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
 * Валидация размеров фонта.
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@DecimalMin("1.0")
@DecimalMax("48.0")
@Constraint(validatedBy = {})
public @interface FontRange {
    String message() default "Font size out of range 1...48";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
