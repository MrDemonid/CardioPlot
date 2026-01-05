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
 * Валидация на размеры в процентах.
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@DecimalMin("10.0")
@DecimalMax("100.0")
@Constraint(validatedBy = {})
public @interface PercentRange {
    String message() default "Percent size out of range 1...100";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
