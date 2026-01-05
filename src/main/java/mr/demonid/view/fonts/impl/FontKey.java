package mr.demonid.view.fonts.impl;


/**
 * Ключ для кеша загруженных фонтов.
 * @param name  Имя фонта.
 * @param size  Размер.
 * @param style Стиль (Font.PLAIN || Font.BOLD || Font.ITALIC)
 */
public record FontKey(String name, float size, int style) {
}
