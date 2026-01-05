package mr.demonid.config.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import mr.demonid.config.validation.impl.LineStyleValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Валидация типа линии значениями из StrokeManager.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LineStyleValidator.class)
public @interface LineStyle {
    String message() default "Invalid line style";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
