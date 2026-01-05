package mr.demonid.config.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import mr.demonid.config.validation.impl.ColorCodeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Валидация значения цвета.
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ColorCodeValidator.class)
public @interface ColorCode {
    String message() default "Invalid color format: expected 0xRRGGBB or Decimal";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
