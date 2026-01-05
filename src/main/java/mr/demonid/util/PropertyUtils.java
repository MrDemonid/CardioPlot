package mr.demonid.util;

import java.lang.reflect.Field;


public class PropertyUtils {

    /**
     * Информация о поле данных объекта.
     * @param container Объект.
     * @param field     Поле.
     */
    private record FieldInfo(Object container, Field field) {
    }


    /**
     * Устанавливает значение поля по пути (например, "pressureTable.width").
     */
    public static void setProperty(Object object, String path, Object value) throws Exception {
        FieldInfo fieldInfo = resolveField(object, path);
        fieldInfo.field().setAccessible(true);
        fieldInfo.field().set(fieldInfo.container(), value);
    }


    /**
     * Получает значение поля по пути из дефолтного объекта.
     * Например, для пути "pressureTable.width" вернёт значение width из дефолтного PressureTable.
     */
    public static Object getProperty(Object object, String path) throws Exception {
        FieldInfo fieldInfo = resolveField(object, path);
        fieldInfo.field().setAccessible(true);
        return fieldInfo.field().get(fieldInfo.container());
    }



    private static FieldInfo resolveField(Object root, String path) throws Exception {
        if (root == null || path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Null or empty input data");
        }

        String[] parts = path.trim().split("\\.");
        if (parts.length == 0) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }

        Object container = root;
        Field field = null;

        for (int i = 0; i < parts.length; i++) {
            String fieldName = parts[i];

            // Ищем поле с учётом иерархии классов
            field = findFieldInHierarchy(container.getClass(), fieldName);
            if (field == null) {
                throw new NoSuchFieldException("Field '" + fieldName + "' not found in " + container.getClass());
            }

            if (i < parts.length - 1) {
                // Получаем следующий контейнер
                field.setAccessible(true);
                container = field.get(container);
                if (container == null) {
                    throw new IllegalArgumentException("Field '" + fieldName + "' is null at path: " + path);
                }
            }
        }
        return new FieldInfo(container, field);
    }


    private static Field findFieldInHierarchy(Class<?> clazz, String fieldName) {
        while (clazz != null && clazz != Object.class) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

}
