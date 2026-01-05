package mr.demonid.chart.services;

import mr.demonid.chart.types.MarkerStyle;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import java.awt.*;
import java.util.List;


public class MarkerService {

    /**
     * Добавляет вывод значков для значений графика.
     * @param chart             График.
     * @param markerSeriesIndex На каком dataset.
     * @param shape             Фигура (значок).
     * @param color             Цвет.
     */
    public void setMarkerShape(JFreeChart chart, int markerSeriesIndex, Shape shape, Color color) {
        XYPlot plot = chart.getXYPlot();

        // Получаем существующий рендерер графика
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();

        // Только точки, линии не рисуем
        renderer.setSeriesLinesVisible(markerSeriesIndex, false);
        renderer.setSeriesShapesVisible(markerSeriesIndex, true);

        // Задаём форму и цвет маркера
        renderer.setSeriesShape(markerSeriesIndex, shape);
        renderer.setSeriesPaint(markerSeriesIndex, color);
    }


    /**
     * Создает маркерные линии для графика.
     * @param chart  График.
     * @param styles Стиль линии (тип, толщина, цвет) и на каком слое рисуем (FOREGROUND или BACKGROUND).
     */
    public void setMarkerStrike(JFreeChart chart, List<MarkerStyle> styles) {
        styles.forEach(style -> {
            XYPlot plot = chart.getXYPlot();

            ValueMarker marker = new ValueMarker(style.value());
            marker.setPaint(style.color());
            marker.setStroke(style.stroke());
            plot.addRangeMarker(marker, style.layer());
        });
    }

}
