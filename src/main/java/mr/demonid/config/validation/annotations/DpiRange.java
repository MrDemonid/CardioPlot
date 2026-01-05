package mr.demonid.config.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Валидация значения DPI.
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Min(72)
@Max(1200)
@Constraint(validatedBy = {})
public @interface DpiRange {
    String message() default "DPI out of range 72...1200";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
