package mr.demonid.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import mr.demonid.config.categories.App;
import mr.demonid.config.categories.ViewConfig;
import mr.demonid.config.yaml.KebabCasePropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * Класс конфигурации приложения.
 * Всегда имеет валидные значения, независимо от наличия файла
 * конфигурации и ошибок/недостающих данных в нём.
 */
@Data
@NoArgsConstructor
public class Config {

    final static Logger log = LogManager.getLogger(Config.class.getName());

    private App app;
    private ViewConfig viewConfig;


    public Config(String configFile) {
        Config instance = loadYaml(configFile);
        this.app = instance.getApp();
        this.viewConfig = instance.getViewConfig();
    }


    /**
     * Загрузка файла-конфигурации.
     * Загруженные данные проходят процедуру валидации и, если необходимо, замены
     * на дефолтные значения.
     *
     * @param filename Имя файла. Ищется либо в папке ресурсов, либо на диске.
     * @return Конфигурация, загруженная из файла или дефолтная.
     */
    private static Config loadYaml(String filename) {

        Constructor constructor = new Constructor(Config.class, new LoaderOptions());
        constructor.setPropertyUtils(new KebabCasePropertyUtils());

        Yaml yaml = new Yaml(constructor);
        try (InputStream in = openConfigStream(filename);
             ConfigPostProcessor processor = new ConfigPostProcessor()) {
            log.info("Loading configuration file '{}'", filename);

            Config raw = yaml.load(in);
            if (raw == null) {
                throw new FileNotFoundException("bad file format");
            }
            // нормализуем
            return processor.normalize(raw);

        } catch (Exception e) {
            log.error("Failed to load '{}'. Using default config. Details: ", filename, e);
            // возвращаем дефолтные настройки
            return ConfigDefaults.createDefaults();
        }
    }

    /**
     * Открытие файла. Сначала ищется в папке ресурсов. Если его там нет,
     * то пытается найти его на диске.
     *
     * @param filename Путь и имя файла.
     */
    public static InputStream openConfigStream(String filename) throws IOException {
        // Сначала пробуем найти в classpath
        InputStream stream = Config.class.getClassLoader().getResourceAsStream(filename);
        if (stream != null) {
            return stream;
        }
        // Если не найдено — ищем в файловой системе
        return new FileInputStream(filename);
    }

}
