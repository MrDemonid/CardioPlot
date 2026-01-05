package mr.demonid.chart.builder.impl;

import mr.demonid.view.styles.linestyle.StrokeManager;


/**
 * Правило для установки маркера на график.
 * @param value Значение шкалы графика.
 * @param type  Стиль линии маркера.
 */
public record MarkerRule(int value, StrokeManager.Type type) {}
