package mr.demonid.config.categories;

public interface DefaultConfig<T> {

    void applyDefault();


    /**
     * Статичный метод для упрощения создания экземпляра класса с дефолтными значениями.
     * @param clazz Класс создаваемого экземпляра.
     */
    static <T extends DefaultConfig<T>> T create(Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            obj.applyDefault();
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Can't create instance of " + clazz.getName(), e);
        }
    }

}
