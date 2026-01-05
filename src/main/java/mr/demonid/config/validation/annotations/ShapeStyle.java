package mr.demonid.config.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import mr.demonid.config.validation.impl.ShapeStyleValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Валидация типа фигуры значениями из ShapesManager.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ShapeStyleValidator.class)
public @interface ShapeStyle {
    String message() default "Invalid shape style";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
