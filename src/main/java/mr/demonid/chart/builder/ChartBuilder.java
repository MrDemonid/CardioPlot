package mr.demonid.chart.builder;

import mr.demonid.model.measurement.types.ChartBounds;
import mr.demonid.model.measurement.types.Measurement;
import org.jfree.chart.JFreeChart;

import java.time.YearMonth;
import java.util.List;


/**
 * Создание таблиц давления и пульса.
 */
public interface ChartBuilder {
    JFreeChart createPressureChart(List<Measurement> list, ChartBounds bounds, YearMonth ym);
    JFreeChart createPulseChart(List<Measurement> list, ChartBounds bounds, YearMonth ym);
}
