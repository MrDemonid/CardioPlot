package mr.demonid.config.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import mr.demonid.config.validation.impl.FileNameValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Валидация имени файла.
 * mustExist - проверять ли существование файла.
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileNameValidator.class)
public @interface FileName {
    String message() default "Invalid file name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    boolean mustExist() default false;
}
