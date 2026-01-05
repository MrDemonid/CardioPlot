package mr.demonid.config.yaml;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;


/**
 * Фильтр для конвертации имен из yaml-файла в CamelCase:
 * nnnn-mmmm --> nnnnMmmm
 */
public class KebabCasePropertyUtils extends PropertyUtils {

    @Override
    public Property getProperty(Class<?> type, String name) {
        return super.getProperty(type, toCamel(name));
    }

    private String toCamel(String kebab) {
        StringBuilder sb = new StringBuilder();
        boolean upper = false;
        for (char c : kebab.toCharArray()) {
            if (c == '-') {
                upper = true;
                continue;
            }
            sb.append(upper ? Character.toUpperCase(c) : c);
            upper = false;
        }
        return sb.toString();
    }
}