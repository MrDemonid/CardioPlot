package mr.demonid.config.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mr.demonid.config.validation.annotations.ShapeStyle;

import java.util.Set;

public class ShapeStyleValidator implements ConstraintValidator<ShapeStyle, String> {

    private static final Set<String> allowed = Set.of(
            "LIGHTING", "ECG", "VSHAPE", "CIRCLE", "DOT", "SQUARE", "RECT"
    );


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        return allowed.contains(value.toUpperCase());
    }
}
