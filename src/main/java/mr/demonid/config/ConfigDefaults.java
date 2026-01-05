package mr.demonid.config;

import mr.demonid.config.categories.App;
import mr.demonid.config.categories.DefaultConfig;
import mr.demonid.config.categories.ViewConfig;


public class ConfigDefaults {

    /**
     * Возвращает полностью инициализированную дефолтную конфигурацию.
     */
    public static Config createDefaults() {
        Config def = new Config();
        def.setApp(DefaultConfig.create(App.class));
        def.setViewConfig(DefaultConfig.create(ViewConfig.class));
        return def;
    }

}
