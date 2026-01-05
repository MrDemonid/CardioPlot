package mr.demonid.config.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mr.demonid.config.validation.annotations.ColorCode;

import java.awt.*;

public class ColorCodeValidator implements ConstraintValidator<ColorCode, Color> {


    @Override
    public boolean isValid(Color value, ConstraintValidatorContext context) {
        return value != null;
    }

}
