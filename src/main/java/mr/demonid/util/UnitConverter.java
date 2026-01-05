package mr.demonid.util;

public final class UnitConverter {

    // дефолтный dpi
    private static final float POINTS_PER_INCH = 72f;


    /**
     * Пересчет pt под новый DPI.
     */
    public static int pointsToPixels(float points, int dpi) {
        return Math.round((points / POINTS_PER_INCH) * dpi);
    }

    /**
     * Возвращает соотношение заданного DPI к дефолтному.
     * @param dpi Заданное DPI.
     */
    public static float dpiScale(int dpi) {
        return dpi / POINTS_PER_INCH;
    }


    /**
     * Выравнивание до 10 в меньшую сторону.
     * @param x Исходное число.
     */
    public static int roundDown10(int x) {
        return (x / 10) * 10;
    }

    /**
     * Выравнивание до 10 в большую сторону
     * @param x Исходное число.
     */
    public static int roundUp10(int x) {
        return ((x + 9) / 10) * 10;
    }

}
