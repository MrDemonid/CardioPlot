package mr.demonid.chart.services;

import mr.demonid.chart.types.ChartLineStyle;
import mr.demonid.chart.types.GridLineStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


public class ChartService {

    private final Logger log = LogManager.getLogger(getClass().getName());


    /**
     * Создание графика на основе временной диаграммы.
     *
     * @param title          Название графика.
     * @param timeAxisLabel  Обозначение горизонтальной (нижней) оси.
     * @param valueAxisLabel Обозначение вертикальной оси.
     * @param dataset        Временная диаграмма.
     */
    public JFreeChart createTimeSeriesChart(String title, String timeAxisLabel, String valueAxisLabel, TimeSeriesCollection dataset) {

        return ChartFactory.createTimeSeriesChart(
                title,
                timeAxisLabel,
                valueAxisLabel,
                dataset,
                false,
                true,
                false
        );
    }

    /**
     * Установка цветов и размеров сетки таблицы.
     *
     * @param chart Таблица.
     * @param style Цвета и размеры сетки.
     */
    public void setGridLinesStyle(JFreeChart chart, GridLineStyle style) {
        XYPlot plot = chart.getXYPlot();
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlineStroke(new BasicStroke(style.rangeSize()));
        plot.setDomainGridlineStroke(new BasicStroke(style.domainSize()));
        plot.setRangeGridlinePaint(style.rangeColor());
        plot.setDomainGridlinePaint(style.domainColor());
    }

    /**
     * Установка размеров и цветов непосредственно линий для отрисовки графиков.
     *
     * @param chart  Таблица.
     * @param styles Стили.
     */
    public void setSeriesLinesStyle(JFreeChart chart, List<ChartLineStyle> styles) {
        XYPlot plot = chart.getXYPlot();
        XYItemRenderer base = plot.getRenderer();
        if (!(base instanceof XYLineAndShapeRenderer renderer))
            return;
        XYDataset ds = plot.getDataset();
        if (ds == null)
            return;

        for (int i = 0; i < styles.size(); i++) {
            renderer.setSeriesStroke(i, styles.get(i).stroke());
            renderer.setSeriesPaint(i, styles.get(i).color());
        }
    }

    /**
     * Установка вертикальной шкалы делений таблицы.
     *
     * @param chart Таблица.
     * @param range Нижний и верхний пределы.
     */
    public void setRange(JFreeChart chart, Range range, int unit) {
        XYPlot plot = chart.getXYPlot();
        // вертикальная разметка каждые 10 единиц
        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setRange(range);
        axis.setTickUnit(new NumberTickUnit(unit));
    }

    public void setRange(JFreeChart chart, int minRange, int maxRange, int unit) {
        XYPlot plot = chart.getXYPlot();
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(minRange, maxRange);
        range.setTickUnit(new NumberTickUnit(unit));
    }

    /**
     * Установка горизонтальной шкалы делений таблицы.
     */
    public void setDomain(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();
        // горизонтальная разметка по 1 дню
        DateAxis domain = (DateAxis) plot.getDomainAxis();
        domain.setAutoTickUnitSelection(false);
        domain.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1, new SimpleDateFormat("dd")));
    }

    /**
     * Установка горизонтальной шкалы делений таблицы на полный месяц,
     * независимо от количества данных.
     */
    public void setDomain(JFreeChart chart, LocalDate startDate, LocalDate endDate) {
        XYPlot plot = chart.getXYPlot();
        DateAxis domain = (DateAxis) plot.getDomainAxis();
        // отключаем автоподбор
        domain.setAutoRange(false);
        // устанавливаем жёсткие границы: от начала до конца месяца
        domain.setMinimumDate(java.util.Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        domain.setMaximumDate(java.util.Date.from(endDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant()));
        // Шаг разметки — 1 день
        domain.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1, new SimpleDateFormat("dd")));
    }


    /**
     * Установка фонта для таблицы.
     * Для заголовка используется немного увеличенный размер шрифта.
     *
     * @param chart Таблица.
     * @param font  Awt-фонт.
     *              <p>
     *              TODO: перенести в конфиг константу.
     */
    public void setFont(JFreeChart chart, Font font) {
        if (font == null) {
            log.error("Font is null");
            return;
        }
        XYPlot plot = chart.getXYPlot();
        // Заголовок
        if (chart.getTitle() != null) {
            float baseSize = font.getSize2D();
            chart.getTitle().setFont(font.deriveFont(Font.BOLD, baseSize + baseSize * (1 / 6f)));
        }
        // Подписи делений оси X
        plot.getDomainAxis().setTickLabelFont(font);
        // Название оси X
        plot.getDomainAxis().setLabelFont(font);
        // Подписи делений оси Y
        plot.getRangeAxis().setTickLabelFont(font);
        // Название оси Y
        plot.getRangeAxis().setLabelFont(font);
    }

    /**
     * Установка цвета фона таблицы и её элементов.
     *
     * @param chart Таблица.
     * @param color Цвет.
     */
    public void setChartBackground(JFreeChart chart, Color color) {
        chart.setBackgroundPaint(color);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(color);
        if (chart.getLegend() != null)
            chart.getLegend().setBackgroundPaint(color);
    }

}
