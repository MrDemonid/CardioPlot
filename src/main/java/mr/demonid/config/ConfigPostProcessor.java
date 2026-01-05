package mr.demonid.config;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import mr.demonid.util.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;


/**
 * Проверка и корректировка загруженных из файла настроек приложения.
 */
public class ConfigPostProcessor implements AutoCloseable {

    final Logger log = LogManager.getLogger(getClass().getName());

    private final Validator validator;
    private final ValidatorFactory factory;


    public ConfigPostProcessor() {
        this.factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }


    public Config normalize(Config cfg) throws Exception {
        Config def = ConfigDefaults.createDefaults();
        process(cfg, def);
        return cfg;
    }


    private void process(Config cfg, Config def) throws Exception {
        processSection(cfg, "app", cfg.getApp(), def.getApp());
        processSection(cfg, "viewConfig", cfg.getViewConfig(), def.getViewConfig());
    }


    private <T> void processSection(Config target, String fieldPath, T section, T defaultSection) throws Exception {
        if (section == null) {
            log.warn("Section '{}' is null, replacing with defaults", fieldPath);
            try {
                PropertyUtils.setProperty(target, fieldPath, defaultSection);
                return;
            } catch (Exception e) {
                log.error("Failed to set section '{}' to defaults: {}", fieldPath, e.getMessage());
                throw e;
            }
        }

        Set<ConstraintViolation<T>> violations = validator.validate(section);
        for (ConstraintViolation<T> v : violations) {
            String propertyPath = v.getPropertyPath().toString();
            try {
                Object defaultValue = PropertyUtils.getProperty(defaultSection, propertyPath);
                PropertyUtils.setProperty(section, propertyPath, defaultValue);
                log.warn("Invalid field '{}' (value: {}). Using default: {}", propertyPath, v.getInvalidValue(), defaultValue);
            } catch (Exception e) {
                log.error("Failed to normalize field '{}': {}", propertyPath, e.getMessage());
                throw e;
            }
        }

    }

    @Override
    public void close() {
        factory.close();
    }


}