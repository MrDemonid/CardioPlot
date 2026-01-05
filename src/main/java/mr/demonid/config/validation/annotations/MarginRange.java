package mr.demonid.config.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Валидация отступов.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@NotNull
@Min(0)
@Max(100)
@Constraint(validatedBy = {})
public @interface MarginRange {
    String message() default "Margin out of range 0..100";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
