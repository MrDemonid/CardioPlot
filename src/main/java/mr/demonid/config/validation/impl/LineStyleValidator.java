package mr.demonid.config.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mr.demonid.config.validation.annotations.LineStyle;

import java.util.Set;

public class LineStyleValidator implements ConstraintValidator<LineStyle, String> {

    private static final Set<String> allowed = Set.of(
            "SOLID", "DOTTED", "DASH",
            "SHORT_DASH", "LONG_DASH",
            "DOT_DASH", "CRITICAL"
    );


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return allowed.contains(value.toUpperCase());
    }

}
