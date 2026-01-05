package mr.demonid.config.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mr.demonid.config.validation.annotations.FileName;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileNameValidator implements ConstraintValidator<FileName, String> {

    private boolean mustExist;


    @Override
    public void initialize(FileName annotation) {
        this.mustExist = annotation.mustExist();
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        Path path;
        try {
            path = Paths.get(value);
        } catch (Exception e) {
            return false;
        }

        if (mustExist) {
            String normalized = path.toString();
            File file = new File(normalized);
            return file.exists() && file.isFile();
        }

        return true;
    }

}
